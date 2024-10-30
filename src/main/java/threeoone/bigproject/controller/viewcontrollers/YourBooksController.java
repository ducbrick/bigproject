package threeoone.bigproject.controller.viewcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * TODO: injects information from user
 */
@Component
@FxmlView("YourBooks.fxml")
public class YourBooksController implements ViewController {
    /**
     * {@code RequestSender} to send {@code Document} to {@code DocumentDetail}
     */
    private final RequestSender<Document> documentRequestSender;

    private final RequestSender<SwitchScene> switchSceneRequestSender;
    public YourBooksController(RequestSender<Document> documentRequestSender, RequestSender<SwitchScene> switchSceneRequestSender) {
        this.documentRequestSender = documentRequestSender;
        this.switchSceneRequestSender = switchSceneRequestSender;
    }

    @FXML
    private Parent root;

    /**
     * table displaying books
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
    private ObservableList<Document> list = FXCollections.observableArrayList(
            new Document("Miku Nakano character book", "holy hell"),
            new Document("Nino Nakano character book", "skibidi"),
            new Document("Gang of Four design pattern", "too long must read")
    );

    @FXML
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
        books.setItems(list);
    }

    @FXML
    private void goToDocDetail(Document document) {
        documentRequestSender.send(document);
        switchSceneRequestSender.send(new SwitchScene(SceneName.DOC_DETAIL));
    }
    @Override
    public Parent getParent() {
        return root;
    }

    @Override
    public void show() {
    }

}