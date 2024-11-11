package threeoone.bigproject.controller.viewcontrollers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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

/**
 * @author purupurupkl
 */
@Component
@FxmlView("Menu.fxml")
public class MenuController implements ViewController {
    private final RequestSender<SwitchScene> switchSceneRequestSender;
    private final RequestSender<Integer> getDocumentByIdRequestSender;
    private final RequestSender<Document> documentDetailRequestSender;
    private final RequestSender<SwitchScene> getTopFiveMembersRequestSender;
    private final RequestSender<SwitchScene> getLastestDocumentsRequestSender;
    private final MenuBarController menuBarController;

    @FXML
    private Parent root;

    @FXML
    private Button Featured;

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
                          RequestSender<Integer> getDocumentByIdRequestSender,
                          RequestSender<Document> documentDetailRequestSender,
                          RequestSender<SwitchScene> getTopFiveMembersRequestSender,
                          RequestSender<SwitchScene> getLastestDocumentsRequestSender,
                          MenuBarController menuBarController) {
        this.switchSceneRequestSender = switchSceneRequestSender;
        this.getDocumentByIdRequestSender = getDocumentByIdRequestSender;
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
    }


    public void setUserList(ObservableList<Member> memberList) {
        MemberList.setItems(memberList);
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
    /**
     * generate a random book
     */
    @FXML
    public void randomBook() {
        getDocumentByIdRequestSender.send(0);
        documentDetailRequestSender.send(randomDocument);
        switchSceneRequestSender.send(new SwitchScene(SceneName.DOC_DETAIL));
    }

    @Override
    public void show() {
        Featured.setText("Featured book \n Nakano Miku character book \n Very nice little book about miku.");
        getTopFiveMembersRequestSender.send(new SwitchScene(SceneName.DOC_DETAIL));
        getLastestDocumentsRequestSender.send(new SwitchScene(SceneName.MAIN_MENU));
    }


}
