package threeoone.bigproject.controller.controllers;

import javafx.collections.FXCollections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.viewcontrollers.MemberListController;
import threeoone.bigproject.entities.Member;
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


   /**
    * Registers request receivers for document handling.
    * @param getAllMembersRequestSender the request sender for getting all member
   */
  @Autowired
  private void registerRequestReceiver (RequestSender<Member> getAllMembersRequestSender) {
    getAllMembersRequestSender.registerReceiver(this::getListAllMembers);
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
}
