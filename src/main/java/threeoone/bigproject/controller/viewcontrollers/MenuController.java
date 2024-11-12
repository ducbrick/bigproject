package threeoone.bigproject.controller.viewcontrollers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.LendingDetail;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.entities.User;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author purupurupkl
 */
@Component
@FxmlView("Menu.fxml")
public class MenuController implements ViewController {
    private final RequestSender<SwitchScene> switchSceneRequestSender;
    private final RequestSender<Document> getRandomDocumentRequestSender;
    private final RequestSender<Document> documentDetailRequestSender;
    private final RequestSender<SwitchScene> getTopFiveMembersRequestSender;
    private final RequestSender<SwitchScene> getLastestDocumentsRequestSender;
    private final MenuBarController menuBarController;

    @FXML
    private Parent root;

    @FXML
    private Button Featured;

    @FXML
    private Label Greeting;

    @FXML
    private Label time;

    @FXML
    private Button Random;

    @FXML
    private TableView<Member> MemberList;

    @FXML
    private TableColumn<Member, String> MemberID;

    @FXML
    private TableColumn<Member, String> MemberName;

    @FXML
    private TableColumn<Member, Integer> BooksIssued;

    @FXML
    private TableView<Document> LastestDocuments;

    @FXML
    private TableColumn<Document, String> BookID;

    @FXML
    private TableColumn<Document, String> Title;

    @FXML
    private TableColumn<Document, String> Author;

    @FXML
    private TableColumn<Document, String> CopiesAvailable;

    @FXML
    private TableColumn<Document, String> Description;

    @FXML
    private TableView<LendingDetail> LendingList;

    @FXML

    private Document randomDocument;

    @Override
    public Parent getParent() {
        return root;
    }

    public MenuController(RequestSender<SwitchScene> switchSceneRequestSender,
                          RequestSender<Document> getRandomDocumentRequestSender,
                          RequestSender<Document> documentDetailRequestSender,
                          RequestSender<SwitchScene> getTopFiveMembersRequestSender,
                          RequestSender<SwitchScene> getLastestDocumentsRequestSender,
                          MenuBarController menuBarController) {
        this.switchSceneRequestSender = switchSceneRequestSender;
        this.getRandomDocumentRequestSender = getRandomDocumentRequestSender;
        this.documentDetailRequestSender = documentDetailRequestSender;
        this.getTopFiveMembersRequestSender = getTopFiveMembersRequestSender;
        this.getLastestDocumentsRequestSender = getLastestDocumentsRequestSender;
        this.menuBarController = menuBarController;

    }


    public void initialize() {
        MemberID.setCellValueFactory(new PropertyValueFactory<>("id"));
        MemberName.setCellValueFactory(new PropertyValueFactory<>("name"));

        BookID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Title.setCellValueFactory(new PropertyValueFactory<>("name"));
        Description.setCellValueFactory(new PropertyValueFactory<>("description"));

        menuBarController.highlight(SceneName.MAIN_MENU);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            time.setText(LocalDateTime.now().format(formatter));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    public void setUserList(ObservableList<Member> memberList) {
        Thread thread = new Thread(() -> MemberList.setItems(memberList));
        thread.start();
        //BooksIssued.setCellValueFactory(new PropertyValueFactory<>("booksIssued"));
    }

    public void setLastestDocuments(ObservableList<Document> lastestDocuments) {
        LastestDocuments.setItems(lastestDocuments);
    }

    /**
     *
     */
    private void featuredBook() {

    }

    public void setRandomBook(Document document) {
        randomDocument = document;
    }

    private String getGreeting() {
        LocalTime now = LocalTime.now();

        if(now.isBefore(LocalTime.NOON)) {
            return "Good morning!";
        } else if (now.isBefore(LocalTime.of(18,0))) {
            return "Good afternoon!";
        } else if (now.isBefore(LocalTime.of(22,0))) {
            return "Good evening!";
        } else {
            return "Good night!";
        }
    }
    /**
     * generate a random book
     */
    @FXML
    public void randomBook() {
        getRandomDocumentRequestSender.send(null);
        documentDetailRequestSender.send(randomDocument);
        switchSceneRequestSender.send(new SwitchScene(SceneName.DOC_DETAIL));
    }

    @Override
    public void show() {
        getTopFiveMembersRequestSender.send(new SwitchScene(SceneName.MAIN_MENU));
        getLastestDocumentsRequestSender.send(new SwitchScene(SceneName.MAIN_MENU));
        Greeting.setText(getGreeting());
    }


}
