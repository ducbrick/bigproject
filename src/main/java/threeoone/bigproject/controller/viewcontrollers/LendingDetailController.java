package threeoone.bigproject.controller.viewcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.observers.DataType;
import threeoone.bigproject.controller.observers.Publisher;
import threeoone.bigproject.controller.observers.QueryPublisher;
import threeoone.bigproject.controller.observers.Subscriber;
import threeoone.bigproject.controller.requestbodies.LendingDetail;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.util.Alerts;

/**
 * The {@code LendingDetailController} class manages the lending detail view and updates
 * its content based on member and document data. It implements the {@link ViewController}
 * and {@link Subscriber} interfaces to handle view management and subscription to updates.
 *
 * @author HUY1902
 */
@Component
@FxmlView("LendingDetail.fxml")
@RequiredArgsConstructor
public class LendingDetailController implements ViewController, Subscriber {
  private final QueryPublisher queryPublisher;
  private final RequestSender<LendingDetail> saveNewLending;
  private final RequestSender<ViewController> switchToMainMenu;
  @FXML
  private Parent root;

  @FXML
  private Label docAuthor;

  @FXML
  private Label docCategory;

  @FXML
  private Label docID;

  @FXML
  private Label docName;

  @FXML
  private ImageView docPhoto;

  @FXML
  private Label userID;

  @FXML
  private Label userName;

  @FXML
  private Label docDescription;

  private Member member;
  private Document document;

  /**
   * Sets the member information in the view.
   *
   * @param member the member whose information is to be displayed
   */
  public void setMember(@NonNull Member member) {
    this.member = member;
    userID.setText(member.getId().toString());
    userName.setText(member.getName());
  }

  /**
   * Sets the document information in the view.
   *
   * @param document the document whose information is to be displayed
   */
  public void setDocument(Document document) {
    this.document = document;
    docID.setText(document.getId().toString());
    docName.setText(document.getName());
    docDescription.setText(document.getDescription());
  }

  /**
   * Gets the root {@link Parent} of the {@code View} managed by the {@link ViewController}.
   *
   * @return the root {@link Parent} of the {@code View}
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
    queryPublisher.subscribe(DataType.MEMBER, this);
    queryPublisher.subscribe(DataType.DOCUMENT, this);
  }

  /**
   * This method is called by the {@code Publisher} to notify the {@code Subscriber} of an update.
   *
   * @param data the data notified by {@link Publisher}
   */
  @Override
  public void update(Object data) {
    if (data instanceof Member member) {
      setMember(member);
    }
    if (data instanceof Document document) {
      setDocument(document);
    }
  }

  /**
   * Handles the action event when the return button is pressed.
   * Unsubscribes from member and document updates and switches to the main menu scene.
   *
   * @param event the action event triggered by pressing the return button
   */
  @FXML
  private void pressReturn(ActionEvent event) {
    queryPublisher.unsubscribe(DataType.MEMBER, this);
    queryPublisher.unsubscribe(DataType.DOCUMENT, this);
    switchToMainMenu.send(null);
  }

  /**
   * Handles the action event when the submit button is pressed.
   * If both a member and a document are selected, it saves the new lending detail.
   * Otherwise, it shows an alert warning to choose both member and document.
   *
   * @param event the action event triggered by pressing the submit button
   */
  @FXML
  private void pressSubmit(ActionEvent event) {
    if (member != null && document != null) {
      saveNewLending.send(new LendingDetail(member, document));
      switchToMainMenu.send(this);
      return;
    }
    Alerts.showAlertWarning("Error!!!", "Choose both member and document");
  }

}
