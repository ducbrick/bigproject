package threeoone.bigproject.controller.viewcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Document;

import javax.print.Doc;

/**
 * Class for handling search bar view
 *
 * @author HUY1902
 */
@Component
@FxmlView("SearchBar.fxml")
public class SearchBarController implements ViewController {
  private final RequestSender<String> queryDocByNameRequestSender;
  private final RequestSender<String> queryDocByAuthorRequestSender;
  private final RequestSender<String> queryDocByCategoryRequestSender;
  private final RequestSender<Document> documentDetailRequestSender;
  private final RequestSender<SwitchScene> switchSceneRequestSender;

  private enum SearchType {
    AUTHOR,
    NAME,
    CATEGORY
  }

  private SearchType searchType = null;
  @FXML
  private ToggleButton author;

  @FXML
  private ToggleButton category;

  @FXML
  private ToggleButton name;
  @FXML
  private Parent root;
  @FXML
  private TextField searchText;

  @FXML
  private ListView<Document> result;

  /**
   * Constructor for class with dependencies which is requestSender for query
   *
   * @param queryDocByNameRequestSender     requestSender for query
   * @param queryDocByAuthorRequestSender   requestSender for query
   * @param queryDocByCategoryRequestSender requestSender for query
   */
  public SearchBarController(RequestSender<String> queryDocByNameRequestSender,
                             RequestSender<String> queryDocByAuthorRequestSender,
                             RequestSender<String> queryDocByCategoryRequestSender,
                             RequestSender<Document> documentDetailRequestSender,
                             RequestSender<SwitchScene> switchSceneRequestSender) {
    this.queryDocByNameRequestSender = queryDocByNameRequestSender;
    this.queryDocByAuthorRequestSender = queryDocByAuthorRequestSender;
    this.queryDocByCategoryRequestSender = queryDocByCategoryRequestSender;
    this.documentDetailRequestSender = documentDetailRequestSender;
    this.switchSceneRequestSender = switchSceneRequestSender;
  }

  @FXML
  private void initialize() {
    ObservableList<Document> documents = FXCollections.observableArrayList(
            new Document("1", "2"),
            new Document("2", "3")
    );
    result.setCellFactory(param -> new ListCell<Document>() {
      @Override
      protected void updateItem(Document item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
        } else {
          setText(item.getName());
        }
      }
    });
    result.setOnMouseClicked(event -> {
      Document selectedDocument = result.getSelectionModel().getSelectedItem();
      if(selectedDocument != null && event.getClickCount() == 2) {
        System.out.println(selectedDocument.getName());
        documentDetailRequestSender.send(selectedDocument);
        switchSceneRequestSender.send(new SwitchScene(SceneName.DOC_DETAIL));
      }
    });
    setResult(documents);
  }

  @FXML
  private void resetSelect() {
    category.setSelected(false);
    name.setSelected(false);
    author.setSelected(false);
    searchType = null;
  }

  @FXML
  private void pressAuthor(ActionEvent event) {
    resetSelect();
    author.setSelected(true);
    searchType = SearchType.AUTHOR;
  }

  @FXML
  private void pressCategory(ActionEvent event) {
    resetSelect();
    category.setSelected(true);
    queryDocByCategoryRequestSender.send(searchText.getText());
    searchType = SearchType.CATEGORY;
  }

  @FXML
  private void pressName(ActionEvent event) {
    resetSelect();
    name.setSelected(true);
    queryDocByNameRequestSender.send(searchText.getText());
    searchType = SearchType.NAME;
  }

  @FXML
  private void pressSearch(ActionEvent event) {
    if (searchType == null) {
      System.out.println("No search type selected");
      return;
    }
    switch (searchType) {
      case AUTHOR:
        queryDocByAuthorRequestSender.send(searchText.getText());
        System.out.println("Author: " + searchText.getText());
        break;
      case CATEGORY:
        queryDocByCategoryRequestSender.send(searchText.getText());
        System.out.println("Category: " + searchText.getText());
        break;
      case NAME:
        queryDocByNameRequestSender.send(searchText.getText());
        System.out.println("Name: " + searchText.getText());
        break;
    }
  }

  /**
   * Set result of query for list view from another controller
   *
   * @param result result of query
   */
  public void setResult(ObservableList<Document> result) {
    this.result.setItems(result);
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
}
