package threeoone.bigproject.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.controller.viewcontrollers.*;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.view.ViewSwitcher;

import static threeoone.bigproject.controller.SceneName.*;

/**
 * Main controller responsible for managing scene switching and document detail handling.
 * This controller is a Spring Component and is injected with necessary dependencies.
 * It registers to receive specific types of requests and handles them accordingly.
 * This is Controller version to use instead of DemoController before
 *
 * @author HUY1902
 */
@Component
public class Controller {
  private final MenuController menuController;
  private final ViewSwitcher viewSwitcher;
  private final DocOverviewController docOverviewController;
  private final DocumentDetailController documentDetailController;
  private final YourBooksController yourBooksController;

  /**
   * Constructs the main controller with dependencies.
   *
   * @param menuController           the controller for the menu view
   * @param docOverviewController    the controller for the document overview view
   * @param viewSwitcher             the component responsible for switching views
   * @param documentDetailController the controller for the document detail view
   */
  public Controller(MenuController menuController,
                    DocOverviewController docOverviewController,
                    YourBooksController yourBooksController,
                    ViewSwitcher viewSwitcher,
                    DocumentDetailController documentDetailController) {
    this.menuController = menuController;
    this.docOverviewController = docOverviewController;
    this.viewSwitcher = viewSwitcher;
    this.documentDetailController = documentDetailController;
    this.yourBooksController = yourBooksController;
  }

  /**
   * Registers request receivers for scene switching and document detail handling.
   *
   * @param switchSceneRequestSender the request sender for scene switching
   * @param documentRequestSender    the request sender for document detail
   */
  @Autowired
  private void registerRequestReceiver(
          RequestSender<SwitchScene> switchSceneRequestSender,
          RequestSender<Document> documentRequestSender) {
    switchSceneRequestSender.registerReceiver(this::switchScene);
    documentRequestSender.registerReceiver(this::documentDetail);
  }

  /**
   * Handles the reception of a document detail request and forwards the document
   * to the DocumentDetailController.
   *
   * @param document the document whose details are to be shown
   */
  private void documentDetail(Document document) {
    documentDetailController.setDocument(document);
  }

  /**
   * Handles the reception of a scene switch request and switches to the requested scene.
   *
   * @param switchScene the switch scene request containing the target scene name
   */
  private void switchScene(SwitchScene switchScene) {
    switch (switchScene.nameScene()) {
      case DOC_OVERVIEW:
        viewSwitcher.switchToView(docOverviewController);
        break;
      case MAIN_MENU:
        viewSwitcher.switchToView(menuController);
        break;
      case DOC_DETAIL:
        viewSwitcher.switchToView(documentDetailController);
        break;
      case YOUR_BOOKS:
        viewSwitcher.switchToView(yourBooksController);
        break;
    }
  }
}
