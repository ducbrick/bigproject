package threeoone.bigproject.services.resetpassword;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import threeoone.bigproject.entities.PasswordResetToken;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.repositories.PasswordResetTokenRepo;

@Service
@RequiredArgsConstructor
public class PasswordResetAuthenticationService {
  private final PasswordResetTokenRepo tokenRepo;

  @Transactional
  public User authenticate(String tokenValue) {
    var token = tokenRepo.findByValue(tokenValue);

    if (token == null) {
      return null;
    }

    User user = token.getUser();

    tokenRepo.delete(token);

    return user;
  }
}
