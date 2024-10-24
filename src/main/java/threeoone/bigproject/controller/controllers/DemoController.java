package threeoone.bigproject.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.controller.viewcontrollers.*;

import threeoone.bigproject.entities.Document;
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
  private final ViewSwitcher viewSwitcher;
  private final DocOverviewController docOverviewController;
  private final GetNameController getNameController;
  private final DocumentDetailController documentDetailController;
  private final AddDocController addDocController;


  public DemoController(HelloWorldController helloWorldController, MenuController menuController,
                        DocOverviewController docOverviewController, ViewSwitcher viewSwitcher,
                        GetNameController getNameController, DocumentDetailController documentDetailController, AddDocController addDocController) {
    this.helloWorldController = helloWorldController;
    this.menuController = menuController;
    this.docOverviewController = docOverviewController;
    this.viewSwitcher = viewSwitcher;
    this.getNameController = getNameController;
    this.documentDetailController = documentDetailController;
    this.addDocController = addDocController;
  }

  @Autowired
  private void registerRequestReceiver(RequestSender<UserInfo> helloWorldRequestSender,
                                       RequestSender<String> menuRequestSender,
                                       RequestSender<SwitchScene> switchSceneRequestSender,
                                       RequestSender<Document> documentRequestSender) {
    helloWorldRequestSender.registerReceiver(this::helloWorld);
    menuRequestSender.registerReceiver(this::menu);
    switchSceneRequestSender.registerReceiver(this::switchScene);
    documentRequestSender.registerReceiver(this::documentDetail);
  }


  private void helloWorld(UserInfo userInfo) {
    helloWorldController.setUserName(userInfo.name());
    viewSwitcher.switchToView(helloWorldController);
  }

  private void documentDetail(Document document) {
    documentDetailController.setDocument(document);
  }

  private void menu(String WHY) {
    viewSwitcher.switchToView(menuController);
  }

  private void switchScene(SwitchScene switchScene) {
    switch (switchScene.nameScene()) {
      case DOC_OVERVIEW:
        viewSwitcher.switchToView(docOverviewController);
        break;
      case GET_NAME:
        viewSwitcher.switchToView(getNameController);
        break;
      case DOC_DETAIL:
        viewSwitcher.switchToView(documentDetailController);
        break;
      case ADD_DOC:
        viewSwitcher.switchToView(addDocController);
        break;
    }
  }
}
