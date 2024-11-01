package threeoone.bigproject.services;

import java.util.Objects;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.exceptions.AlreadyLoggedInException;
import threeoone.bigproject.repositories.UserRepo;

public class LoginService {
  private final UserRepo userRepo;

  private Integer loggedInUserId;

  public LoginService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  public Integer getLoggedInUserId() {
    return loggedInUserId;
  }

  public void logout() {
    loggedInUserId = null;
  }

  //TODO: Implement a password hashing algorithm
  public boolean login(User user) {
    if (loggedInUserId != null) {
      throw new AlreadyLoggedInException("A user has already logged in. A user should be able to log in only if no other user is currently logged in");
    }

    String loginName = user.getLoginName();
    String password = user.getPassword();

    User registedUser = userRepo.findByLoginName(loginName);

    if (registedUser == null) {
      return false;
    }

    if (Objects.equals(registedUser.getPassword(), password) == false) {
      return false;
    }

    loggedInUserId = registedUser.getId();
    return true;
  }
}
