package threeoone.bigproject.controller.viewcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;

/**
 * A controller responsible for managing the menu view defined in the {@code Menu.fxml} file.
 * This class interacts with the view components and handles user events to switch scenes.
 * An instance of this class is created and managed by the Spring framework.
 * The class implements the {@link ViewController} interface to allow integration with the application's
 * view management system.
 * Now, it just a page to show pages which I have done more easily
 * @author HUY1902
 */
@Component
@FxmlView("Menu.fxml")
public class MenuController implements ViewController {
  private final RequestSender<SwitchScene> switchSceneRequestSender;

  @FXML
  private AnchorPane root;

  /**
   * Constructs a new {@code MenuController} with the specified {@link RequestSender}.
   *
   * @param switchSceneRequestSender the {@link RequestSender} for switching scenes
   */
  public MenuController(RequestSender<SwitchScene> switchSceneRequestSender) {
    this.switchSceneRequestSender = switchSceneRequestSender;
  }

  /**
   * Returns the root {@link Parent} of this view.
   *
   * @return the root {@link Parent} of this view
   */
  @Override
  public Parent getParent() {
    return root;
  }

  /**
   * Notifies this controller that its view is displayed.
   * This method can be overridden to add custom behavior when the view is shown.
   */
  @Override
  public void show() {
    // Custom behavior when view is displayed
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
