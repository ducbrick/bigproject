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
    public MenuController(RequestSender<SwitchScene> switchSceneRequestSender) {
        this.switchSceneRequestSender = switchSceneRequestSender;
    }

    @FXML
    private AnchorPane root;

    @FXML
    private Button UserInfo;

    @FXML
    private Button Borrow;

    @FXML
    private Button YourBooks;

    @FXML
    private Label testLabel;
  /**
   * @return the root {@link Parent} of this view
   */
  @Override
  public Parent getParent() {
    return root;
  }

    @Override
    public void show() {
        testLabel.setText("Waiting for input...");
    }

    public void displayUserInfo() {
        testLabel.setText("how to fetch user info here?");
    }

    public void borrowBooks() {
        testLabel.setText("sending you to borrow page. how to do this?");
    }

    public void yourBooks() {
        testLabel.setText("sending you to yourBooks page.how to do this...");
        switchSceneRequestSender.send(new SwitchScene(SceneName.YOUR_BOOKS));
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
