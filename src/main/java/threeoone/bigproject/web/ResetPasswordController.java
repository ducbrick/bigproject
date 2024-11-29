package threeoone.bigproject.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.services.resetpassword.PasswordResetAuthenticationService;

@Controller
@RequiredArgsConstructor
public class ResetPasswordController {
  private final PasswordResetAuthenticationService authenticationService;

  @GetMapping("${api.resetpassword.endpoint}")
  public String resetPassword(String tokenValue) {
    User user = authenticationService.authenticate(tokenValue);

    if (user == null) {
      return "requestdenied.html";
    }

    return "requestauthenticated.html";
  }
}
