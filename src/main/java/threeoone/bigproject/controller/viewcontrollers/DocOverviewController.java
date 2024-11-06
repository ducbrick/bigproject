package threeoone.bigproject.controller.viewcontrollers;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.util.Alerts;

import java.util.*;

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
public class DocOverviewController implements ViewController {
  private final RequestSender<User> getListAllDocumentRequestSender;
  private final RequestSender<Document> updateDocActionRequestSender;
  private final RequestSender<SwitchScene> switchSceneRequestSender;

  private final RequestSender<Document> documentDetailRequestSender;

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


  /**
   * Constructs a new {@code DocOverviewController} with the given request senders.
   *
   * @param switchSceneRequestSender    Request sender for switching scenes
   * @param documentDetailRequestSender Request sender for handling document interactions
   * @param getListAllDocumentRequestSender Request sender for set up table
   * @param updateDocActionRequestSender Request sender for update context menu
   */
  public DocOverviewController(RequestSender<User> getListAllDocumentRequestSender,
                               RequestSender<Document> updateDocActionRequestSender,
                               RequestSender<SwitchScene> switchSceneRequestSender,
                               RequestSender<Document> documentDetailRequestSender,
                               MenuBarController menuBarController) {
    this.getListAllDocumentRequestSender = getListAllDocumentRequestSender;
    this.updateDocActionRequestSender = updateDocActionRequestSender;
    this.switchSceneRequestSender = switchSceneRequestSender;
    this.documentDetailRequestSender = documentDetailRequestSender;
    this.menuBarController = menuBarController;
  }

  /**
   * Initializes the controller. Sets up the row double-click listener for the
   * document table.
   */
  public void initialize() {
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
          updateDocActionRequestSender.send(row.getItem());
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
    menuBarController.highlight(SceneName.DOC_OVERVIEW);
    getListAllDocumentRequestSender.send(new User());
  }

  /**
   * update context menu follow which action is available for document now
   *
   * @param isBorrowAvailable can be borrowed
   * @param isRemoveAvailable can be removed
   */
  public void updateMenuContext(boolean isBorrowAvailable, boolean isRemoveAvailable) {
    contextMenu.getItems().clear();
    if (isBorrowAvailable) {
      contextMenu.getItems().add(borrow());
    }
    if (isRemoveAvailable) {
      contextMenu.getItems().add(remove());
    }
  }

  /**
   * Create borrow item for context menu
   *
   * @return borrow item in context menu
   */
  private MenuItem borrow() {
    MenuItem item = new MenuItem("Borrow");
    item.setOnAction(event -> {
      Alert alert = Alerts.alertConfirmation("Borrow Confirmation",
              "Are you sure you want to borrow this item?");
      alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
          // Handle the borrow action
        }
      });
    });
    return item;
  }


  /**
   * Creates a menu item for removing an item.
   * Displays a confirmation dialog when the menu item is selected.
   *
   * @return the menu item for removing an item
   */
  private MenuItem remove() {
    MenuItem item = new MenuItem("Remove");
    item.setOnAction(event -> {
      Alert alert = Alerts.alertConfirmation("Remove Confirmation",
              "Are you sure you want to remove this item?");
      alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
          // Handle the remove action
        }
      });
    });
    return item;
  }

  @FXML
  private void pressSearch() {
    switchSceneRequestSender.send(new SwitchScene(SceneName.SEARCH));
  }


  /**
   * Sets the items in the document table with the given list.
   * String arguments like: description,  name in `PropertyValueFactory` is attribute of {@link Document}
   * Number is number of row in table. Documents on table must be different, if not number will have duplicate.
   * Uploader is a user object. Using a callback to extract displayName from Uploader in Document.
   *
   * @param list An observable list of documents to be displayed in the table
   */
  public void setTable(ObservableList<Document> list) {
    description.setCellValueFactory(new PropertyValueFactory<>("description"));
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    number.setCellValueFactory(cellData
            -> new SimpleIntegerProperty(table.getItems().indexOf(cellData.getValue()) + 1));
    uploader.setCellValueFactory(cellData
            -> new SimpleStringProperty(cellData.getValue().getUploader().getDisplayName()));
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
    switchSceneRequestSender.send(new SwitchScene(SceneName.DOC_DETAIL));
  }

  /**
   * Handles the event when the back button is pressed.
   * Switches the scene to the menu view.
   */
  @FXML
  private void pressBack() {
    switchSceneRequestSender.send(new SwitchScene(SceneName.MAIN_MENU));
  }

  /**
   * Switching to Add New Doc page when press Add Doc button
   *
   * @param event event trigger add new doc button
   */
  @FXML
  private void pressAddDoc(ActionEvent event) {
    switchSceneRequestSender.send(new SwitchScene(SceneName.ADD_NEW_DOC));
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
  }
}
