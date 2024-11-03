package threeoone.bigproject.controller.viewcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.User;

@Component
@FxmlView("Register.fxml")
public class RegisterController implements ViewController{

  @FXML
  private Parent root;

  @FXML
  private PasswordField confirmpass;

  @FXML
  private TextField displayname;

  @FXML
  private PasswordField password;

  @FXML
  private TextField username;

  @FXML
  private Label confirmMessage;

  @FXML
  private Button signupButton;

  private RequestSender<SwitchScene> switchSceneRequestSender;
  private RequestSender<User> userRegisterRequestSender;

  /**
   * Constructor for Register Controller
   * <p>
   *   This controller handle Register view, take signup information of user and
   *   send it to service through RequestSender, switch to Login if needed.
   * </p>
   * @param switchSceneRequestSender  Request sender for switch scene request
   * @param userRegisterRequestSender Request sender for register information
   */
  public RegisterController(RequestSender<SwitchScene> switchSceneRequestSender, RequestSender<User> userRegisterRequestSender) {
    this.switchSceneRequestSender = switchSceneRequestSender;
    this.userRegisterRequestSender = userRegisterRequestSender;
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
      if(!confirmpass.getText().equals(password.getText())) {
        confirmMessage.setText("Passwords do not match");
      }
      else {
        confirmMessage.setText("");
      }
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
   * @param event the action triggered by pressing the sign-up button
   */
  @FXML
  private void pressSignUp(ActionEvent event) {
    userRegisterRequestSender.send(new User(username.getText(), password.getText(), displayname.getText()));
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
}
