package threeoone.bigproject.controller.viewcontrollers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Document;

/**
 * TODO: if book is not borrowed, presents a borrow button
 * TODO: return button to the last view (e.g. if accessing book details from "your books", return to "your books")
 * This controller manages the "Document detail" view.
 * This view presents the information of a book, currently including:
 *      The cover of the book (if it exists)
 *      The name and author of the book
 *      An overview about the book's content (the description)
 *
 */
@Component
@FxmlView("DocDetail.fxml")
@RequiredArgsConstructor
public class DocumentDetailController implements ViewController {
    private final RequestSender<ViewController> switchToYourBooks;

    private Document document;

    @FXML
    private Parent root;

    @FXML
    private ImageView cover;

    @FXML
    private Label bookName;

    @FXML
    private Label bookDescription;

    @FXML
    private Label uploader;

    @FXML
    private Button returnToLast;

    public Parent getParent() {
        return root;
    }

    public void initialize() {

    }

    /**
     * set the private {@code Document} ref to the Document sent by the {@code RequestSender}
     * @param document the Document sent
     */
    public void setDocument(Document document) {
        this.document = document;
    }

    public void returnToLast() {
        switchToYourBooks.send(null);
    }

    /**
     * show the selected document
     * TODO: show default "loading" when document info is not loaded
     */
    @Override
    public void show() {
        Platform.runLater(() -> {
            bookName.setText("Name:" + document.getName());
            bookDescription.setText("Description:" + document.getDescription());
            uploader.setText("Uploader:" + document.getUploader());
            Image coverImage;
            coverImage = new Image(getClass().getResourceAsStream("三玖.jpg"));
            cover.setImage(coverImage);
        });
    }
}
