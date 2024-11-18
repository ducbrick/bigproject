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
import lombok.Setter;
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
import java.util.*;

/**
 * this view present information about a specific member, which include their name, address, phone number
 * email and the books they are currently borrowing.
 * the books which the member is borrowing is displayed in a interactive table
 * TODO: laggy document detail
 */
@Component
@FxmlView("MemberDetails.fxml")
@RequiredArgsConstructor @Setter
public class MemberDetailsController implements ViewController{
    private Member member;

    private final RequestSender<Document> documentDetailRequestSender;
    private final RequestSender<ViewController> switchToDocDetail;

    @FXML
    private Parent root;

    @FXML
    private Label Name;

    @FXML
    private Label Phone;

    @FXML
    private Label Email;

    @FXML
    private Label Address;

    /**
     * the table and its columns
     */
    @FXML
    private TableView<LendingDetail> BorrowingBooks;

    @FXML
    private TableColumn<LendingDetail, String> BorrowTime;

    @FXML
    private TableColumn<LendingDetail, Integer> DocumentID;

    @FXML
    private TableColumn<LendingDetail, String> Title;


    public void initialize() {
        initTable();
    }


    /**
     * initializes the columns' property
     * and handles user's interaction with the table (double-clicking on a row sends user to DocumentDetail of the
     * document on that row)
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

        BorrowTime.setCellValueFactory(cellData ->{
            LocalDateTime date = cellData.getValue().getLendTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd/MM/yyyy");
            return new SimpleStringProperty(date.format(formatter));
        });
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
        switchToDocDetail.send(this);
    }

    @Override
    public Parent getParent() {
        return root;
    }

    /**
     * TODO: retrieve lending details of member
     * display member's info and set table's data to member's lending details
     */
    @Override
    public void show() {
        ObservableList<LendingDetail> lendingDetailObservableList = FXCollections.observableList(member.getLendingDetails());
        BorrowingBooks.setItems(lendingDetailObservableList);
        Name.setText(member.getName());
        Phone.setText(member.getAddress());
        Address.setText(member.getAddress());
        Email.setText(member.getEmail());
    }
}
