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

    /**
     * method for AddBook
     */
    @FXML
    private void toAddBook() {
        highlight(AddBook);
        switchSceneRequestSender.send(new SwitchScene(SceneName.ADD_NEW_DOC));

    }

    @FXML
    private void toYourBooks() {
        switchSceneRequestSender.send(new SwitchScene(SceneName.YOUR_BOOKS));
        highlight(YourBooks);
    }

    @FXML
    private void toDocOverview() {
        switchSceneRequestSender.send(new SwitchScene(SceneName.DOC_OVERVIEW));
        highlight(DocOverview);
    }

    /**
     * not working
     * @param active
     */
    @FXML
    private void highlight(Button active) {
        System.out.println("Highlighting button: " + active.getText());
        active.setId("active");
    }

}
