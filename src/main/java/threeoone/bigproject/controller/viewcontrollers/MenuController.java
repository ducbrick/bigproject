package threeoone.bigproject.controller.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;

/**
 * @author purupurupkl
 */
@Component
@FxmlView("Menu.fxml")
public class MenuController implements ViewController {
    private final RequestSender<SwitchScene> switchSceneRequestSender;

    @FXML
    private AnchorPane root;

    /**
     * "document overview" for borrowing?
     */
    @FXML
    private Button DocumentOverview;

    /**
     *"Your Books" page
     */
    @FXML
    private Button BorrowedBooks;

    @FXML
    private Button Featured;

    @FXML
    private Button Random;

    @Override
    public Parent getParent() {
        root.setPrefSize(1200, 800);
        return root;
    }

    public MenuController(RequestSender<SwitchScene> switchSceneRequestSender) {
        this.switchSceneRequestSender = switchSceneRequestSender;
    }

    @Override
    public void show() {
        Featured.setText("Featured book \n Nakano Miku character book \n Very nice little book about miku.");
    }

    private void displayUserInfo() {

    }

    private void borrowBooks() {

    }

    private void yourBooks() {
        switchSceneRequestSender.send(new SwitchScene(SceneName.YOUR_BOOKS));
    }

    /**
     * generate a random book
     */
    private void randomBook() {

    }

    /**
     *
     */
    private void featuredBook() {

    }
}
