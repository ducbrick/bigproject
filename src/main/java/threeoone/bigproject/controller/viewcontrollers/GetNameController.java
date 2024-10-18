package threeoone.bigproject.controller.viewcontrollers;

import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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

  public GetNameController(RequestSender <UserInfo> helloWorldRequestSender) {
    this.helloWorldRequestSender = helloWorldRequestSender;
  }

  @Override
  public Parent getParent() {
    return root;
  }

  @Override
  public void show() {
    Thread thread = new Thread(() -> {
      System.out.print("ENTER YOUR NAME: ");
      Scanner scanner = new Scanner(System.in);
      String name = scanner.nextLine();
      helloWorldRequestSender.send(new UserInfo(name));
    });
    thread.start();
  }
}
