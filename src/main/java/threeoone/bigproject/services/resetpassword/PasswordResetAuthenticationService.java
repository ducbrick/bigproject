package threeoone.bigproject.services.resetpassword;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threeoone.bigproject.entities.PasswordResetToken;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.repositories.PasswordResetTokenRepo;

@Service
@RequiredArgsConstructor
public class PasswordResetAuthenticationService {
  private final PasswordResetTokenRepo tokenRepo;

  public User authenticate(String tokenValue) {
    var token = tokenRepo.findByValue(tokenValue);

    if (token == null) {
      return null;
    }

    return token.getUser();
  }
}
