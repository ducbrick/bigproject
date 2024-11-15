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
 * It is constructed with many view controller.
 *
 * @author HUY1902
 */
@Component
@RequiredArgsConstructor
public class SwitchViewController {
  private final MenuController menuController;
  private final ViewSwitcher viewSwitcher;
  private final DocOverviewController docOverviewController;
  private final DocumentDetailController documentDetailController;
  private final LoginController loginController;
  private final YourBooksController yourBooksController;
  private final RegisterController registerController;
  private final AddNewDocController addNewDocController;
  private final SearchPageController searchPageController;
  private final EditDocumentController editDocumentController;
  private final EditMemController editMemController;
  private final MemberListController memberListController;
  private final AddNewMemController addNewMemController;

  /**
   * Registers request receivers for switching to different scenes.
   *
   * @param switchToDocOverview RequestSender for switching to document overview scene.
   * @param switchToMainMenu    RequestSender for switching to main menu scene.
   * @param switchToDocDetail   RequestSender for switching to document detail scene.
   * @param switchToLogin       RequestSender for switching to login scene.
   * @param switchToSignup      RequestSender for switching to signup scene.
   * @param switchToAddNewDoc   RequestSender for switching to add new document scene.
   * @param switchToSearch      RequestSender for switching to search scene.
   * @param switchToEditDoc     RequestSender for switching to edit document scene.
   * @param switchToMemList     RequestSender for switching to member list scene.
   * @param switchToEditMem     RequestSender for switching to edit member scene.
   * @param switchToAddMem      RequestSender for switching to add new member scene.
   * @param switchToYourBooks   RequestSender for switching to your books scene.
   */
  @Autowired
  private void registerRequestReceiver(
          RequestSender<ViewController> switchToDocOverview,
          RequestSender<ViewController> switchToMainMenu,
          RequestSender<ViewController> switchToDocDetail,
          RequestSender<ViewController> switchToLogin,
          RequestSender<ViewController> switchToSignup,
          RequestSender<ViewController> switchToAddNewDoc,
          RequestSender<ViewController> switchToSearch,
          RequestSender<ViewController> switchToEditDoc,
          RequestSender<ViewController> switchToMemList,
          RequestSender<ViewController> switchToEditMem,
          RequestSender<ViewController> switchToAddMem,
          RequestSender<ViewController> switchToYourBooks
  ) {
    switchToDocOverview.registerReceiver(this::switchToDocOverview);
    switchToMainMenu.registerReceiver(this::switchToMainMenu);
    switchToDocDetail.registerReceiver(this::switchToDocDetail);
    switchToLogin.registerReceiver(this::switchToLogin);
    switchToSignup.registerReceiver(this::switchToSignup);
    switchToAddNewDoc.registerReceiver(this::switchToAddNewDoc);
    switchToSearch.registerReceiver(this::switchToSearch);
    switchToEditDoc.registerReceiver(this::switchToEditDoc);
    switchToMemList.registerReceiver(this::switchToMemList);
    switchToEditMem.registerReceiver(this::switchToEditMem);
    switchToAddMem.registerReceiver(this::switchToAddMem);
    switchToYourBooks.registerReceiver(this::switchToYourBooks);
  }

  /**
   * Switches to the document overview view.
   *
   * @param from the current view controller.
   */
  private void switchToDocOverview(ViewController from) {
    viewSwitcher.switchToView(docOverviewController);
  }

  /**
   * Switches to the main menu view.
   *
   * @param from the current view controller.
   */
  private void switchToMainMenu(ViewController from) {
    viewSwitcher.switchToView(menuController);
  }

  /**
   * Switches to the document detail view.
   *
   * @param from the current view controller.
   */
  private void switchToDocDetail(ViewController from) {
    viewSwitcher.switchToView(documentDetailController);
  }

  /**
   * Switches to the login view.
   *
   * @param from the current view controller.
   */
  private void switchToLogin(ViewController from) {
    viewSwitcher.switchToView(loginController);
  }

  /**
   * Switches to the your books view.
   *
   * @param from the current view controller.
   */
  private void switchToYourBooks(ViewController from) {
    viewSwitcher.switchToView(yourBooksController);
  }

  /**
   * Switches to the signup view.
   *
   * @param from the current view controller.
   */
  private void switchToSignup(ViewController from) {
    viewSwitcher.switchToView(registerController);
  }

  /**
   * Switches to the add new document view.
   *
   * @param from the current view controller.
   */
  private void switchToAddNewDoc(ViewController from) {
    viewSwitcher.switchToView(addNewDocController);
  }

  /**
   * Switches to the search view.
   *
   * @param from the current view controller.
   */
  private void switchToSearch(ViewController from) {
    viewSwitcher.switchToView(searchPageController);
  }

  /**
   * Switches to the edit document view.
   *
   * @param from the current view controller.
   */
  private void switchToEditDoc(ViewController from) {
    viewSwitcher.switchToView(editDocumentController);
  }

  /**
   * Switches to the member list view.
   *
   * @param from the current view controller.
   */
  private void switchToMemList(ViewController from) {
    viewSwitcher.switchToView(memberListController);
  }

  /**
   * Switches to the add new member view.
   *
   * @param from the current view controller.
   */
  private void switchToAddMem(ViewController from) {
    viewSwitcher.switchToView(addNewMemController);
  }

  /**
   * Switches to the edit member view.
   *
   * @param from the current view controller.
   */
  private void switchToEditMem(ViewController from) {
    viewSwitcher.switchToView(editMemController);
  }
}