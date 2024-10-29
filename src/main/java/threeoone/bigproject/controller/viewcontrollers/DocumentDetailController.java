package threeoone.bigproject.controller.viewcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Document;

/**
 * Controller for the document detail view.
 * Manages the display of document details in the associated FXML view.
 * This is my temporary Document Detail to show how DocOverview works.
 * It can be replaced then.
 * @author HUY1902
**/
@Component
@FxmlView("DocDetail.fxml")
public class DocumentDetailController implements ViewController {
    private final RequestSender<SwitchScene> switchSceneRequestSender;
    private Document document;

    @FXML
    private Parent root;

    @FXML
    private Label bookName;

    @FXML
    private Label bookDescription;

  public DocumentDetailController(RequestSender<SwitchScene> switchSceneRequestSender) {
    this.switchSceneRequestSender = switchSceneRequestSender;
  }

  /**
     * Gets the root {@link Parent} node of the view.
     *
     * @return the root {@link Parent} node
     */
    public Parent getParent() {
        return root;
    }

    /**
     * Sets the document to be displayed.
     *
     * @param document the {@link Document} to display
     */
    public void setDocument(Document document) {
        this.document = document;
    }

    /**
     * Initializes the view elements with the document data.
     */
    public void show() {
        bookName.setText(document.getName());
        bookDescription.setText(document.getDescription());
    }

    /**
     * Handles the action event when the "Back to Overview" button is pressed.
     *
     * @param event the action event that triggered this method
     */
    @FXML
    void pressBackOverview(ActionEvent event) {
        switchSceneRequestSender.send(new SwitchScene(SceneName.DOC_OVERVIEW));
    }
}
