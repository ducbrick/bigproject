package threeoone.bigproject.controller.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("HelloWorld.fxml")
public class HelloWorldController implements ViewController {
  @FXML
  private Parent root;

  @FXML
  private Label message;

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
}
