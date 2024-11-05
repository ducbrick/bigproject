package threeoone.bigproject.controller.viewcontrollers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;

/**
 * simple ahh menu bar.
 * <p>
 *    This controller controls a menubar which appears on the left of the page.
 *  It has 4 buttons to: Menu, Add Document, Your Documents and Document Overview (called Borrow)
 * </p>
 * The menubar is defined in {@code Menubar.fxml}. To include the menubar in a view,
 * use fx:include on the FXML of that view. This creates NEW objects for that view's FXML
 * file. <br>
 * this method use general.css style
 * TODO: expandable menu (with css?)
 */
@Component
@FxmlView("MenuBar.fxml")
public class MenuBarController {
    /**
     * request sender to switch between views
     */
    RequestSender<SwitchScene> switchSceneRequestSender;
    public MenuBarController(RequestSender<SwitchScene> switchSceneRequestSender) {
        this.switchSceneRequestSender = switchSceneRequestSender;
    }
    @FXML
    private VBox box;
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
     * actual methods to change view
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


    /** highlight one button according to the {@code SceneName}.<br>
     * For this method to actually works,
     * inject the MenuBarController into the view's controller and run the method there <br>
     * I'm sorry it turned out like this.
     */
    public void highlight(SceneName sceneName) {
        Button button = switch (sceneName) {
            case ADD_NEW_DOC -> AddBook;
            case YOUR_BOOKS -> YourBooks;
            case DOC_OVERVIEW -> DocOverview;
            case MAIN_MENU -> Menu;
            default -> new Button();
        };
        button.setId("active");
    }

}
