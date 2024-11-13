package threeoone.bigproject.controller.viewcontrollers;

import javafx.scene.Parent;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.util.Alerts;

/**
 * Controller class for the "Edit Member" view.
 *
 * @author HUY1902
 */
@Component
@FxmlView("EditMem.fxml")
@RequiredArgsConstructor
@Setter
public class EditMemController implements ViewController {

  private final RequestSender<SwitchScene> switchSceneRequestSender;
  private final RequestSender<Member>  commitChangeMemberRequestSender;
  private Member chosenMember;

  @FXML
  private Parent root;

  @FXML
  private Label id;

  @FXML
  private TextField name;

  /**
   * Initializes the controller class. This method is automatically called
   * after the FXML file has been loaded.
   */
  @FXML
  private void initialize() {
    // Initialization code, if necessary
  }

  /**
   * Handles the action when the return button is pressed.
   *
   * @param event the event triggered when the return button is pressed
   */
  @FXML
  private void pressReturn(ActionEvent event) {
    switchSceneRequestSender.send(new SwitchScene(SceneName.MEM_LIST));
  }

  /**
   * Handles the action when the submit button is pressed.
   * If the name field is empty, an alert is shown. Otherwise, the member's name
   * is updated and the action is sent to the server.
   *
   * @param event the event triggered when the submit button is pressed
   */
  @FXML
  private void pressSubmit(ActionEvent event) {
    if (name.getText().isEmpty()) {
      Alerts.showAlertWarning("No change!", "Name text field is empty. Member name will be remained");
    } else {
      chosenMember.setName(name.getText());
    }
    commitChangeMemberRequestSender.send(chosenMember);
    switchSceneRequestSender.send(new SwitchScene(SceneName.MEM_LIST));
  }

  /**
   * Gets the root {@link #root} of the {@code View} managed by the {@link ViewController}.
   *
   * @return the root {@link #root} of the {@code View}
   */
  @Override
  public Parent getParent() {
    return root;
  }

  /**
   * Notify the {@link ViewController} that its {@code View} is displayed.
   * Sets the ID and name fields based on the chosen member.
   */
  @Override
  public void show() {
    if (chosenMember == null) {
      switchSceneRequestSender.send(new SwitchScene(SceneName.MEM_LIST));
      return;
    }
    id.setText(String.valueOf(chosenMember.getId()));
    name.setText(chosenMember.getName());
  }
}
