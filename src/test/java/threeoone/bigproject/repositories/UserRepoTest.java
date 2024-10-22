package threeoone.bigproject.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import threeoone.bigproject.entities.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepoTest {
  @Autowired
  private UserRepo userRepo;

  @Test
  @DisplayName("Count before and after single insertion")
  public void countBeforeAndAfterSingleInsertion() {
    long countBefore = userRepo.count();

    userRepo.save(new User("loginname", "password", "display name"));

    long countAfter = userRepo.count();

    assertThat(countAfter).isEqualTo(countBefore + 1);
  }

  @Test
  @DisplayName("Count before and after multiple insertion")
  public void countBeforeAndAfterMultipleInsertion() {
    long countBefore = userRepo.count();

    List <User> users = new ArrayList <> ();
    users.add(new User("username a", "password a", "display name a"));
    users.add(new User("username b", "password b", "display name b"));
    users.add(new User("username c", "password c", "display name c"));
    users.add(new User("username d", "password d", "display name d"));

    for (User user : users) {
      userRepo.save(user);
    }

    long countAfter = userRepo.count();

    assertThat(countAfter).isEqualTo(countBefore + users.size());
  }

  @Test
  @DisplayName("Count before and after list insertion")
  public void countBeforeAndAfterListInsertion() {
    long countBefore = userRepo.count();

    List <User> users = new ArrayList <> ();
    users.add(new User("username a", "password a", "display name a"));
    users.add(new User("username b", "password b", "display name b"));
    users.add(new User("username c", "password c", "display name c"));
    users.add(new User("username d", "password d", "display name d"));

    userRepo.saveAll(users);

    long countAfter = userRepo.count();

    assertThat(countAfter).isEqualTo(countBefore + users.size());
  }
}