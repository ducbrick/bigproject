package threeoone.bigproject.controller.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.User;

/**
 * @author purupurupkl
 */
@Component
@FxmlView("Menu.fxml")
public class MenuController implements ViewController {
    private final RequestSender<SwitchScene> switchSceneRequestSender;

    @FXML
    private Parent root;

    @FXML
    private Button Featured;

    @FXML
    private TableView<User> UserList;

    @FXML
    private TableColumn<User, String> UserID;

    @FXML
    private TableColumn<User, String> UserName;

    @FXML
    private TableColumn<User, Integer> BooksIssued;

    @Override
    public Parent getParent() {
        return root;
    }

    public MenuController(RequestSender<SwitchScene> switchSceneRequestSender) {
        this.switchSceneRequestSender = switchSceneRequestSender;
    }

    @Override
    public void show() {
        Featured.setText("Featured book \n Nakano Miku character book \n Very nice little book about miku.");
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
