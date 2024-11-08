package threeoone.bigproject.controller.viewcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.User;

import java.io.File;

/**
 * ViewController for Register Page
 * No logic in this class except compare
 * confirmation and password to ensure
 * User information send to userRegisterController to validate with Service
 *
 * @author HUY1902
 */
@Component
@FxmlView("Register.fxml")
public class RegisterController implements ViewController {
  @FXML
  private TextField displayname;

  @FXML
  private Label confirmMessage;

  @FXML
  private PasswordField confirmpass;

  @FXML
  private PasswordField password;

  @FXML
  private Parent root;

  @FXML
  private Button signupButton;

  @FXML
  private TextField username;

  private RequestSender<SwitchScene> switchSceneRequestSender;
  private RequestSender<User> registerRequestSender;

  /**
   * Constructor for Register Controller
   * <p>
   * This controller handle Register view, take signup information of user and
   * send it to service through RequestSender, switch to Login if needed.
   * </p>
   *
   * @param switchSceneRequestSender Request sender for switch scene request
   * @param registerRequestSender    Request sender for register information
   */
  public RegisterController(RequestSender<SwitchScene> switchSceneRequestSender, RequestSender<User> registerRequestSender) {
    this.switchSceneRequestSender = switchSceneRequestSender;
    this.registerRequestSender = registerRequestSender;
  }

  /**
   * Initializes the controller class. This method is automatically called
   * after the fxml file has been loaded.
   */
  public void initialize() {
    displayname.setOnAction(event -> username.requestFocus());
    username.setOnAction(event -> password.requestFocus());
    password.setOnAction(event -> confirmpass.requestFocus());
    confirmpass.setOnAction(event -> {
      signupButton.fire();
    });
  }

  /**
   * Handles the sign-in button press event.
   *
   * @param event the action event triggered by pressing the sign-in button
   */
  @FXML
  private void pressSignIn(ActionEvent event) {
    switchSceneRequestSender.send(new SwitchScene(SceneName.LOGIN));
  }


  /**
   * Handle the sign-up button press event
   *
   * @param event the action triggered by pressing the sign-up button
   */
  @FXML
  private void pressSignUp(ActionEvent event) {
    if(validateConfirmation()) {
      registerRequestSender.send(new User(username.getText(), password.getText()));
    }
  }

  /**
   * Gets the root {@link Parent} of the {@code View} managed by the {@link ViewController}.
   *
   * @return the root {@link Parent} of the {@code View}
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

  }

  /**
   * Set confirm Message
   *
   * @param confirmMessage alert for password confirmation
   */
  public void setConfirmMessage(String confirmMessage) {
    this.confirmMessage.setText(confirmMessage);
  }

  /**
   * Alert when user signup successfully
   */
  public void showSuccessDialog() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Registration Successful");
    alert.setHeaderText(null);
    alert.setContentText("You have registered successfully!\nPlease log in to your account");
    alert.showAndWait();
    alert.close();
  }

  /**
   * Reset text of all alert message
   */
  public void resetAlert() {
    this.setConfirmMessage("");
  }

  /**
   * Compare confirmation and password
   * @return true if equal; otherwise, false
   */
  private boolean validateConfirmation() {
    if (!confirmpass.getText().equals(password.getText())) {
      confirmMessage.setText("Passwords do not match");
      return false;
    } else {
      confirmMessage.setText("");
      return true;
    }
  }
}
