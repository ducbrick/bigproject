package threeoone.bigproject.controller.viewcontrollers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.User;

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
  /**
   * Request sender for switching scenes
   */
  private final RequestSender<SwitchScene> switchSceneRequestSender;
  /**
   * Request sender for handling document interactions
   */
  private final RequestSender<Document> documentRequestSender;

  private final MenuBarController menuBarController;
  /**
   * Root node of the view
   */
  @FXML
  private Parent root;

  /**
   * Table column for document descriptions
   */
  @FXML
  private TableColumn<Document, String> description;

  /**
   * Table column for document IDs
   */
  @FXML
  private TableColumn<Document, Number> number;

  /**
   * Table column for document names
   */
  @FXML
  private TableColumn<Document, String> name;

  /**
   * Table column for author name
   */
  @FXML
  private TableColumn<Document, String> uploader;

  /**
   * Table view for displaying documents
   */
  @FXML
  private TableView<Document> table;


  /**
   * Constructs a new {@code DocOverviewController} with the given request senders.
   *
   * @param switchSceneRequestSender Request sender for switching scenes
   * @param documentRequestSender    Request sender for handling document interactions
   */
  public DocOverviewController(RequestSender<SwitchScene> switchSceneRequestSender,
                               RequestSender<Document> documentRequestSender,
                               MenuBarController menuBarController) {
    this.switchSceneRequestSender = switchSceneRequestSender;
    this.documentRequestSender = documentRequestSender;
    this.menuBarController = menuBarController;
  }

  /**
   * Initializes the controller. Sets up the row double-click listener for the
   * document table.
   */
  public void initialize() {
    table.setRowFactory(tableview -> {
      TableRow<Document> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && (!row.isEmpty())) {
          Document document = row.getItem();
          pressDocToGoToDetail(document);
        }
      });
      return row;
    });

    menuBarController.highlight();
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
    number.setCellValueFactory(cellData -> {
      return new SimpleIntegerProperty(table.getItems().indexOf(cellData.getValue()) + 1);
    });
    uploader.setCellValueFactory(cellData -> {
      return new SimpleStringProperty(cellData.getValue().getUploader().getDisplayName());
    });
    table.setItems(list);
  }

  /**
   * Handles the event when a document is double-clicked.
   * Sends the document request and switches the scene to document details view.
   *
   * @param document The document that was double-clicked
   */
  private void pressDocToGoToDetail(Document document) {
    documentRequestSender.send(document);
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
