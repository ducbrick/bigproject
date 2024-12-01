package threeoone.bigproject.controller.controllers;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.viewcontrollers.LoginController;
import threeoone.bigproject.controller.viewcontrollers.ResetPasswordView;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.services.persistence.UserPersistenceService;
import threeoone.bigproject.util.Alerts;
import threeoone.bigproject.view.ViewSwitcher;

@Controller
@RequiredArgsConstructor
@Validated
public class ResetPasswordConstroller {
  private final Logger logger = LoggerFactory.getLogger(ResetPasswordConstroller.class);

  private final ViewSwitcher viewSwitcher;

  private final ResetPasswordView resetPasswordView;
  private final LoginController loginController;

  private final UserPersistenceService userPersistenceService;

  @Autowired
  private void registerRequestSenders(
    RequestSender <User> redirectToPasswordResetPageRequestSender,
      RequestSender <User> resetPasswordRequestSender) {
    redirectToPasswordResetPageRequestSender.registerReceiver(this::redirectToPage);
    resetPasswordRequestSender.registerReceiver(this::resetPassword);
  }

  private void redirectToPage(@NotNull @Valid User user) {
    /*Should not throw any exceptions*/
    resetPasswordView.setUser(user);
    viewSwitcher.switchToView(resetPasswordView);
  }

  private void resetPassword(@NotNull @Valid User user) {
    try {
      userPersistenceService.update(user);
      Alerts.showAlertInfo("Success", "Password reset! Sign in to continue");
      viewSwitcher.switchToView(loginController);
    }
    catch (ConstraintViolationException e) {
      Alerts.showAlertWarning("Error!", e.getConstraintViolations().iterator().next().getMessage());
    }
    catch (RuntimeException e) {
      logger.warn(e.getMessage());
    }
  }
}
