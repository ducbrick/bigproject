package threeoone.bigproject.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.entities.User;

@Controller
public class ResetPasswordConstroller {
  @Autowired
  private void registerRequestSenders(RequestSender <User> resetPasswordRequestSender) {
    resetPasswordRequestSender.registerReceiver(this::resetPassword);
  }

  private void resetPassword(User user) {
  }
}
