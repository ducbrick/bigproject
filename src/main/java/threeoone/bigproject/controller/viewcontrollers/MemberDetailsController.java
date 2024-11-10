package threeoone.bigproject.controller.viewcontrollers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
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
import java.util.*;

/**
 * this view present information about a specific member, which include their name and the books they are currently
 * borrowing.
 */
@Component
@FxmlView("MemberDetails.fxml")
@RequiredArgsConstructor
public class MemberDetailsController implements ViewController{
    private Member member;

    /** send choosen document to docDetail */
    private final RequestSender<Document> documentDetailRequestSender;
    private final RequestSender<SwitchScene> switchSceneRequestSender;

    @FXML
    private Parent root;

    @FXML
    private Label Name;

    @FXML
    private TableView<LendingDetail> BorrowingBooks;

    @FXML
    private TableColumn<LendingDetail, LocalDateTime> BorrowTime;

    @FXML
    private TableColumn<LendingDetail, Integer> DocumentID;

    @FXML
    private TableColumn<LendingDetail, String> Title;

    public void initialize() {
        initTable();
    }

    /**
     * initializes the columns' property
     * and initializes rows so they send user to the detail page of the selected document
     */
    private void initTable() {
        BorrowingBooks.setRowFactory(tableview -> {
            TableRow<LendingDetail> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Document doc = row.getItem().getDocument();
                    gotoDocDetail(doc);
                }
            });
            return row;
        });

        BorrowTime.setCellValueFactory(new PropertyValueFactory<>("lend_time"));
        DocumentID.setCellValueFactory(cellData -> {
            Document doc = cellData.getValue().getDocument();
            return new SimpleIntegerProperty(doc.getId()).asObject();
        });
        Title.setCellValueFactory(cellData -> {
            Document doc = cellData.getValue().getDocument();
            return new SimpleStringProperty(doc.getName());
        });
    }

    /**
     * used in initTable() to handle changing view to DocDetail
     * @param document the document of the selected row
     */
    private void gotoDocDetail(Document document) {
        documentDetailRequestSender.send(document);
        switchSceneRequestSender.send(new SwitchScene(SceneName.DOC_DETAIL));
    }

    @Override
    public Parent getParent() {
        return root;
    }

    @Override
    public void show() {
        member = new Member("RAHHHHHH");
        LendingDetail lendingDetail1 = new LendingDetail(LocalDateTime.now());
        Document doc1 = new Document("test1", "desc1");
        doc1.setId(0);
        lendingDetail1.setDocument(doc1);
        lendingDetail1.setMember(member);
        List<LendingDetail> lendingDetails = new ArrayList<>();
        lendingDetails.add(lendingDetail1);
        member.setLendingDetails(lendingDetails);
        ObservableList<LendingDetail> lendingDetailObservableList = FXCollections.observableList(member.getLendingDetails());
        BorrowingBooks.setItems(lendingDetailObservableList);
        Name.setText("Name: " + member.getName());
    }
}
