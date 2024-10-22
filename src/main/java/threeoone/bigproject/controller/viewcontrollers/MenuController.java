package threeoone.bigproject.controller.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("Menu.fxml")
public class MenuController implements ViewController {
    @FXML
    private Parent root;

    @FXML
    private Button UserInfo;

    @FXML
    private Button Borrow;

    @FXML
    private Button YourBooks;

    @FXML
    private Label testLabel;

    @Override
    public Parent getParent() {
        return root;
    }

    @Override
    public void show() {
        testLabel.setText("Waiting for input...");
    }

    public void displayUserInfo() {
        testLabel.setText("how to fetch user info here?");
    }

    public void borrowBooks() {
        testLabel.setText("sending you to borrow page. how to do this?");
    }

    public void yourBooks() {
        testLabel.setText("sending you to yourBooks page.how to do this...");
    }
}
