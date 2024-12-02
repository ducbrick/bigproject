package threeoone.bigproject.controller.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.util.Alerts;
import threeoone.bigproject.view.ViewSwitcher;

@Component
@RequiredArgsConstructor
@FxmlView("ForgetPassword.fxml")
public class ForgetPasswordController implements ViewController {
    private final RequestSender<String> sendResetLinkRequestSender;
    private final RequestSender<ViewController> switchToSignup;
    private final RequestSender<ViewController> switchToLogin;

    @FXML
    private Parent root;

    @FXML
    private Button Submit;

    @FXML
    private TextField textField;

    @FXML
    private Label confirmation;

    @Setter
    private boolean flag = false;

    @FXML
    private Hyperlink login;

    @FXML
    private Hyperlink signup;

    @FXML
    private void submit() {
        sendResetLinkRequestSender.send(textField.getText());
        if (flag) {
            confirmation.setVisible(true);
        }
        else {
            Alerts.showAlertInfo("Failed", "Couldn't find any account with that username");
        }
    }

    @FXML
    private void toLogin() {
        switchToLogin.send(this);
    }

    @FXML
    private void toSignUp() {
        switchToSignup.send(this);
    }

    @Override
    public Parent getParent() {
        return root;
    }

    @Override
    public void show() {

    }
}
