package threeoone.bigproject.controller.viewcontrollers;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.LendingDetail;
import threeoone.bigproject.entities.Member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private final RequestSender<ViewController> switchToLendingDetail;
    private final RequestSender<Document> lendingDetailRequestSender;

    private Document document;

    @FXML
    private Parent root;

    @FXML
    private ImageView cover;

    @FXML
    private Label bookName;

    @FXML
    private Label author;

    @FXML
    private Label category;

    @FXML
    private Label bookDescription;

    @FXML
    private Label uploader;

    @FXML
    private Label ISBN;

    @FXML
    private Label uploadedDate;

    @FXML
    private Label copies;

    @FXML
    private Button borrow;

    @FXML
    private TableView<LendingDetail> lendings;

    @FXML
    private TableColumn<LendingDetail, String> MemberName;

    @FXML
    private TableColumn<LendingDetail, String> BorrowDate;

    public Parent getParent() {
        return root;
    }

    public void initialize() {
        MemberName.setCellValueFactory(cellData -> {
            Member member = cellData.getValue().getMember();
            return new SimpleStringProperty(member.getName());
        });

        BorrowDate.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy || hh:mm:ss");
            LocalDateTime date = cellData.getValue().getLendTime();
            return new SimpleStringProperty(date.format(formatter));
        });
    }

    /**
     * set the private {@code Document} ref to the Document sent by the {@code RequestSender}
     * @param document the Document sent
     */
    public void setDocument(Document document) {
        this.document = document;
    }

    @FXML
    private void toBorrow() {
        lendingDetailRequestSender.send(document);
        switchToLendingDetail.send(null); //don't need this data
    }

    /**
     * show the selected document
     * TODO: show default "loading" when document info is not loaded
     */
    @Override
    public void show() {
        Platform.runLater(() -> {
            bookName.setText(document.getName());
            author.setText(document.getAuthor());
            bookDescription.setText(document.getDescription());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy | HH:mm:ss");
            uploadedDate.setText(document.getUploadTime().format(formatter));
            category.setText(document.getCategory());
            uploader.setText(document.getUploader().getUsername());
            ISBN.setText(document.getIsbn());
            copies.setText(document.getCopies().toString());
            Image coverImage;
            coverImage = new Image(getClass().getResourceAsStream("三玖.jpg"));
            cover.setImage(coverImage);
        });

        ObservableList<LendingDetail> lendingDetail =
                FXCollections.observableList(document.getLendingDetails());
        lendings.setItems(lendingDetail);

    }
}
