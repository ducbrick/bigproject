package threeoone.bigproject.controller.viewcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;

@Component
@FxmlView("AddDoc.fxml")
public class AddDocController implements ViewController {
  private final RequestSender<SwitchScene> switchSceneRequestSender;

  @FXML
  private Parent root;

  @FXML
  private ChoiceBox<String> categories;

  @FXML
  private DatePicker date;

  @FXML
  private TextField description;

  @FXML
  private TextField isnb;

  @FXML
  private TextField name;

  public AddDocController(RequestSender<SwitchScene> switchSceneRequestSender) {
    this.switchSceneRequestSender = switchSceneRequestSender;
  }

  public void initialize() {
    String[] categories = {"Category1", "Category2", "Category3"};
    this.categories.getItems().addAll(categories);
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

  @FXML
  public void pressSubmit(ActionEvent event) {
    System.out.println(categories.getValue());
    System.out.println(date.getValue());
    switchSceneRequestSender.send(new SwitchScene(SceneName.DOC_OVERVIEW));
  }
}
