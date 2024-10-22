package threeoone.bigproject.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.viewcontrollers.MenuController;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.view.ViewSwitcher;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.UserInfo;
import threeoone.bigproject.controller.viewcontrollers.HelloWorldController;

@Component
public class DemoController {
  private final HelloWorldController helloWorldController;
  private final MenuController menuController;
  private final ViewSwitcher viewSwitcher;

  public DemoController(HelloWorldController helloWorldController, MenuController menuController, ViewSwitcher viewSwitcher) {
    this.helloWorldController = helloWorldController;
    this.menuController = menuController;
    this.viewSwitcher = viewSwitcher;
  }

  @Autowired
  private void registerRequestReceiver(RequestSender <UserInfo> helloWorldRequestSender, RequestSender <String> menuRequestSender) {
    helloWorldRequestSender.registerReceiver(this::helloWorld);
    menuRequestSender.registerReceiver(this::menu);
  }


  private void helloWorld(UserInfo userInfo) {
    helloWorldController.setUserName(userInfo.name());
    viewSwitcher.switchToView(helloWorldController);
  }

  private void menu(String WHY) {
    viewSwitcher.switchToView(menuController);
  }
}
