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
import threeoone.bigproject.entities.User;
import threeoone.bigproject.services.persistence.UserPersistenceService;
import threeoone.bigproject.util.Alerts;

@Controller
@RequiredArgsConstructor
@Validated
public class ResetPasswordConstroller {
  private final Logger logger = LoggerFactory.getLogger(ResetPasswordConstroller.class);

  private final UserPersistenceService userPersistenceService;

  @Autowired
  private void registerRequestSenders(RequestSender <User> resetPasswordRequestSender) {
    resetPasswordRequestSender.registerReceiver(this::resetPassword);
  }

  private void resetPassword(@NotNull @Valid User user) {
    try {
      userPersistenceService.update(user);
    }
    catch (ConstraintViolationException e) {
      Alerts.showAlertWarning("Error!", e.getConstraintViolations().iterator().next().getMessage());
    }
    catch (RuntimeException e) {
      logger.warn(e.getMessage());
    }
  }
}
