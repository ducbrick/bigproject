package threeoone.bigproject.controller.controllers;

import javafx.collections.FXCollections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.MemActionType;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.ActionOnMem;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.controller.viewcontrollers.AddNewMemController;
import threeoone.bigproject.controller.viewcontrollers.EditMemController;
import threeoone.bigproject.controller.viewcontrollers.MemberListController;
import threeoone.bigproject.controller.viewcontrollers.MenuController;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.services.MemberEditingService;
import threeoone.bigproject.services.MemberRetrievalService;

/**
 * Interacting with view controller and service
 * Handle logic for Member manipulation
 * Get information from service and send action to view based on that information
 *
 * @author HUY1902
 */
@Component
@RequiredArgsConstructor
public class ActionOnMemController {
  private final MemberListController memberListController;
  private final MemberRetrievalService memberRetrievalService;
  private final MemberEditingService memberEditingService;
  private final AddNewMemController addNewMemController;
  private final EditMemController editMemController;
  private final MenuController menuController;
   /**
    * Registers request receivers for document handling.
    * @param getAllMembersRequestSender the request sender for getting all member
   */
  @Autowired
  private void registerRequestReceiver (RequestSender<SwitchScene> getAllMembersRequestSender,
                                        RequestSender<ActionOnMem> actionOnMemRequestSender,
                                        RequestSender<SwitchScene> getTopFiveMembersRequestSender) {
    getAllMembersRequestSender.registerReceiver(this::getListAllMembers);
    actionOnMemRequestSender.registerReceiver(this::actionOnMem);
    getTopFiveMembersRequestSender.registerReceiver(this::getTop5);
  }

  /**
   * return List of All Member in dataset
   *
   * @param
   */
  private void getListAllMembers (SwitchScene switchScene) {
    memberListController.setTable(FXCollections.observableArrayList(
            memberRetrievalService.getAll()
    ));
  }

  private void getTop5(SwitchScene switchScene) {
    menuController.setUserList(FXCollections.observableArrayList(memberRetrievalService.findTop5Records()));
  }
  /**
   * Call service and switch scene if needed for make an action on member
   *
   * @param actionOnMem action request
   */
  private void actionOnMem(ActionOnMem actionOnMem) {
    switch (actionOnMem.type()) {
      case MemActionType.ADD -> addNewMemController.setMember(memberEditingService.update(actionOnMem.member()));
      case MemActionType.EDIT -> editMemController.setChosenMember(actionOnMem.member());
      case MemActionType.COMMIT_EDIT -> memberEditingService.update(actionOnMem.member());
    }
  }
}
