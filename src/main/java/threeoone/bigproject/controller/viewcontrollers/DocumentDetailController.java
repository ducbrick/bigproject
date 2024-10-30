package threeoone.bigproject.controller.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.entities.Document;

/**
 * TODO: make a borrow button for books that the user is not borrowing (inject this info)
 * This controller manages the "Document detail" view.
 * This view presents the information of a book, currently including:
 *      The cover of the book (if it exists)
 *      The name and author of the book
 *      An overview about the book's content (the description)
 *
 */
@Component
@FxmlView("DocDetail.fxml")
public class DocumentDetailController implements ViewController {
    private Document document;
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

    /**
     * set the private {@code Document} ref to the Document sent by the {@code RequestSender}
     * @param document the Document sent
     */
    public void setDocument(Document document) {
        this.document = document;
    }

    public void show() {
        bookName.setText("Name:" + document.getName());
        bookDescription.setText("Description:" + document.getDescription());
        coverImage = new Image(getClass().getResourceAsStream("三玖.jpg"));
        cover.setImage(coverImage);
        cover.setPreserveRatio(false);
    }
}
