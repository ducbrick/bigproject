package threeoone.bigproject.controller.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Document;
import javafx.event.ActionEvent;

@Component
@FxmlView("DocDetail.fxml")
public class DocumentDetailController implements ViewController {
    private final RequestSender<SwitchScene> sceneNameRequestSender;
    Document document;
    @FXML
    private Parent root;

    @FXML
    private ImageView cover;


    private Image coverImage;

    @FXML
    private Label bookName;

    @FXML
    private Label bookDescription;

  public DocumentDetailController(RequestSender<SwitchScene> sceneNameRequestSender) {
    this.sceneNameRequestSender = sceneNameRequestSender;
  }

  public Parent getParent() {
        return root;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @FXML
    void pressBackDocOverview(ActionEvent event) {
        sceneNameRequestSender.send(new SwitchScene(SceneName.DOC_OVERVIEW));
    }

    public void show() {
        bookName.setText(document.getName());
        bookDescription.setText(document.getDescription());
        coverImage = new Image(getClass().getResourceAsStream("三玖.jpg"));
        cover.setImage(coverImage);
    }
}
