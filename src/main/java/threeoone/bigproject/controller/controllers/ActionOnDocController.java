package threeoone.bigproject.controller.controllers;

import javafx.collections.FXCollections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.controller.viewcontrollers.*;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.LendingDetail;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.services.persistence.DocumentPersistenceService;
import threeoone.bigproject.services.retrieval.DocumentRetrievalService;
import threeoone.bigproject.util.Alerts;

import java.util.List;

/**
 * Interacts with view controllers and services to handle logic for document manipulation.
 * Retrieves information from services and sends actions to views based on that information.
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
  private final PDFReaderController pdfReaderController;
  private final AddNewDocController addNewDocController;

  /**
   * Registers request receivers for document handling.
   *
   * @param documentDetailRequestSender      request sender for document details
   * @param getListAllDocumentRequestSender  request sender to get all documents
   * @param updateDocActionRequestSender     request sender to update document actions
   * @param getDocumentByIdRequestSender     request sender to get document by ID
   * @param getLastestDocumentsRequestSender request sender to get the latest documents
   * @param getRandomDocumentRequestSender   request sender to get a random document
   * @param editDocumentRequestSender        request sender to edit a document
   * @param removeDocumentRequestSender      request sender to remove a document
   * @param borrowDocumentRequestSender      request sender to borrow a document
   * @param addDocumentRequestSender         request sender to add a new document
   * @param commitChangeDocRequestSender     request sender to commit changes to a document
   * @param openDocByPdfReaderRequestSender  request sender to open a document in the PDF reader
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
          RequestSender<Document> commitChangeDocRequestSender,
          RequestSender<Document> lendingDetailRequestSender,
          RequestSender<Document> openDocByPdfReaderRequestSender) {
    documentDetailRequestSender.registerReceiver(this::documentDetail);
    getListAllDocumentRequestSender.registerReceiver(this::getListAllDocument);
    editDocumentRequestSender.registerReceiver(this::editDocument);
    removeDocumentRequestSender.registerReceiver(this::removeDocument);
    borrowDocumentRequestSender.registerReceiver(this::borrowDocument);
    addDocumentRequestSender.registerReceiver(this::addDocument);
    getLastestDocumentsRequestSender.registerReceiver(this::getLastestDocByIdDesc);
    getRandomDocumentRequestSender.registerReceiver(this::randomDocument);
    getDocumentByIdRequestSender.registerReceiver(this::getDocumentById);
    commitChangeDocRequestSender.registerReceiver(this::commitChangeDoc);
    lendingDetailRequestSender.registerReceiver(this::lendingDetail);
    openDocByPdfReaderRequestSender.registerReceiver(this::openDocByPDFReader);
  }

  /**
   * Opens a document in the PDF reader.
   * If an error occurs, this method showing alerts
   * and stay in Doc Overview page
   *
   * @param document the document to be opened
   */
  private void openDocByPDFReader(Document document) {
    try {
      pdfReaderController.setDocument(document);
    } catch (Exception e) {
      Alerts.showAlertWarning("Error!!!", e.getMessage());
      docOverviewController.setChosenDoc(null);
    }
  }

  /**
   * Handles the reception of a document detail request and forwards the document
   * to the DocumentDetailController.
   *
   * @param document the document whose details are to be shown
   */
  private void documentDetail(Document document) {
    Document temp = documentRetrievalService.getDocumentWithLendingDetails(document.getId());
    documentDetailController.setDocument(temp);
  }

  /**
   * Returns a list of all documents in the dataset.
   *
   * @param user the user requesting the list (not used)
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
   * Used in "Document detail" to send document information to "Lending detail" page.
   * @param document the document currently shown in "Document detail"
   */
  private void lendingDetail(Document document) {
    lendingDetailController.setDocument(document);
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
    try {
      documentPersistenceService.delete(document.getId());
    } catch (Exception e) {
      Alerts.showAlertWarning("Error!", e.getMessage());
    }
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
      addNewDocController.setDocument(documentPersistenceService.add(document));
    } catch (Exception e) {
      Alerts.showAlertWarning("Error!!!", e.getMessage());
    }
  }

  /**
   * Randomly selects a document and sets it in the menu controller.
   *
   * @param d the document (not used)
   */
  private void randomDocument(Document d) {
    menuController.setRandomBook(documentRetrievalService.getRandomDocument());
  }

  /**
   * Sets the latest documents in the menu controller based on ID in descending order.
   *
   * @param switchScene the switch scene
   */
  private void getLastestDocByIdDesc(SwitchScene switchScene) {
    menuController.setLastestDocuments(
            FXCollections.observableList(documentRetrievalService.getLatestDocuments())
    );
  }

  /**
   * Retrieves a document by its ID and sets it in the menu controller.
   *
   * @param id the ID of the document to be retrieved
   */
  private void getDocumentById(Integer id) {
    menuController.setRandomBook(documentRetrievalService.getDocumentById(id));
  }

  /**
   * Commits changes to the specified document by updating it in the persistence service.
   * If an exception occurs during the update, a warning alert is shown with the error message.
   *
   * @param document the document to be updated
   */
  private void commitChangeDoc(Document document) {
    try {
      documentPersistenceService.update(document);
    } catch (Exception e) {
      Alerts.showAlertWarning("Error!!!", e.getMessage());
    }
  }

}
