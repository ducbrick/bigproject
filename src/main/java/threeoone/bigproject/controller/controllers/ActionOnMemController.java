package threeoone.bigproject.controller.controllers;

import javafx.collections.FXCollections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.controller.viewcontrollers.*;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.services.MemberEditingService;
import threeoone.bigproject.services.MemberRetrievalService;
import threeoone.bigproject.util.Alerts;

import java.util.Comparator;
import java.util.List;

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
  private final MemberDetailsController memberDetailsController;

  /**
   * Registers request receivers for document handling.
   *
   * @param getAllMembersRequestSender the request sender for getting all member
   */
  @Autowired
  private void registerRequestReceiver(RequestSender<Member> getAllMembersRequestSender,
                                       RequestSender<Member> editMemberRequestSender,
                                       RequestSender<Member> commitChangeMemberRequestSender,
                                       RequestSender<Member> removeMemberRequestSender,
                                       RequestSender<Member> addMemberRequestSender,
                                       RequestSender<SwitchScene> getTopFiveMembersRequestSender,
                                       RequestSender<Member> memberDetailsRequestSender) {
    getAllMembersRequestSender.registerReceiver(this::getListAllMembers);
    editMemberRequestSender.registerReceiver(this::editMember);
    commitChangeMemberRequestSender.registerReceiver(this::commitChangeMember);
    removeMemberRequestSender.registerReceiver(this::removeMember);
    addMemberRequestSender.registerReceiver(this::addMember);
    getTopFiveMembersRequestSender.registerReceiver(this::getTop5);
    memberDetailsRequestSender.registerReceiver(this::memberDetail);
  }

  /**
   * return List of All Member in dataset
   *
   * @param member None
   */
  private void getListAllMembers(Member member) {
    try {
      List<Member> memberList = memberRetrievalService.getAll();
      memberList.sort(Comparator.comparing(Member::getId));
      memberListController.setTable(FXCollections.observableArrayList(memberList));
    } catch (Exception e) {
      Alerts.showAlertWarning("Error!", e.getMessage());
    }
  }

  private void getTop5(SwitchScene switchScene) {
    menuController.setUserList(FXCollections.observableArrayList(memberRetrievalService.findTop5Records()));
  }

  /**
   * Edits the given member by setting it in the edit member controller.
   *
   * @param member the member to be edited
   */
  private void editMember(Member member) {
    try {
      editMemController.setChosenMember(member);
    } catch (Exception e) {
      Alerts.showAlertWarning("Error!", e.getMessage());
    }
  }

  /**
   * Adds a new member using the member editing service and sets it in the add new member controller.
   *
   * @param member the member to be added
   */
  private void addMember(Member member) {
    try {
      addNewMemController.setMember(memberEditingService.add(member));
    } catch (Exception e) {
      Alerts.showAlertWarning("Error!", e.getMessage());
    }
  }

  /**
   * Commits changes to the given member by updating it using the member editing service.
   *
   * @param member the member whose changes are to be committed
   */
  private void commitChangeMember(Member member) {
    try {
      memberEditingService.update(member);
    } catch (Exception e) {
      Alerts.showAlertWarning("Error!", e.getMessage());
    }
  }

  /**
   * Removes the given member by deleting it using the member editing service.
   *
   * @param member the member to be removed
   */
  private void removeMember(Member member) {
    try {
      memberEditingService.delete(member.getId());
    } catch (Exception e) {
      Alerts.showAlertWarning("Error!", e.getMessage());
    }
  }

  private void memberDetail(Member member) {
    Member temp = memberRetrievalService.findWithLendingDetails(member.getId());
    memberDetailsController.setMember(temp);
  }
}
