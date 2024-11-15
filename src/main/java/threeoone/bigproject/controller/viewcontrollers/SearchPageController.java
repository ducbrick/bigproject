package threeoone.bigproject.controller.viewcontrollers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Document;

/**
 * Class for handling search bar view
 *
 * @author HUY1902
 */
@Component
@FxmlView("SearchPage.fxml")
@RequiredArgsConstructor
public class SearchPageController implements ViewController {
  private final RequestSender<String> queryDocByNameRequestSender;
  private final RequestSender<String> queryDocByAuthorRequestSender;
  private final RequestSender<String> queryDocByCategoryRequestSender;
  private final RequestSender<Document> documentDetailRequestSender;
  private final RequestSender<ViewController> switchToDocDetail;

  /**
   * Represent for search type
   */
  private enum SearchType {
    AUTHOR,
    NAME,
    CATEGORY,
    MEMBER
  }

  private SearchType searchType = null;
  @FXML
  private ToggleButton author;

  @FXML
  private ToggleButton category;

  @FXML
  private ToggleButton name;

  @FXML
  private ToggleButton member;

  @FXML
  private Parent root;
  @FXML
  private TextField searchText;

  @FXML
  private ListView<Document> result;

  /**
   * Initialization for JavaFX object handle by view
   */
  // TODO Show author when search author, category when search category
  @FXML
  private void initialize() {
    result.setCellFactory(param -> new ListCell<Document>() {
      @Override
      protected void updateItem(Document item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
        } else {
          switch (searchType) {
            //case AUTHOR ->  setText("Author: " + item.getAuthor());
            case NAME -> setText("Name: " + item.getName());
            //case CATEGORY -> setText("Category: " + item.getCategory());
          }
        }
      }
    });
    result.setOnMouseClicked(event -> {
      Document selectedDocument = result.getSelectionModel().getSelectedItem();
      if (selectedDocument != null && event.getClickCount() == 2) {
        System.out.println(selectedDocument.getName());
        documentDetailRequestSender.send(selectedDocument);
        switchToDocDetail.send(this);
      }
    });
  }

  /**
   * Reset state of all toggle button {@link #category}, {@link #name}, {@link #author},
   * {@link #member}
   */
  @FXML
  private void resetSelect() {
    category.setSelected(false);
    name.setSelected(false);
    author.setSelected(false);
    member.setSelected(false);
    searchType = null;
  }

  /**
   * Handle author button
   *
   * @param event event trigger author button
   */
  @FXML
  private void pressAuthor(ActionEvent event) {
    resetSelect();
    author.setSelected(true);
    searchType = SearchType.AUTHOR;
  }

  /**
   * Handle category button
   *
   * @param event event trigger category button
   */
  @FXML
  private void pressCategory(ActionEvent event) {
    resetSelect();
    category.setSelected(true);
    queryDocByCategoryRequestSender.send(searchText.getText());
    searchType = SearchType.CATEGORY;
  }

  /**
   * Handle name button
   *
   * @param event event trigger name button
   */
  @FXML
  private void pressName(ActionEvent event) {
    resetSelect();
    name.setSelected(true);
    queryDocByNameRequestSender.send(searchText.getText());
    searchType = SearchType.NAME;
  }

  /**
   * Handle search button. Search name by default
   *
   * @param event event trigger search button
   */
  @FXML
  private void pressSearch(ActionEvent event) {
    if (searchType == null) {
      queryDocByNameRequestSender.send(searchText.getText());
      return;
    }
    switch (searchType) {
      case AUTHOR:
        queryDocByAuthorRequestSender.send(searchText.getText());
        break;
      case CATEGORY:
        queryDocByCategoryRequestSender.send(searchText.getText());
        break;
      case NAME:
        queryDocByNameRequestSender.send(searchText.getText());
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

  public void addResult(ObservableList<Document> result) {
    this.result.getItems().addAll(result);
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
