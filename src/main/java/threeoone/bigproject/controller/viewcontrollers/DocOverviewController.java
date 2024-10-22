package threeoone.bigproject.controller.viewcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Document;

import javax.print.Doc;

@Component
@FxmlView("DocOverview.fxml")
public class DocOverviewController implements ViewController {
  private final RequestSender<SwitchScene> switchSceneRequestSender;
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

  public DocOverviewController(RequestSender<SwitchScene> switchSceneRequestSender) {
    this.switchSceneRequestSender = switchSceneRequestSender;
  }

  public void initialize() {
    description.setCellValueFactory(new PropertyValueFactory<>("description"));
    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    name.setCellValueFactory(new PropertyValueFactory<>("name"));

    table.setItems(list);
  }

  public void pressBack() {
    switchSceneRequestSender.send(new SwitchScene("getName"));
  }

  /**
   * @return
   */
  @Override
  public Parent getParent() {
    return root;
  }

  /**
   *
   */
  @Override
  public void show() {

  }
}
