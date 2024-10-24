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

@Component
@FxmlView("DocOverview.fxml")
public class DocOverviewController implements ViewController {
  private final RequestSender<SwitchScene> switchSceneRequestSender;
  private final RequestSender<Document> documentRequestSender;
  @FXML
  private Parent root;

  @FXML
  private TableColumn<Document, String> description;

  @FXML
  private TableColumn<Document, Integer> id;

  @FXML
  private TableColumn<Document, String> name;

  @FXML
  private TableView<Document> table;

  ObservableList<Document> list = FXCollections.observableArrayList(
          new Document("Head-first", "bleble"),
          new Document("Head-second", "blublu"),
          new Document("Head-third", "nicenice")
  );

  /**
   * Constructor for DocOverviewController.
   *
   * @param switchSceneRequestSender the RequestSender for switching scenes
   * @param documentRequestSender    the RequestSender for documents
   */
  public DocOverviewController(RequestSender<SwitchScene> switchSceneRequestSender,
                               RequestSender<Document> documentRequestSender) {
    this.switchSceneRequestSender = switchSceneRequestSender;
    this.documentRequestSender = documentRequestSender;
  }

  /**
   * Initializes the controller class. This method is automatically called
   * after the FXML file has been loaded.
   */
  public void initialize() {
    description.setCellValueFactory(new PropertyValueFactory<>("description"));
    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    name.setCellValueFactory(new PropertyValueFactory<>("name"));

    table.setItems(list);

    table.setRowFactory(tableview -> {
      TableRow<Document> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && (!row.isEmpty())) {
          Document document = row.getItem();
          pressDocToGoToDetail(document);
          System.out.println(document.getName());
        }
      });
      return row;
    });
  }

  /**
   * Method to be called when a document is clicked to go to its detail view.
   *
   * @param document the Document to be displayed in detail
   */
  private void pressDocToGoToDetail(Document document) {
    new Thread(() -> {
      documentRequestSender.send(document);
      switchSceneRequestSender.send(new SwitchScene(SceneName.DOC_DETAIL));
    }).start();
  }

  /**
   * Method to handle the press of the back button.
   */
  public void pressBack() {
    switchSceneRequestSender.send(new SwitchScene(SceneName.GET_NAME));
  }

  /**
   * Method to handle the press of the new book button.
   *
   * @param event the ActionEvent triggered by the button press
   */
  @FXML
  void pressNewBook(ActionEvent event) {
    switchSceneRequestSender.send(new SwitchScene(SceneName.ADD_DOC));
  }

  /**
   * Gets the root node of the view managed by this controller.
   *
   * @return the root Parent node
   */
  @Override
  public Parent getParent() {
    return root;
  }

  /**
   * Called when the view is shown.
   */
  @Override
  public void show() {

  }
}
