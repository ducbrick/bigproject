package threeoone.bigproject.controller.controllers;

import javafx.collections.FXCollections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.controller.viewcontrollers.*;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.services.DocumentPersistenceService;
import threeoone.bigproject.services.DocumentRetrievalService;
import threeoone.bigproject.util.Alerts;

import java.util.Comparator;
import java.util.List;

/**
 * Interacting with view controller and service
 * Handle logic for Document manipulation
 * Get information from service and send action to view based on that information
 *
 * @author HUY1902
 */
@Component
@RequiredArgsConstructor
public class ActionOnDocController {
  private final DocumentDetailController documentDetailController;
  private final DocumentRetrievalService documentRetrievalService;
  private final DocOverviewController docOverviewController;
  private final LendingDetailController lendingDetailController;
  private final MenuController menuController;
  private final EditDocumentController editDocumentController;
  private final DocumentPersistenceService documentPersistenceService;


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
          RequestSender<Integer> getDocumentByIdRequestSender,
          RequestSender<SwitchScene> getLastestDocumentsRequestSender,
          RequestSender<Document> getRandomDocumentRequestSender,
          RequestSender<Document> editDocumentRequestSender,
          RequestSender<Document> removeDocumentRequestSender,
          RequestSender<Document> borrowDocumentRequestSender,
          RequestSender<Document> addDocumentRequestSender,
          RequestSender<Document> commitChangeDocRequestSender) {
    documentDetailRequestSender.registerReceiver(this::documentDetail);
    getListAllDocumentRequestSender.registerReceiver(this::getListAllDocument);
    updateDocActionRequestSender.registerReceiver(this::updateAvailableActionOnDocument);
    editDocumentRequestSender.registerReceiver(this::editDocument);
    removeDocumentRequestSender.registerReceiver(this::removeDocument);
    borrowDocumentRequestSender.registerReceiver(this::borrowDocument);
    addDocumentRequestSender.registerReceiver(this::addDocument);
    getLastestDocumentsRequestSender.registerReceiver(this::getLastestDocByIdDesc);
    getRandomDocumentRequestSender.registerReceiver(this::randomDocument);
    getDocumentByIdRequestSender.registerReceiver(this::getDocumentById);
    commitChangeDocRequestSender.registerReceiver(this::commitChangeDoc);
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
   *
   * @param user None
   */
  private void getListAllDocument(User user) {
    try {
      List<Document> documentList = documentRetrievalService.getAllDocuments();
      docOverviewController.setTable(FXCollections.observableArrayList(
              documentList
      ));
    } catch (Exception e) {
      Alerts.showAlertWarning("Error!", e.getMessage());
    }
  }

  /**
   * Call service and get status about document then update available action
   * for that document
   *
   * @param document document which need get status
   */
  // TODO: call to service
  private void updateAvailableActionOnDocument(Document document) {
    boolean isBorrowAvailable = true; //get from service
    boolean isRemoveAvailable = true; //get from service
    docOverviewController.updateMenuContext(isBorrowAvailable, isRemoveAvailable);
  }

  /**
   * Edits the given document by setting it in the edit document controller.
   *
   * @param document the document to be edited
   */
  private void editDocument(Document document) {
    editDocumentController.setDocument(document);
  }

  /**
   * Removes the given document by deleting it using the document persistence service.
   *
   * @param document the document to be removed
   */
  private void removeDocument(Document document) {
    documentPersistenceService.delete(document.getId());
  }

  /**
   * Borrows the given document.
   *
   * @param document the document to be borrowed
   */
  private void borrowDocument(Document document) {
    lendingDetailController.setDocument(document);
  }

  /**
   * Adds a new document using the document persistence service.
   *
   * @param document the document to be added
   */
  private void addDocument(Document document) {
    try {
      documentPersistenceService.add(document);
    } catch (Exception e) {
      Alerts.showAlertWarning("Error!!!", e.getMessage());
    }
  }


  private void randomDocument(Document d) {
    menuController.setRandomBook(documentRetrievalService.getRandomDocument());
  }

  private void getLastestDocByIdDesc(SwitchScene switchScene) {
    menuController.setLastestDocuments(FXCollections.observableList(documentRetrievalService.getLatestDocuments()));
  }


  private void getDocumentById(Integer id) {
    menuController.setRandomBook(documentRetrievalService.getDocumentById(id));
  }

  /**
   * Commits changes to the specified document by updating it in the persistence service.
   * If an exception occurs during the update, a warning alert is shown with the error message.
   *
   * @param document the document to be updated.
   */
  private void commitChangeDoc(Document document) {
    try {
      documentPersistenceService.update(document);
    } catch (Exception e) {
      Alerts.showAlertWarning("Error!!!", e.getMessage());
    }
  }

}
