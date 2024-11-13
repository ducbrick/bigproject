package threeoone.bigproject.controller.controllers;

import javafx.collections.FXCollections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
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
                                        RequestSender<Member> editMemberRequestSender,
                                         RequestSender<Member> commitChangeMemberRequestSender,
                                        RequestSender<Member> removeMemberRequestSender,
                                        RequestSender<Member> addMemberRequestSender) {
    getAllMembersRequestSender.registerReceiver(this::getListAllMembers);
    editMemberRequestSender.registerReceiver(this::editMember);
    commitChangeMemberRequestSender.registerReceiver(this::commitChangeMember);
    removeMemberRequestSender.registerReceiver(this::removeMember);
    addMemberRequestSender.registerReceiver(this::addMember);
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
   * Edits the given member by setting it in the edit member controller.
   *
   * @param member the member to be edited
   */
  private void editMember(Member member) {
    editMemController.setChosenMember(member);
  }

  /**
   * Adds a new member using the member editing service and sets it in the add new member controller.
   *
   * @param member the member to be added
   */
  private void addMember(Member member) {
    addNewMemController.setMember(memberEditingService.add(member));
  }

  /**
   * Commits changes to the given member by updating it using the member editing service.
   *
   * @param member the member whose changes are to be committed
   */
  private void commitChangeMember(Member member) {
    memberEditingService.update(member);
  }

  /**
   * Removes the given member by deleting it using the member editing service.
   *
   * @param member the member to be removed
   */
  private void removeMember(Member member) {
    memberEditingService.delete(member.getId());
  }

}
