package threeoone.bigproject.controller.viewcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
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

  private final MenuBarController menuBarController;

  private final RequestSender<String> queryISBNGoogleRequestSender;
  @FXML
  private Parent root;

  @FXML
  private TextField categories;

  @FXML
  private DatePicker date;

  @FXML
  private TextField description;

  @FXML
  private TextField isbn;

  @FXML
  private TextField name;

  @FXML
  private Button submitButton;

  @FXML
  private Button chooseButton;

  @FXML
  private TextField author;

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
    description.setOnAction(event -> date.requestFocus());
    categories.setText("Unknown");
    date.setValue(getTodayDate());
    date.setOnAction(event -> date.requestFocus());
    date.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        if (date.getValue() != null) {
          submitButton.fire();
        }
      }
    });

    menuBarController.highlight(SceneName.ADD_NEW_DOC);

    chooseButton.setOnAction(event -> openFileChooser());
  }

  /**
   * Handler for submit button
   *
   * @param event event trigger submit button
   */
  // TODO: Submit send today date, path, categories, etc to service
  @FXML
  private void pressSubmit(ActionEvent event) {
    if (name.getText().isEmpty() || author.getText().isEmpty()) {
      Alerts.showAlertWarning("Warning!", "Fill all required fields!");
      return;
    }
    Document document = new Document(name.getText(), description.getText());
    addDocumentRequestSender.send(document);
    if (selectedFile == null) {
      Alerts.showAlertWarning("Warning!", "Unselected file. Adding with no digital document.");
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

  }

  /**
   * This function get today date follow time zone +7
   *
   * @return today date
   */
  private LocalDate getTodayDate() {
    return LocalDate.now(ZoneId.of("Asia/Bangkok"));
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
