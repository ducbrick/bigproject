package threeoone.bigproject.controller.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("UserDetails.fxml")
public class UserDetailsController implements ViewController{

    @FXML
    private Parent root;

    @FXML
    private Text Name;

    @FXML
    private Text Address;

    @FXML
    private Text Age;

    @FXML
    private Text ID;

    @Override
    public Parent getParent() {
        return root;
    }

    @Override
    public void show() {

    }
}
