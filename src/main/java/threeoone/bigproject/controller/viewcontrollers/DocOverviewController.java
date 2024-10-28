package threeoone.bigproject.controller.viewcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Document;

import javax.print.Doc;

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
  private TableColumn<Document, Integer> id;

  /**
   * Table column for document names
   */
  @FXML
  private TableColumn<Document, String> name;

  /**
   * Table column for author name
   */
  @FXML
  private TableColumn<Document, String> author;

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
                               RequestSender<Document> documentRequestSender) {
    this.switchSceneRequestSender = switchSceneRequestSender;
    this.documentRequestSender = documentRequestSender;
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
  }

  /**
   * Sets the items in the document table with the given list.
   * String arguments like: description, id, author, name in `PropertyValueFactory` is attribute of {@link Document}
   * @param list An observable list of documents to be displayed in the table
   */
  public void setTable(ObservableList<Document> list) {
    description.setCellValueFactory(new PropertyValueFactory<>("description"));
    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    author.setCellValueFactory(new PropertyValueFactory<>("author"));
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
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
  public void pressBack() {
    switchSceneRequestSender.send(new SwitchScene(SceneName.MENU));
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
