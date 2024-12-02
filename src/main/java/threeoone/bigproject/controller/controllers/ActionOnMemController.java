package threeoone.bigproject.controller.controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Set;
import javafx.collections.FXCollections;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.controller.viewcontrollers.*;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.services.forgotpassword.PasswordResetLinkSenderService;
import threeoone.bigproject.services.persistence.MemberEditingService;
import threeoone.bigproject.services.retrieval.MemberRetrievalService;
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
  private final Logger logger = LoggerFactory.getLogger(ActionOnMemController.class);

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
      logger.warn(e.getMessage());
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
      Alerts.showAlertWarning("Error!", "Unexpected error occured! Please try again later.");

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
    }
    catch (ConstraintViolationException e) {
      /*Should not happen*/
      Set <ConstraintViolation <?>> violations = e.getConstraintViolations();
      Alerts.showAlertWarning("Error!", violations.iterator().next().getMessage());
    }
    catch (Exception e) {
      Alerts.showAlertWarning("Error!", "Unexpected error occured");
      logger.warn(e.getMessage());
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
