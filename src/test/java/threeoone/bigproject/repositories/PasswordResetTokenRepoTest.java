package threeoone.bigproject.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import threeoone.bigproject.entities.PasswordResetToken;
import threeoone.bigproject.entities.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class PasswordResetTokenRepoTest {
  @Autowired
  UserRepo userRepo;

  @Autowired
  PasswordResetTokenRepo passwordResetTokenRepo;

  @Test
  void test() {
    User user = new User();
    user.setUsername("skibidi");
    user.setPassword("rizz");
    user.setEmail("skibidi@rizzler.mog");

    user = userRepo.save(user);

    var token = new PasswordResetToken();
    token.setValue("skdjfasdfjksldfasd");
    token.setExpireTime(LocalDateTime.now());
    token.setUser(user);

    token = passwordResetTokenRepo.save(token);
  }

  @Test
  @DisplayName("Delete tokens associated to a user")
  void deleteTokensAssociatedToAUser() {
    User user = new User();
    user.setUsername("skibidi");
    user.setPassword("rizz");
    user.setEmail("skibidi@rizzler.mog");

    user = userRepo.save(user);

    var token1 = new PasswordResetToken();
    token1.setValue("skdjfakjsdfskdjf");
    token1.setExpireTime(LocalDateTime.now());
    token1.setUser(user);

    token1 = passwordResetTokenRepo.save(token1);

    var token2 = new PasswordResetToken();
    token2.setValue("sdjauasjdsdkjfak");
    token2.setExpireTime(LocalDateTime.now());
    token2.setUser(user);

    token2 = passwordResetTokenRepo.save(token2);

    passwordResetTokenRepo.deleteByUser(user.getId());

    assertThat(passwordResetTokenRepo.existsById(token1.getId())).isFalse();
    assertThat(passwordResetTokenRepo.existsById(token2.getId())).isFalse();
  }
}