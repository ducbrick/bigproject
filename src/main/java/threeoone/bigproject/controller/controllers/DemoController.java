package threeoone.bigproject.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.controller.viewcontrollers.*;

import threeoone.bigproject.entities.User;
import threeoone.bigproject.view.ViewSwitcher;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.UserInfo;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class DemoController {
  private final HelloWorldController helloWorldController;
  private final MenuController menuController;
  private final YourBooksController yourBooksController;
  private final ViewSwitcher viewSwitcher;
  private final DocOverviewController docOverviewController;
  private final GetNameController getNameController;


  public DemoController(HelloWorldController helloWorldController, MenuController menuController, YourBooksController yourBooksController,
                        DocOverviewController docOverviewController, ViewSwitcher viewSwitcher, GetNameController getNameController) {
    this.helloWorldController = helloWorldController;
    this.menuController = menuController;
    this.yourBooksController = yourBooksController;
    this.docOverviewController = docOverviewController;

    this.viewSwitcher = viewSwitcher;
    this.getNameController = getNameController;
  }

  @Autowired
  private void registerRequestReceiver(RequestSender <UserInfo> helloWorldRequestSender, RequestSender <String> menuRequestSender,
                                       RequestSender<SwitchScene> switchSceneRequestSender) {
    helloWorldRequestSender.registerReceiver(this::helloWorld);
    menuRequestSender.registerReceiver(this::menu);
    switchSceneRequestSender.registerReceiver(this::switchScene);
  }



  private void helloWorld(UserInfo userInfo) {
    helloWorldController.setUserName(userInfo.name());
    viewSwitcher.switchToView(helloWorldController);
  }

  private void menu(String WHY) {
    viewSwitcher.switchToView(menuController);
  }

  private void switchScene(SwitchScene switchScene) {
    switch (switchScene.nameScene()) {
      case "YourBooks" :
        viewSwitcher.switchToView(yourBooksController);
        break;
      case "DocOverview":
        viewSwitcher.switchToView(docOverviewController);
        break;
      case "getName":
        viewSwitcher.switchToView(getNameController);
        break;
    }
  }
}
