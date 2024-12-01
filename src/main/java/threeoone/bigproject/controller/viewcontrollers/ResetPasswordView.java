package threeoone.bigproject.controller.viewcontrollers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import java.util.Objects;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.entities.User;

@Component
@RequiredArgsConstructor
@FxmlView("resetpassword.fxml")
public class ResetPasswordView implements ViewController {
  private final Validator validator;

  private final RequestSender <ViewController> switchToLogin;
  private final RequestSender <User> resetPasswordRequestSender;

  @Valid
  private User user;

  @FXML
  private Parent root;

  @FXML
  private Label pageLabel;

  @FXML
  private TextField enteredPasswordField;

  @FXML
  private TextField reEnteredPasswordField;

  @FXML
  private Label errorMessage;

  @FXML
  private void initialize() {
    hideErrorMessage();
  }

  @Override
  public Parent getParent() {
    return root;
  }

  private void showErrorMessage() {
    errorMessage.setVisible(true);
    errorMessage.setManaged(true);
  }

  private void hideErrorMessage() {
    errorMessage.setVisible(false);
    errorMessage.setManaged(false);
  }

  private void setPageLabel() {
    String label = "Reset password for account ";

    if (user != null) {
      label = label + user.getUsername();
    }

    pageLabel.setText(label);
  }

  @Override
  public void show() {
    setPageLabel();
  }

  public void setUser(@Valid @NonNull User user) {
    Set <ConstraintViolation <@NonNull User>> violations = validator.validate(user);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }

    this.user = user;
  }

  @FXML
  private void switchToLogin() {
    switchToLogin.send(null);
  }

  private String getEnteredPassword() {
    return enteredPasswordField.getText();
  }

  private String getReEnteredPassword() {
    return reEnteredPasswordField.getText();
  }

  private boolean matchPassword() {
    return Objects.equals(getEnteredPassword(), getReEnteredPassword());
  }

  @FXML
  private void submitRequest() {
    if (!matchPassword()) {
      showErrorMessage();
      return;
    }

    hideErrorMessage();

    user.setPassword(getEnteredPassword());
    resetPasswordRequestSender.send(user);
  }
}
