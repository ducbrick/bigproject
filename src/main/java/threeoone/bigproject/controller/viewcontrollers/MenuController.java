package threeoone.bigproject.controller.viewcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;

@Component
@FxmlView("Menu.fxml")
public class MenuController implements ViewController {
  private final RequestSender<SwitchScene> switchSceneRequestSender;
  private final MenuBarController menuBarController;
  /**
   * Temporary page controller to show features.
   * @param switchSceneRequestSender request sender for switch scene
   */
  public MenuController(RequestSender<SwitchScene> switchSceneRequestSender,
                        MenuBarController menuBarController) {
    this.switchSceneRequestSender = switchSceneRequestSender;
    this.menuBarController = menuBarController;
  }

  @FXML
  private AnchorPane root;

  public void initialize() {
    menuBarController.highlight(SceneName.MAIN_MENU);
  }
  /**
   * @return the root {@link Parent} of this view
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

  /**
   * Handles the event when the "Doc Overview" button is pressed.
   *
   * @param event the {@link ActionEvent} triggered by button press
   */
  @FXML
  void pressDocOverview(ActionEvent event) {
    switchSceneRequestSender.send(new SwitchScene(SceneName.DOC_OVERVIEW));
  }
}
