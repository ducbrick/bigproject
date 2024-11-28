package threeoone.bigproject.services.resetpassword;

import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import threeoone.bigproject.entities.PasswordResetToken;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.repositories.PasswordResetTokenRepo;
import threeoone.bigproject.repositories.UserRepo;
import threeoone.bigproject.services.EmailSenderService;
import threeoone.bigproject.services.RandomStringGeneratorService;

@Service
@Validated
@RequiredArgsConstructor
public class PasswordResetLinkSenderService {
  public static int VALID_TIME_IN_MINUTES = 10;

  private final EmailSenderService emailSender;
  private final UserRepo userRepo;
  private final PasswordResetTokenRepo tokenRepo;
  private final RandomStringGeneratorService randomStringGeneratorService;

  @Value("${server.url}")
  private String serverUrl;

  @Value("${api.resetpassword.endpoint}")
  private String passwordResetApiEndpoint;

  @Value("${api.resetpassword.param.tokenvalue}")
  private String passwordResetApiTokenParam;

  private final Logger logger = LoggerFactory.getLogger(PasswordResetLinkSenderService.class);

  @Transactional
  public boolean sendResetLink(String username) {
    User user = userRepo.findByUsername(username);

    if (user == null) {
      return false;
    }

    /*Delete previous tokens to avoid loitering*/
    tokenRepo.deleteByUser(user.getId());

    var token = new PasswordResetToken();

    token.setValue(randomStringGeneratorService.generate(PasswordResetToken.MAX_TOKEN_LENGTH));
    token.setExpireTime(LocalDateTime.now().plusMinutes(VALID_TIME_IN_MINUTES));
    token.setUser(user);

    token = tokenRepo.save(token);

    String url
        = serverUrl
        + passwordResetApiEndpoint
        + "?"
        + passwordResetApiTokenParam
        + "="
        + token.getValue();

    System.out.println("""
              You made a request to reset your password.
              Please follow this link to do so:
              %s
              If you did not make such a request, kindly ignore this email""".formatted(url));

    try {
      emailSender.sendEmail(
          user.getEmail(),
          "Reset password for user " + username,
          """
              You made a request to reset your password.
              Please follow this link to do so:
              %s
              If you did not make such a request, kindly ignore this email""".formatted(url));
    } catch (MessagingException e) {
      logger.warn("Failed to send the Email containing the password reset link to User");
      logger.trace(e.getMessage());
    }

    return true;
  }
}
