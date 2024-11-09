package threeoone.bigproject.controller.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.entities.LendingDetail;

import java.time.LocalDateTime;

@Component
@FxmlView("MemberDetails.fxml")
public class MemberDetailsController implements ViewController{

    @FXML
    private Parent root;

    @FXML
    private Text Name;

    @FXML
    private Text Address;

    @FXML
    private Text Age;

    @FXML
    private Text ID;

    @FXML
    private TableView<LendingDetail> BorrowingBooks;

    @FXML
    private TableColumn<LendingDetail, LocalDateTime> BorrowTime;

    @FXML
    private TableColumn<LendingDetail, Integer> DocumentID;

    @FXML
    private TableColumn<LendingDetail, String> Title;

    @Override
    public Parent getParent() {
        return root;
    }

    @Override
    public void show() {

    }
}
