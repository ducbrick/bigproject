package threeoone.bigproject.controller.controllers;

import javafx.collections.FXCollections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.DocActionType;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.ActionOnDoc;
import threeoone.bigproject.controller.viewcontrollers.DocOverviewController;
import threeoone.bigproject.controller.viewcontrollers.DocumentDetailController;
import threeoone.bigproject.controller.viewcontrollers.MenuController;
import threeoone.bigproject.controller.viewcontrollers.EditDocumentController;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.services.DocumentRetrievalService;

/**
 * Interacting with view controller and service
 * Handle logic for Document manipulation
 * Get information from service and send action to view based on that information
 *
 * @author HUY1902
 */
@Component
public class ActionOnDocController {


  private final DocumentDetailController documentDetailController;
  private final DocumentRetrievalService documentRetrievalService;
  private final DocOverviewController docOverviewController;
  private final MenuController menuController;
  private final EditDocumentController editDocumentController;

  /**
   * Constructor for document logic handler
   * Construct with needed service and view controller
   *
   * @param documentDetailController document detail page controller
   * @param documentRetrievalService document retrieval service
   * @param docOverviewController    document overview controller
   */
  public ActionOnDocController(DocumentDetailController documentDetailController,
                               DocumentRetrievalService documentRetrievalService,
                               DocOverviewController docOverviewController,
                               MenuController menuController,
                               EditDocumentController editDocumentController) {
    this.documentDetailController = documentDetailController;
    this.documentRetrievalService = documentRetrievalService;
    this.docOverviewController = docOverviewController;
    this.menuController = menuController;
    this.editDocumentController = editDocumentController;
  }

  /**
   * Registers request receivers for document handling.
   *
   * @param documentDetailRequestSender the request sender for document detail
   */
  @Autowired
  private void registerRequestReceiver(
          RequestSender<Document> documentDetailRequestSender,
          RequestSender<User> getListAllDocumentRequestSender,
          RequestSender<Document> updateDocActionRequestSender,
          RequestSender<ActionOnDoc> actionOnDocRequestSender,
          RequestSender<Integer> getDocumentByIdRequestSender) {
    documentDetailRequestSender.registerReceiver(this::documentDetail);
    getListAllDocumentRequestSender.registerReceiver(this::getListAllDocument);
    updateDocActionRequestSender.registerReceiver(this::updateAvailableActionOnDocument);
    actionOnDocRequestSender.registerReceiver(this::makeActionOnDoc);
  }

  /**
   * Handles the reception of a document detail request and forwards the document
   * to the DocumentDetailController.
   *
   * @param document the document whose details are to be shown
   */
  private void documentDetail(Document document) {
    documentDetailController.setDocument(document);
  }

  /**
   * return List of All Document in dataset
   * @param user None
   */
  private void getListAllDocument(User user) {
    docOverviewController.setTable(FXCollections.observableArrayList(
            documentRetrievalService.getAllDocuments()
    ));
  }

  /**
   * Call service and get status about document then update available action
   * for that document
   *
   * @param document document which need get status
   */
  // TODO: call to service
  private void updateAvailableActionOnDocument(Document document) {
    boolean isBorrowAvailable = false; //get from service
    boolean isRemoveAvailable = true; //get from service
    docOverviewController.updateMenuContext(isBorrowAvailable, isRemoveAvailable);
  }

  /**
   * Call service and switch scene if needed for make an action on doc
   *
   * @param actionOnDoc action request
   */
  //TODO Handle action by calling to service
  private void makeActionOnDoc(ActionOnDoc actionOnDoc) {
    switch (actionOnDoc.type()) {
      case DocActionType.EDIT -> {editDocumentController.setDocument(actionOnDoc.document());}
//      case DocActionType.BORROW -> System.out.println("borrow");
//      case DocActionType.REMOVE -> System.out.println("remove");
    }
  }

  private void getDocumentById(Integer id) {
    menuController.setRandomBook(documentRetrievalService.getDocumentById(id));
  }
}
