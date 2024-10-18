package threeoone.bigproject.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.viewcontrollers.ViewController;

@Component
public class ViewSwitcher {
  private Scene scene;

  public void setStage(Stage stage, ViewController startController) {
    Parent root = startController.getParent();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    startController.show();
  }

  public void switchToView(ViewController controller) {
    Parent root = controller.getParent();
    scene.setRoot(root);
    controller.show();
  }
}
