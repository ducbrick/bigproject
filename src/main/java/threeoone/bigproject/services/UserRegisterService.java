package threeoone.bigproject.services;

import threeoone.bigproject.entities.User;
import threeoone.bigproject.exceptions.AlreadyLoggedInException;
import threeoone.bigproject.exceptions.IllegalCredentialsException;
import threeoone.bigproject.exceptions.UserAlreadyExistException;
import threeoone.bigproject.repositories.UserRepo;

public class UserRegisterService {
  private final UserRepo userRepo;
  private final LoginService loginService;

  public UserRegisterService(UserRepo userRepo, LoginService loginService) {
    this.userRepo = userRepo;
    this.loginService = loginService;
  }

  //TODO: Check User constraints
  public void register(User user) throws UserAlreadyExistException, IllegalCredentialsException {
    if (user == null) {
      throw new IllegalArgumentException("Tried to register with a NULL User");
    }

    if (loginService.getLoggedInUserId() != null) {
      throw new AlreadyLoggedInException("Users shouldn't register while currently logged in");
    }

    if (userRepo.findByLoginName(user.getLoginName()) != null) {
      throw new UserAlreadyExistException("There is another registered user with that username");
    }

    userRepo.save(user);
  }
}
