package threeoone.bigproject.controller.viewcontrollers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;

/**
 * simple ahh menu bar.
 * <p>
 * This controller controls a menubar which appears on the left of the page.
 * It has buttons to: Menu, AddDocument, (all borrowed documents), DocumentOverview (called Borrow) and MemberList
 * </p>
 * The menubar is defined in {@code Menubar.fxml}. To include the menubar in a view,
 * use fx:include on the FXML of that view. This creates NEW objects for that view's FXML
 * file. <br>
 * this method use general.css style
 * TODO: expandable menu (with css?)
 */
@Component
@FxmlView("MenuBar.fxml")
@RequiredArgsConstructor
public class MenuBarController {
  /**
   * request sender to switch between views
   */
  private final RequestSender<ViewController> switchToAddNewDoc;
  private final RequestSender<ViewController> switchToYourBooks;
  private final RequestSender<ViewController> switchToDocOverview;
  private final RequestSender<ViewController> switchToMainMenu;
  private final RequestSender<ViewController> switchToMemList;
  private final RequestSender<ViewController> switchToLendingDetail;
  @FXML
  private VBox box;
  /**
   * buttons for views
   */
  @FXML
  private Button AddBook;

  @FXML
  private Button DocOverview;

  @FXML
  private Button YourBooks;

  @FXML
  private Button Menu;

  @FXML
  private Button Search;

  @FXML
  private Button MemList;

  @FXML
  private Button Lend;

  /**
   * actual methods to change view
   */
  @FXML
  private void toAddBook() {
    switchToAddNewDoc.send(null);
  }

  @FXML
  private void toYourBooks() {
    switchToYourBooks.send(null);
  }

  @FXML
  private void toDocOverview() {
    switchToDocOverview.send(null);
  }

  @FXML
  private void toMenu() {
    switchToMainMenu.send(null);
  }

  @FXML
  private void toSearch() {
  }

  @FXML
  private void toMemberList() { switchToMemList.send(null); }

  @FXML
  private void toLend() { switchToLendingDetail.send(null); }

  /**
   * highlight one button according to the {@code SceneName}.<br>
   * For this method to actually works,
   * inject the MenuBarController into the view's controller and run the method there <br>
   * :(
   */
  public void highlight(SceneName sceneName) {
    Button button = switch (sceneName) {
      case ADD_NEW_DOC -> AddBook;
      case YOUR_BOOKS -> YourBooks;
      case DOC_OVERVIEW -> DocOverview;
      case MAIN_MENU -> Menu;
      case SEARCH -> Search;
      default -> new Button();
    };
    button.setId("active");
  }

}
