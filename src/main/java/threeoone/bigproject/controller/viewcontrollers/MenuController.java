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
 * main menu (so-called Dashboard). shows up after logging in
 * shows current date - time, one table for newest uploaded documents, one for members info and one for latest book issues
 * (which is not even working rn).
 * also has a funny random button that sends you to a random document's detail page.
 */
@Component
@FxmlView("Menu.fxml")
@RequiredArgsConstructor
public class MenuController implements ViewController {
    private final RequestSender<ViewController> switchToDocDetail;
    private final RequestSender<Document> getRandomDocumentRequestSender;
    private final RequestSender<Document> documentDetailRequestSender;
    private final RequestSender<SwitchScene> getTopFiveMembersRequestSender;
    private final RequestSender<SwitchScene> getLastestDocumentsRequestSender;
    private final MenuBarController menuBarController;

    @FXML
    private Parent root;

    /**
     * TODO: custom funni background
     */
    @FXML
    private Label Greeting;

    @FXML
    private Label time;

    /**
     * TODO: WHERE. IS. MY. SERVICE.
     */
    @FXML
    private Label NumOfLendings;

    @FXML
    private Button Random;

    /**
     * Member list table. Show 5 member of smallest ID.
     * TODO: make this actually do something pls
     */
    @FXML
    private TableView<Member> MemberList;
    @FXML
    private TableColumn<Member, String> MemberID;
    @FXML
    private TableColumn<Member, String> MemberName;

    /**
     * Latest document table.
     */
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

    private Document randomDocument;

    @Override
    public Parent getParent() {
        return root;
    }

    /**
     * initializes the table and sets up a Timeline for the clock
     */
    public void initialize() {
        MemberID.setCellValueFactory(new PropertyValueFactory<>("id"));
        MemberName.setCellValueFactory(new PropertyValueFactory<>("name"));

        BookID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Title.setCellValueFactory(new PropertyValueFactory<>("name"));
        Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        Author.setCellValueFactory(new PropertyValueFactory<>("author"));
        CopiesAvailable.setCellValueFactory(new PropertyValueFactory<>("copies"));
        menuBarController.highlight(SceneName.MAIN_MENU);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy | HH:mm:ss");
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
            return "Good night!\nGet some sleep :)";
        }
    }
    /**
     * sends user to a random document's page.
     */
    @FXML
    public void randomBook() {
        getRandomDocumentRequestSender.send(null);
        if(randomDocument != null) {
            documentDetailRequestSender.send(randomDocument);
            switchToDocDetail.send(this);
        }
    }

    /**
     * sets greeting based on time and set data for tables.
     */
    @Override
    public void show() {
        getTopFiveMembersRequestSender.send(new SwitchScene(SceneName.MAIN_MENU));
        getLastestDocumentsRequestSender.send(new SwitchScene(SceneName.MAIN_MENU));
        Greeting.setText(getGreeting());
    }


}
