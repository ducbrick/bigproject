package threeoone.bigproject.controller.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Document;

/**
 * TODO: injects information on which books are borrowed
 * This view display the documents the user is borrowing
 * For now it's a Table that shows the following information of a document:
 * ID, Name, Description, Author, Uploader, BorrowDate and ReturnDate.
 * This view also allows an user to see the details of any book
 * by double clicking on that book's row on the table.
 */
@Component
@FxmlView("YourBooks.fxml")
public class YourBooksController implements ViewController {
    /**
     * {@code RequestSender} to send {@code Document} to {@code DocumentDetail}
     */
    private final RequestSender<Document> documentDetailRequestSender;

    private final RequestSender<SwitchScene> switchSceneRequestSender;
    public YourBooksController(RequestSender<Document> documentDetailRequestSender,
                               RequestSender<SwitchScene> switchSceneRequestSender,
                               MenuBarController menuBarController) {
        this.documentDetailRequestSender = documentDetailRequestSender;
        this.menuBarController = menuBarController;
        
    }

    @FXML
    private Parent root;

    /**
     * A TableView to display documents
     */
    @FXML
    private TableView<Document> books;

    @FXML
    private TableColumn<Document, String> ID;

    @FXML
    private TableColumn<Document, String> Name;

    @FXML
    private TableColumn<Document, String> Desc;

    @FXML
    private TableColumn<Document, String> Author;

    @FXML
    private TableColumn<Document, String> Uploader;

    @FXML
    private TableColumn<Document, String> BorrowDate;

    @FXML
    private TableColumn<Document, String> ReturnDate;

    /**
     * Sets up a double-click listener for every row
     *
     */
    public void initialize() {
        // Set up the columns with entity properties
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Desc.setCellValueFactory(new PropertyValueFactory<>("description"));

        books.setRowFactory(tableview -> {
            TableRow<Document> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Document document = row.getItem();
                    goToDocDetail(document);
                }
            });
            return row;
        });
        menuBarController.highlight(SceneName.YOUR_BOOKS);

    }


    /**
     * Sends a document request when a document is selected (currently by double-clicking on its row)
     * @param document the document selected
     */
    private void goToDocDetail(Document document) {
        documentDetailRequestSender.send(document);
        switchSceneRequestSender.send(new SwitchScene(SceneName.DOC_DETAIL));
    }

    @Override
    public Parent getParent() {
        return root;
    }

    @Override
    public void show(){
    }

}