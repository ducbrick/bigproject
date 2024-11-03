package threeoone.bigproject.controller.viewcontrollers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.entities.User;

@Component
@FxmlView("Login.fxml")
public class LoginController implements ViewController {
  private final RequestSender<User> loginRequestSender;
  @FXML
  private Parent root;

  @FXML
  private PasswordField passwordField;

  @FXML
  private TextField usernameField;

  @FXML
  private Button loginButton;

  @FXML
  private Text message;


  /**
   * Initializes the controller class. This method is automatically called
   * after the fxml file has been loaded.
   */
  public void initialize() {
    usernameField.setPromptText("Username");
    passwordField.setPromptText("Password");
    usernameField.setOnAction(event -> passwordField.requestFocus());
    passwordField.setOnAction(event -> loginButton.fire());
  }

  /**
   * Constructs a LoginController with the specified RequestSender.
   *
   * @param loginRequestSender the RequestSender to handle login requests
   */
  public LoginController(RequestSender<User> loginRequestSender) {
    this.loginRequestSender = loginRequestSender;
  }

  /**
   * Handles the login button press action. All logic for login page now is handled by
   * {@link threeoone.bigproject.controller.controllers.UserLoginController}
   *
   * @param event the event triggered when the login button is pressed
   */
  @FXML
  void pressLogin(ActionEvent event) {
    loginRequestSender.send(new User(usernameField.getText(), passwordField.getText(), null));
  }

  /**
   * Sets the message to be displayed on the UI.
   *
   * @param message the message to be displayed
   */
  public void setMessage(String message) {
    this.message.setText(message);
  }

  /**
   * Gets the root {@link Parent} node of this controller's view.
   *
   * @return the root {@link Parent} node
   */
  @Override
  public Parent getParent() {
    return root;
  }

  /**
   * Shows the view managed by this controller.
   */
  @Override
  public void show() {

  }
}
