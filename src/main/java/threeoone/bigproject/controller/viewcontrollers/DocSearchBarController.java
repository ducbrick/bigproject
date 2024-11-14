package threeoone.bigproject.controller.viewcontrollers;

import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.util.Duration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.*;
import threeoone.bigproject.controller.observers.DataType;
import threeoone.bigproject.controller.observers.QueryPublisher;
import threeoone.bigproject.entities.Document;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code SearchBarController} class manages the search functionality in the UI.
 * It allows users to search for members by user ID or username and displays the results
 * in a popup list.
 *
 * @author HUY1902
 */
@Component
@FxmlView("MemberSearchBar.fxml")
@RequiredArgsConstructor
@Getter
public class DocSearchBarController {
  private final QueryPublisher queryPublisher;
  private final RequestSender<Integer> queryDocByIdRequestSender;
  private final List<ToggleButton> toggleButtons = new ArrayList<>();
  private final PauseTransition hideTimer = new PauseTransition();

  private final int CELL_SIZE = 30;
  private final Popup popup = new Popup();
  private final ListView<Document> documentListView = new ListView<>();

  @FXML
  private HBox hbox;

  @FXML
  private TextField search;

  @FXML
  private GridPane searchRoot;

  @FXML
  private Button searchButton;


  /**
   * Initializes the controller, setting up the toggle buttons, list view, and popup.
   */
  @FXML
  private void initialize() {
    toggleButtons.add(new ToggleButton("ID"));
    toggleButtons.add(new ToggleButton("Name"));
    toggleButtons.add(new ToggleButton("Author"));
    toggleButtons.add(new ToggleButton("Category"));
    hbox.getChildren().addAll(toggleButtons);
    hideTimer.setDuration(Duration.seconds(2));
    hideTimer.setOnFinished(event -> popup.hide());

    documentListView.setCellFactory(param -> new ListCell<>() {
      @Override
      protected void updateItem(Document item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
          setText("ID: " + item.getId() + "; Name: " + item.getName());
        }
      }
    });

    documentListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        queryPublisher.notifySubscribers(DataType.DOCUMENT, newValue);
        popup.hide();
      }
    });

    search.setOnKeyPressed(event -> {
      popup.hide();
      if (event.getCode() == KeyCode.ENTER) {
        searchButton.fire();
      }
    });
    documentListView.setFixedCellSize(CELL_SIZE);
    popup.getContent().add(documentListView);
  }

  /**
   * Handles the search button action. Sends member queries based on the selected toggle buttons and
   * displays the results in a popup.
   *
   * @param event the action event triggered by clicking the search button
   */
  @FXML
  private void pressSearch(ActionEvent event) {
    if (search.getText().isEmpty()) {
      return;
    }
    documentListView.getItems().clear();
    for (ToggleButton toggleButton : toggleButtons) {
      if (toggleButton.isSelected()) {
        switch (toggleButton.getText()) {
          case "ID" -> {
            queryDocByIdRequestSender.send(Integer.valueOf(search.getText()));
          }
        }
      }
    }
    documentListView.setPrefHeight(documentListView.getItems().size() * CELL_SIZE);
    popup.setHeight(documentListView.getHeight());
    if (!documentListView.getItems().isEmpty() && !popup.isShowing()) {
      popup.show(search, search.localToScreen(search.getBoundsInLocal()).getMinX(),
              search.localToScreen(search.getBoundsInLocal()).getMaxY());
    }
  }

  /**
   * Sets the search result to the list view and displays it.
   *
   * @param result the search result to be displayed
   */
  public void setResult(ObservableList<Document> result) {
    documentListView.getItems().addAll(result);
  }

}
