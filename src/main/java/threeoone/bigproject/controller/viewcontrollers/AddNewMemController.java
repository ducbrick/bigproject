package threeoone.bigproject.controller.viewcontrollers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.util.Alerts;
import threeoone.bigproject.view.ViewSwitcher;

/**
 * Controller class for the "Add New Member" view.
 */
@Component
@Setter
@RequiredArgsConstructor
@FxmlView("AddNewMem.fxml")
public class AddNewMemController implements ViewController {

  private final RequestSender<Member> addMemberRequestSender;
  private final RequestSender<ViewController> switchToMemList;

  @FXML
  private TextField address;

  @FXML
  private TextField email;

  @FXML
  private Button getID;

  @FXML
  private Label id;

  @FXML
  private TextField name;

  @FXML
  private TextField phoneNumber;

  @FXML
  private ProgressIndicator progress;

  @FXML
  private Parent root;

  @FXML
  private Button submit;

  private Member member;

  /**
   * Initializes the controller class. This method is automatically called
   * after the FXML file has been loaded.
   */
  @FXML
  private void initialize() {
    name.setOnAction(event -> phoneNumber.requestFocus());
    phoneNumber.setOnAction(event -> address.requestFocus());
    address.setOnAction(event -> email.requestFocus());
    email.setOnAction(this::pressGetID);
  }

  /**
   * Handles the action when the return button is pressed.
   *
   * @param event the event triggered when the return button is pressed
   */
  @FXML
  private void pressReturn(ActionEvent event) {
    switchToMemList.send(null);
  }

  @FXML
  private void fulfill(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER) {
      getID.fire();
    }
  }

  /**
   * Handles the action when the get ID button is pressed.
   * Sends an action request to add a new member and updates the progress indicator.
   *
   * @param event the event triggered when the get ID button is pressed
   */
  @FXML
  private void pressGetID(ActionEvent event) {
    if (name.getText().isEmpty() || phoneNumber.getText().isEmpty()
            || address.getText().isEmpty() || email.getText().isEmpty()) {
      Alerts.showAlertWarning("Not fulfill!", "You must fill required fields!");
      return;
    }
    progress.progressProperty().unbind();
    progress.setVisible(true);
    progress.setProgress(0);
    member = new Member(name.getText());
    member.setPhoneNumber(phoneNumber.getText());
    member.setAddress(address.getText());
    member.setEmail(email.getText());
    Task<Void> task = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        updateProgress(20, 100);
        addMemberRequestSender.send(member);
        updateProgress(100, 100);
        Platform.runLater(() -> {
          id.setText(member.getId().toString());
          submit.requestFocus();
        });
        return null;
      }
    };
    progress.progressProperty().bind(task.progressProperty());

    Thread thread = new Thread(task);
    thread.start();
  }

  /**
   * Handles the action when the submit button is pressed.
   * Sends a request to switch the scene to the member list.
   */
  @FXML
  private void pressSubmit() {
    switchToMemList.send(null);
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
   */
  @Override
  public void show() {
    progress.setVisible(false);
    name.setText("");
    phoneNumber.setText("");
    address.setText("");
    email.setText("");
    id.setText("");
  }
}
