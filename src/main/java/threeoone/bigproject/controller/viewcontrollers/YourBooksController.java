package threeoone.bigproject.controller.viewcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.controller.viewcontrollers.ViewController;
import threeoone.bigproject.entities.Document;

/**
 * TODO: injects information from user
 */
@Component
@FxmlView("YourBooks.fxml")
public class YourBooksController implements ViewController {
    @FXML
    private Parent root;
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
    private TableColumn<Document, String> BorrowDate;

    @FXML
    private TableColumn<Document, String> ReturnDate;

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
    }
    @Override
    public Parent getParent() {
        return root;
    }

    @Override
    public void show() {
        books.setItems(list);
    }

}