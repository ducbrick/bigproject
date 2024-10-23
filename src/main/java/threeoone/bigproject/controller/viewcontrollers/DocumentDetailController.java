package threeoone.bigproject.controller.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.entities.Document;

@Component
@FxmlView("DocDetail.fxml")
public class DocumentDetailController implements ViewController {
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

    public Parent getParent() {
        return root;
    }

    public void show() {
        document = new Document("Miku character book", "very nice Miku character book");
        bookName.setText(document.getName());
        bookDescription.setText(document.getDescription());
        coverImage = new Image(getClass().getResourceAsStream("三玖.jpg"));
        cover.setImage(coverImage);
    }
}
