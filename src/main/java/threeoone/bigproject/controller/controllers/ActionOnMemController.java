package threeoone.bigproject.controller.controllers;

import javafx.collections.FXCollections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.MemActionType;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.ActionOnMem;
import threeoone.bigproject.controller.viewcontrollers.AddNewMemController;
import threeoone.bigproject.controller.viewcontrollers.EditMemController;
import threeoone.bigproject.controller.viewcontrollers.MemberListController;
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

   /**
    * Registers request receivers for document handling.
    * @param getAllMembersRequestSender the request sender for getting all member
   */
  @Autowired
  private void registerRequestReceiver (RequestSender<Member> getAllMembersRequestSender,
                                        RequestSender<ActionOnMem> actionOnMemRequestSender) {
    getAllMembersRequestSender.registerReceiver(this::getListAllMembers);
    actionOnMemRequestSender.registerReceiver(this::actionOnMem);
  }

  /**
   * return List of All Member in dataset
   *
   * @param member None
   */
  private void getListAllMembers (Member member) {
    memberListController.setTable(FXCollections.observableArrayList(
            memberRetrievalService.getAll()
    ));
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
