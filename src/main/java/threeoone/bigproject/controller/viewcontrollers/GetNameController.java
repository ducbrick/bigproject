package threeoone.bigproject.controller.viewcontrollers;

import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.UserInfo;

@Component
@FxmlView("GetName.fxml")
public class GetNameController implements ViewController {
  private final RequestSender <UserInfo> helloWorldRequestSender;
  @FXML
  private Parent root;
  @FXML
  private TextField nameField;
  @FXML
  private Button enterButton;
  @FXML
  private Label enterNameLabel;

  public GetNameController(RequestSender <UserInfo> helloWorldRequestSender) {
    this.helloWorldRequestSender = helloWorldRequestSender;
  }

  @Override
  public Parent getParent() {
    return root;
  }

  @Override
  public void show() {
    enterNameLabel.setText("Enter your name");
  }

  public void submit(ActionEvent event) {
    String name = nameField.getText();
    helloWorldRequestSender.send(new UserInfo(name));
  }

}
