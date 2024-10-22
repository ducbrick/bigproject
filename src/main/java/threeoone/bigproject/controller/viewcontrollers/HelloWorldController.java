package threeoone.bigproject.controller.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.UserInfo;

@Component
@FxmlView("HelloWorld.fxml")
public class HelloWorldController implements ViewController {
  private final RequestSender <String> menuRequestSender;

  public HelloWorldController(RequestSender<String> menuRequestSender) {
    this.menuRequestSender = menuRequestSender;
  }

  @FXML
  private Parent root;

  @FXML
  private Label message;

  @FXML
  private Button toMenu;


  @Override
  public Parent getParent() {
    return root;
  }

  @Override
  public void show() {
  }

  public void setUserName(String userName) {
    message.setText("Hello " + userName);
  }

  public void toMenuView() {
    menuRequestSender.send("troll");
  }
}
