package threeoone.bigproject.controller.viewcontrollers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.util.MenuItemFactory;

/**
 * Class for handle Member List page
 *
 * @author HUY1902
 */
@Component
@FxmlView("MemberList.fxml")
@RequiredArgsConstructor
public class MemberListController implements ViewController {
  private final RequestSender<ViewController> switchToEditMem;
  private final RequestSender<ViewController> switchToAddMem;
  private final RequestSender<ViewController> switchToMainMenu;
  private final RequestSender<ViewController> switchToSearch;
  private final RequestSender<Member> getAllMembersRequestSender;
  private final RequestSender<Member> editMemberRequestSender;
  private final RequestSender<Member> removeMemberRequestSender;
  @FXML
  private Parent root;

  @FXML
  private TableView<Member> table;

  @FXML
  private TableColumn<Member, String> id;

  @FXML
  private TableColumn<Member, String> name;

  @FXML
  private ContextMenu contextMenu;

  private Member chosenMember;

  /**
   * Initialized method for FXML page
   */
  // TODO: go to member detail
  @FXML
  private void initialize() {
    contextMenu.getItems().add(remove());
    contextMenu.getItems().add(edit());
    table.setRowFactory(tableview -> {
      TableRow<Member> row = new TableRow<>();
      //handle click on item
      row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && (!row.isEmpty())) {
          Member member = row.getItem();
          // Go to member detail
//          System.out.println(member.getName());
        }
      });
      //handle show context menu
      row.setOnContextMenuRequested(event -> {
        if (!row.isEmpty()) {
          chosenMember = row.getItem();
          contextMenu.show(row, event.getScreenX(), event.getScreenY());
        }
      });
      //handle key press to show menu at true location
      row.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.CONTEXT_MENU && !row.isEmpty()) {
          contextMenu.show(row, row.getScene().getWindow().getX() + row.getLayoutX() + row.getTranslateX(),
                  row.getScene().getWindow().getY() + row.getLayoutY() + row.getTranslateY());
        }
      });
      return row;
    });
    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
  }

  /**
   * Creates a menu item for removing an item.
   * Displays a confirmation dialog when the menu item is selected.
   *
   * @return the menu item for removing an item
   */
  private MenuItem remove() {
    return MenuItemFactory.createMenuItem("Remove",
            "Remove Confirmation",
            "Are you sure you want to remove this member?",
            unused -> {
              removeMemberRequestSender.send(chosenMember);
              getAllMembersRequestSender.send(null);
            });
  }

  /**
   * Creates a menu item for removing an item.
   * Displays a confirmation dialog when the menu item is selected.
   *
   * @return the menu item for removing an item
   */
  private MenuItem edit() {
    return MenuItemFactory.createMenuItem("Edit",
            "Edit Confirmation",
            "Are you sure you want to edit this member?",
            unused -> {
              editMemberRequestSender.send(chosenMember);
              switchToEditMem.send(this);
            });
  }

  /**
   * Handler for add button. Go to add new member page
   *
   * @param event event trigger button
   */
  @FXML
  private void pressAdd(ActionEvent event) {
    switchToAddMem.send(this);
  }

  /**
   * Handler for return button. Return Main menu
   *
   * @param event event trigger button
   */
  @FXML
  private void pressReturn(ActionEvent event) {
    switchToMainMenu.send(null);
  }

  /**
   * Handler for search button. Go to search page.
   *
   * @param event event trigger button
   */
  @FXML
  private void pressSearch(ActionEvent event) {
    switchToSearch.send(null);
  }


  /**
   * Sets the items in the {@link #table} with the given member list.
   * String arguments like: id,  name in `PropertyValueFactory` is attribute of {@link Member}
   *
   * @param memberObservableList An observable list of members to be displayed in the table
   */
  public void setTable(ObservableList<Member> memberObservableList) {
    table.setItems(memberObservableList);
  }

  /**
   * Gets the root {@link #root} of the {@code View} managed by the {@link ViewController}.
   *
   * @return the root {@link #root} of the {@code View}
   */
  @Override
  public Parent getParent() {
    return root;
  }

  /**
   * Notify the {@link ViewController} that its {@code View} is displayed.
   */
  @Override
  public void show() {
    getAllMembersRequestSender.send(null);
  }
}
