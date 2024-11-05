package threeoone.bigproject.controller.controllers;

import javafx.collections.FXCollections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.viewcontrollers.DocOverviewController;
import threeoone.bigproject.controller.viewcontrollers.DocumentDetailController;
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

  /**
   * Constructor for document logic handler
   * Construct with needed service and view controller
   *
   * @param documentDetailController document detail page controller
   * @param documentRetrievalService document retrieval service
   * @param docOverviewController    document overview controller
   */
  public ActionOnDocController(DocumentDetailController documentDetailController,
                               DocumentRetrievalService documentRetrievalService, DocOverviewController docOverviewController) {
    this.documentDetailController = documentDetailController;
    this.documentRetrievalService = documentRetrievalService;
    this.docOverviewController = docOverviewController;
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
          RequestSender<Document> updateDocActionRequestSender) {
    documentDetailRequestSender.registerReceiver(this::documentDetail);
    getListAllDocumentRequestSender.registerReceiver(this::getListAllDocument);
    updateDocActionRequestSender.registerReceiver(this::updateAvailableActionOnDocument);
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
  private void updateAvailableActionOnDocument(Document document) {
    boolean isBorrowAvailable = false; //get from service
    boolean isRemoveAvailable = true; //get from service
    docOverviewController.updateMenuContext(isBorrowAvailable, isRemoveAvailable);
  }
}
