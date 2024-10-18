package threeoone.bigproject.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.view.ViewSwitcher;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.UserInfo;
import threeoone.bigproject.controller.viewcontrollers.HelloWorldController;

@Component
public class DemoController {
  private final HelloWorldController helloWorldController;
  private final ViewSwitcher viewSwitcher;

  public DemoController(HelloWorldController helloWorldController, ViewSwitcher viewSwitcher) {
    this.helloWorldController = helloWorldController;
    this.viewSwitcher = viewSwitcher;
  }

  @Autowired
  private void registerRequestReceiver(RequestSender <UserInfo> helloWorldRequestSender) {
    helloWorldRequestSender.registerReceiver(this::helloWorld);
  }

  private void helloWorld(UserInfo userInfo) {
    helloWorldController.setUserName(userInfo.name());
    viewSwitcher.switchToView(helloWorldController);
  }
}
