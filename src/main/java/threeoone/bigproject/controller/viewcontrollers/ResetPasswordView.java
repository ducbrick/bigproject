package threeoone.bigproject.controller.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FxmlView("resetpassword.fxml")
public class ResetPasswordView implements ViewController {
  @FXML
  private Parent root;


  @Override
  public Parent getParent() {
    return root;
  }

  @Override
  public void show() {
  }
}
