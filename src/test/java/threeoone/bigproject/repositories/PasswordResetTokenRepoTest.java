package threeoone.bigproject.repositories;

import java.time.LocalDateTime;
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

    user = userRepo.save(user);

    var token = new PasswordResetToken();
    token.setValue("skdjfasdfjksldfasd");
    token.setExpireTime(LocalDateTime.now());
    token.setUser(user);

    token = passwordResetTokenRepo.save(token);
  }
}