package threeoone.bigproject.controller.viewcontrollers;

import java.util.Objects;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;

@Component
@RequiredArgsConstructor
@FxmlView("resetpassword.fxml")
public class ResetPasswordView implements ViewController {
  private final RequestSender <ViewController> switchToLogin;

  @FXML
  private Parent root;

  @FXML
  private TextField passwordField;

  @FXML
  private TextField reEnteredPasswordField;

  @FXML
  private Label errorMessage;

  @Override
  public Parent getParent() {
    return root;
  }

  public void showErrorMessage() {
    errorMessage.setVisible(true);
    errorMessage.setManaged(true);
  }

  public void hideErrorMessage() {
    errorMessage.setVisible(false);
    errorMessage.setManaged(false);
  }

  @Override
  public void show() {
    hideErrorMessage();
  }

  @FXML
  private void switchToLogin() {
    switchToLogin.send(null);
  }

  private boolean matchReEnteredPassword() {
    String password = passwordField.getText();
    String reEnteredPassword = reEnteredPasswordField.getText();
    return (Objects.equals(password, reEnteredPassword));
  }

  @FXML
  private void submitRequest() {
    if (!matchReEnteredPassword()) {
      showErrorMessage();
      return;
    }
  }
}
