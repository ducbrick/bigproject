package threeoone.bigproject.controller.viewcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Document;

/**
 * This class handles for edit document page
 *
 * @author HUY1902
 */
@Component
@FxmlView("EditDocument.fxml")
@RequiredArgsConstructor
public class EditDocumentController implements ViewController {
  private final RequestSender<Document> commitChangeDocRequestSender;
  private final  RequestSender<ViewController> switchToDocOverview;
  @FXML
  private Parent root;

  @FXML
  private TextField author;

  @FXML
  private TextField category;

  @FXML
  private TextField name;

  @FXML
  private ImageView image;

  @FXML
  private TextArea description;

  @FXML
  private Label isbn;

  @FXML
  private Button submitButton;

  private Document document;

  /**
   * Set default information about the document for text field
   */
  private void setInfoDefault() {
    isbn.setText(document.getName());
    name.setText(document.getName());
    description.setText(document.getDescription());
  }

  /**
   * Set new info from text field to {@link #document}
   */
  // TODO: Set image for imageView
  private void commitNewInfo() {
    document.setName(name.getText());
    document.setDescription(description.getText());

  }
  /**
   * Initialized method for FXML page
   */
  @FXML
  private void initialize() {
    name.setOnAction(event -> author.requestFocus());
    author.setOnAction(event -> category.requestFocus());
    category.setOnAction(event -> description.requestFocus());
    description.setOnKeyPressed(event -> {
      if(event.getCode() == KeyCode.ENTER) {
        submitButton.fire();
      }
    });
    Thread newThread = new Thread(() -> {
      Image temp = new Image("threeoone/bigproject/controller/viewcontrollers/No_image_available.png");
      image.setImage(temp);
    });
    newThread.start();
  }

  /**
   * Method handled press Return button. Return to Doc Overview page
   *
   * @param event event trigger button
   */
  @FXML
  private void pressReturn(ActionEvent event) {
    switchToDocOverview.send(null);
  }

  /**
   * Method handles press Reset button. Set all info turn back default
   *
   * @param event event trigger button
   */
  @FXML
  private void pressReset(ActionEvent event) {
    setInfoDefault();
  }

  /**
   * Method handles submit button. Send changed info to service
   *
   * @param event event trigger button
   */
  // TODO: maybe can eliminate commitChange by this way
  @FXML
  private void pressSubmit(ActionEvent event) {
    commitNewInfo();
    commitChangeDocRequestSender.send(document);
    switchToDocOverview.send(this);
  }

  public void setDocument(@NonNull Document document) {
    this.document = document;
    setInfoDefault();
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

  }
}
