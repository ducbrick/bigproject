package threeoone.bigproject.controller.viewcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.util.Alerts;
import threeoone.bigproject.util.FileOperation;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Controller class for the Add New Doc scene.
 * Handles the text field and button for Add New Doc view.
 * <p>
 * Provide method for interacting with button and text field
 * in Add New Doc view
 * </p>
 *
 * @author HUY1902
 */
@Component
@RequiredArgsConstructor
@FxmlView("AddNewDoc.fxml")
public class AddNewDocController implements ViewController {
  private final RequestSender<ViewController> switchToDocOverview;
  private final RequestSender<Document> addDocumentRequestSender;
  private final RequestSender<String> queryISBNGoogleRequestSender;

  private final MenuBarController menuBarController;
  @FXML
  private TextField author;

  @FXML
  private TextField categories;

  @FXML
  private Button chooseButton;

  @FXML
  private TextArea description;

  @FXML
  private TextField isbn;

  @FXML
  private TextField name;

  @FXML
  private TextField numOfCopies;

  @FXML
  private Button returnButton;

  @FXML
  private SplitPane root;

  @FXML
  private Button submitButton;

  @FXML
  private ImageView docCover;

  @Value("${document.photo-path.default}")
  private String coverPhotoPath;

  private File lastDirectory = null;
  private File selectedFile = null;

  /**
   * Initializes the controller class. This method is automatically called
   * after the fxml file has been loaded.
   * Set default date for user (today)
   */
  public void initialize() {
    name.setOnAction(event -> author.requestFocus());
    author.setOnAction(event -> categories.requestFocus());
    categories.setOnAction(event -> isbn.requestFocus());
    isbn.setOnAction(event -> description.requestFocus());
    description.setOnKeyPressed(event -> {
      if(event.getCode() == KeyCode.ENTER) {
        numOfCopies.requestFocus();
      }
    });
    categories.setText("Unknown");
    numOfCopies.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        submitButton.fire();
      }
    });

    returnButton.setOnAction(event -> switchToDocOverview.send(null));

    menuBarController.highlight(SceneName.ADD_NEW_DOC);

    chooseButton.setOnAction(event -> openFileChooser());
  }

  /**
   * Handler for submit button
   *
   * @param event event trigger submit button
   */
  // TODO: Submit send path to service
  @FXML
  private void pressSubmit(ActionEvent event) {
    if (name.getText().isEmpty() || author.getText().isEmpty() || numOfCopies.getText().isEmpty()) {
      Alerts.showAlertWarning("Warning!", "Fill all required fields!");
      return;
    }
    Document document = new Document(name.getText(), description.getText(), Integer.valueOf(numOfCopies.getText()));
    document.setAuthor(author.getText());
    document.setCategory(categories.getText());
    document.setIsbn(isbn.getText());
    addDocumentRequestSender.send(document);
    if (selectedFile == null) {
      Alerts.showAlertWarning("Warning!", "Unselected digital version for document!");
    } else {
      FileOperation.copyFile(selectedFile.getPath(), "", document.getId().toString());
      Alerts.showAlertInfo("Successfully!", "Adding with digital document.");
    }
    switchToDocOverview.send(null);
  }

  @FXML
  private void fulfillISBN(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER) {
      queryISBNGoogleRequestSender.send(isbn.getText());
      System.out.println(isbn.getText());
    }
  }

  /**
   * Auto fulfill information text field with give document's info
   *
   * @param document given document to fulfill
   */
  public void fulfillInfo(Document document) {
    name.setText(document.getName());
    description.setText(document.getDescription());
    author.setText(document.getAuthor());
    categories.setText(document.getCategory());
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
    docCover.setImage(new Image(coverPhotoPath));
    name.setText("");
    author.setText("");
    description.setText("");
    categories.setText("");
    isbn.setText("");
    numOfCopies.setText("");
  }

  /**
   * Open window file chooser (belong to OS) to choosing document file
   */
  private void openFileChooser() {
    FileChooser fileChooser = new FileChooser();
    if (lastDirectory != null) {
      fileChooser.setInitialDirectory(lastDirectory);
    }
    selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());
    if (selectedFile != null) {
      lastDirectory = selectedFile.getParentFile();
      chooseButton.setText("Selected File: " + selectedFile.getName());
    }
  }
}
