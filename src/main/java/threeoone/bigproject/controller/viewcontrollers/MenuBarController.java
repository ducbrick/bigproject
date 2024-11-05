package threeoone.bigproject.controller.viewcontrollers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;

/**
 * simple ahh menu bar
 * this controller controls a menubar which appears on the left of the page
 * just add <fx:include source="MenuBar.fxml" /> to the FXML file of the view and watch magic unfolds
 */
@Component
@FxmlView("MenuBar.fxml")
public class MenuBarController {
    /**
     * rq sender to switch between views
     */
    RequestSender<SwitchScene> switchSceneRequestSender;
    public MenuBarController(RequestSender<SwitchScene> switchSceneRequestSender) {
        this.switchSceneRequestSender = switchSceneRequestSender;
    }

    /**
     * buttons for views
     */
    @FXML
    private Button AddBook;

    @FXML
    private Button DocOverview;

    @FXML
    private Button YourBooks;

    @FXML
    private Button Menu;

    /**
     * method for AddBook
     */
    @FXML
    private void toAddBook() {
        switchSceneRequestSender.send(new SwitchScene(SceneName.ADD_NEW_DOC));
    }

    @FXML
    private void toYourBooks() {
        switchSceneRequestSender.send(new SwitchScene(SceneName.YOUR_BOOKS));
    }

    @FXML
    private void toDocOverview() {
        switchSceneRequestSender.send(new SwitchScene(SceneName.DOC_OVERVIEW));
    }

    @FXML
    private void toMenu() {
        switchSceneRequestSender.send(new SwitchScene(SceneName.MAIN_MENU));
    }

    /**
     * not working
     *
     */
    public void highlight(SceneName sceneName) {
        System.out.println("Highlighting button: ");
        Button button = switch (sceneName) {
            case ADD_NEW_DOC -> AddBook;
            case YOUR_BOOKS -> YourBooks;
            case DOC_OVERVIEW -> DocOverview;
            case MAIN_MENU -> Menu;
            default -> null;
        };
        button.setId("active");
    }

}
