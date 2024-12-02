package threeoone.bigproject.controller.viewcontrollers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.util.MenuItemFactory;

/**
 * Controller class for the Document Overview scene.
 * Handles the interaction logic for the document overview view.
 * <p>
 * Provides methods for interacting with the documents table and handling
 * double-click events to view document details.
 * </p>
 *
 * @author HUY1902
 */
@Component
@FxmlView("DocOverview.fxml")
@RequiredArgsConstructor
public class DocOverviewController implements ViewController {
  private final RequestSender<User> getListAllDocumentRequestSender;
  private final RequestSender<Document> updateDocActionRequestSender;
  private final RequestSender<ViewController> switchToEditDoc;
  private final RequestSender<ViewController> switchToDocDetail;
  private final RequestSender<ViewController> switchToMainMenu;
  private final RequestSender<ViewController> switchToAddNewDoc;
  private final RequestSender<ViewController> switchToLendingDetail;
  private final  RequestSender<ViewController> switchToPDFReader;

  private final RequestSender<Document> documentDetailRequestSender;
  private final RequestSender<Document> editDocumentRequestSender;
  private final RequestSender<Document> removeDocumentRequestSender;
  private final RequestSender<Document> borrowDocumentRequestSender;
  private final RequestSender<Document> openDocByPdfReaderRequestSender;

  @FXML
  private ContextMenu contextMenu;

  private final MenuBarController menuBarController;

  @FXML
  private Parent root;

  @FXML
  private TableColumn<Document, String> description;

  @FXML
  private TableColumn<Document, Number> number;

  @FXML
  private TableColumn<Document, String> name;

  @FXML
  private TableColumn<Document, String> uploader;

  @FXML
  private TableView<Document> table;

  @FXML
  private ListView<Document> documentList;


  @Setter
  private Document chosenDoc;

  private final int CELL_SIZE = 30;


  /**
   * Initializes the controller. Sets up the row double-click listener for the
   * document table.
   */
  public void initialize() {
    documentList.setCellFactory(param -> new ListCell<>() {
      @Override
      protected void updateItem(Document item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
          setText("ID: " + item.getId() + "; Name: " + item.getName() + "; Author: " + item.getAuthor()
          + "; Categories: " + item.getCategory());
        }
      }
    });

    documentList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        switchToDocDetail.send(null);
        documentDetailRequestSender.send(newValue);
      }
    });

    table.setRowFactory(tableview -> {
      TableRow<Document> row = new TableRow<>();
      //handle click on item
      row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && (!row.isEmpty())) {
          Document document = row.getItem();
          pressDocToGoToDetail(document);
        }
      });
      //handle show context menu
      row.setOnContextMenuRequested(event -> {
        if (!row.isEmpty()) {
          chosenDoc = row.getItem();
          updateDocActionRequestSender.send(chosenDoc);
          contextMenu.show(row, event.getScreenX(), event.getScreenY());
        }
      });
      //handle key press to show menu at true location
      row.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.CONTEXT_MENU && !row.isEmpty()) {
          contextMenu.show(row, row.getScene().getWindow().getX() + row.getLayoutX() + row.getTranslateX(),
                  row.getScene().getWindow().getY() + row.getLayoutY() + row.getTranslateY());
        }
      });
      return row;
    });
    description.setCellValueFactory(new PropertyValueFactory<>("description"));
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    number.setCellValueFactory(cellData
            -> new SimpleIntegerProperty(table.getItems().indexOf(cellData.getValue()) + 1));
    uploader.setCellValueFactory(cellData
            -> new SimpleStringProperty(cellData.getValue().getUploader().getUsername()));
    menuBarController.highlight(SceneName.DOC_OVERVIEW);

    contextMenu.getItems().add(edit());
    contextMenu.getItems().add(borrow());
    contextMenu.getItems().add(remove());
    contextMenu.getItems().add(openPDF());
  }

  /**
   * Sets the search result to the list view and displays it.
   *
   * @param result the search result to be displayed
   */
  public void setResult(ObservableList<Document> result) {
    documentList.getItems().clear();
    documentList.getItems().addAll(result);
    documentList.setPrefHeight(documentList.getItems().size() * CELL_SIZE);
  }

  /**
   * Clears the items in the documentList and sets its preferred height to 0.
   * This method is triggered by a mouse event.
   *
   * @param event the MouseEvent that triggers this method
   */
  @FXML
  private void outListView(MouseEvent event) {
    documentList.getItems().clear();
    documentList.setPrefHeight(0);
  }

  /**
   * Create open item for context menu
   *
   * @return openPDF in context menu
   */
  private MenuItem openPDF() {
    return MenuItemFactory.createMenuItem("Open PDF",
            "Open Confirmation", "Are you sure you want to open this document?",
            unused -> {
              openDocByPdfReaderRequestSender.send(chosenDoc);
              if(chosenDoc != null) {
                switchToPDFReader.send(null);
              }
            });
  }

  /**
   * Create borrow item for context menu
   *
   * @return borrow item in context menu
   */
  private MenuItem borrow() {
    return MenuItemFactory.createMenuItem("Borrow",
            "Borrow Confirmation", "Are you sure you want to borrow this document?",
            unused -> {
              switchToLendingDetail.send(null);
              borrowDocumentRequestSender.send(chosenDoc);
            });
  }


  /**
   * Creates a menu item for removing an item.
   * Displays a confirmation dialog when the menu item is selected.
   *
   * @return the menu item for removing an item
   */
  private MenuItem remove() {
    return MenuItemFactory.createMenuItem("Remove",
            "Remove Confirmation",
            "Are you sure you want to remove this document?",
            unused -> {
              removeDocumentRequestSender.send(chosenDoc);
              getListAllDocumentRequestSender.send(null);
            });
  }

  /**
   * Creates a menu item for removing an item.
   * Displays a confirmation dialog when the menu item is selected.
   *
   * @return the menu item for removing an item
   */
  private MenuItem edit() {
    return MenuItemFactory.createMenuItem("Edit",
            "Edit Confirmation",
            "Are you sure you want to edit this document?",
            unused -> {
              editDocumentRequestSender.send(chosenDoc);
              switchToEditDoc.send(null);
            });
  }


  /**
   * Sets the items in the document table with the given list.
   *
   * @param list An observable list of documents to be displayed in the table
   */
  public void setTable(ObservableList<Document> list) {
    table.setItems(list);
  }

  /**
   * Handles the event when a document is double-clicked.
   * Sends the document request and switches the scene to document details view.
   *
   * @param document The document that was double-clicked
   */
  private void pressDocToGoToDetail(Document document) {
    documentDetailRequestSender.send(document);
    switchToDocDetail.send(this);
  }

  /**
   * Handles the event when the back button is pressed.
   * Switches the scene to the menu view.
   */
  @FXML
  private void pressBack() {
    switchToMainMenu.send(this);
  }

  /**
   * Switching to Add New Doc page when press Add Doc button
   *
   * @param event event trigger add new doc button
   */
  @FXML
  private void pressAddDoc(ActionEvent event) {
    switchToAddNewDoc.send(this);
  }

  /**
   * Returns the root node of the view controlled by this controller.
   *
   * @return The root node of the view
   */
  @Override
  public Parent getParent() {
    return root;
  }

  /**
   * Notifies the controller that its view is being displayed.
   */
  @Override
  public void show() {
    getListAllDocumentRequestSender.send(null);
  }
}
