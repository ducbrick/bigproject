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

  private int failedAttempts = 0;
  private static final int MAX_ATTEMPTS = 3;
  private static final int LOCKOUT_DURATION = 5; //second

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
   * Validates the information provided by the user.
   *
   * @return true if the username and password fields are not empty;
   * false otherwise
   */
  private boolean validateInformation() {
    return !(usernameField.getText().isEmpty() || passwordField.getText().isEmpty());
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
   * Handles the login button press action. Validates the user's credentials,
   * sends the login request if valid, or displays appropriate messages if invalid.
   * Implements a lockout mechanism after a number of failed attempts.
   *
   * @param event the event triggered when the login button is pressed
   */
  @FXML
  void pressLogin(ActionEvent event) {
    if (failedAttempts >= MAX_ATTEMPTS) {
      message.setText("Too many attempts. Please wait...");
      return;
    }
    if (validateInformation()) {
      failedAttempts = 0; // Reset counter
      loginRequestSender.send(new User(usernameField.getText(), passwordField.getText(), null));
    } else {
      failedAttempts++;
      message.setText("Invalid credentials. Attempt " + failedAttempts);

      if (failedAttempts >= MAX_ATTEMPTS) {
        message.setText("Too many attempts. Please wait...");

        PauseTransition pause = new PauseTransition(Duration.seconds(LOCKOUT_DURATION));
        pause.setOnFinished(e -> {
          failedAttempts = 0;
          message.setText("");
        });
        pause.play();
      }
    }
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
   *
   * @return  number of times user fail when trying to login
   */
  public int getFailedAttempts() {
    return failedAttempts;
  }

  /**
   * Set gate for setting number of user fail when trying to login
   * @param failedAttempts
   *
   */
  public void setFailedAttempts(int failedAttempts) {
    this.failedAttempts = failedAttempts;
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
