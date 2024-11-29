package threeoone.bigproject.web;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.services.resetpassword.PasswordResetAuthenticationService;

@Controller
@RequiredArgsConstructor
public class ResetPasswordController {
  private final Validator validator;

  private final PasswordResetAuthenticationService authenticationService;
  private final RequestSender <User> redirectToPasswordResetPageRequestSender;

  @GetMapping("${api.resetpassword.endpoint}")
  public String resetPassword(
      @RequestParam("${api.resetpassword.param.tokenvalue}") String tokenValue) {
    User user = authenticationService.authenticate(tokenValue);

    if (user == null || !validator.validate(user).isEmpty()) {
      return "requestdenied.html";
    }

    redirectToPasswordResetPageRequestSender.send(user);

    return "requestauthenticated.html";
  }
}
