package threeoone.bigproject.controller.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.controller.viewcontrollers.*;
import threeoone.bigproject.view.ViewSwitcher;

/**
 * Main controller responsible for managing scene switching.
 * This controller is a Spring Component and is injected with necessary dependencies.
 * It registers to receive specific types of requests and handles them accordingly.
 * This is Controller version to use instead of DemoController before
 * It is constructed with many view controller
 *
 * @author HUY1902
 */
@Component
@RequiredArgsConstructor
public class Controller {
  private final MenuController menuController;
  private final ViewSwitcher viewSwitcher;
  private final DocOverviewController docOverviewController;
  private final DocumentDetailController documentDetailController;
  private final LoginController loginController;
  private final YourBooksController yourBooksController;
  private final RegisterController registerController;
  private final AddNewDocController addNewDocController;
  private final SearchBarController searchBarController;
  private final EditDocumentController editDocumentController;
  private final EditMemController editMemController;

  private final MemberListController memberListController;
  private final AddNewMemController addNewMemController;

  /**
   * Registers request receivers for scene switching and document detail handling.
   *
   * @param switchSceneRequestSender the request sender for scene switching
   */
  @Autowired
  private void registerRequestReceiver(
          RequestSender<SwitchScene> switchSceneRequestSender) {
    switchSceneRequestSender.registerReceiver(this::switchScene);
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
      case LOGIN:
        viewSwitcher.switchToView(loginController);
        break;
      case YOUR_BOOKS:
        viewSwitcher.switchToView(yourBooksController);
        break;
      case SIGNUP:
        viewSwitcher.switchToView(registerController);
        break;
      case ADD_NEW_DOC:
        viewSwitcher.switchToView(addNewDocController);
        break;
      case SEARCH:
        viewSwitcher.switchToView(searchBarController);
        break;
      case EDIT_DOC:
        viewSwitcher.switchToView(editDocumentController);
        break;
      case MEM_LIST:
        viewSwitcher.switchToView(memberListController);
        break;
      case ADD_MEM:
        viewSwitcher.switchToView(addNewMemController);
        break;
      case EDIT_MEM:
        viewSwitcher.switchToView(editMemController);
        break;
    }
  }
}
