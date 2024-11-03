package threeoone.bigproject.services;

import org.springframework.stereotype.Service;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.exceptions.AlreadyLoggedInException;
import threeoone.bigproject.exceptions.IllegalCredentialsException;
import threeoone.bigproject.exceptions.UserAlreadyExistException;
import threeoone.bigproject.repositories.UserRepo;

/**
 * A service to register a new {@link User} into the Database.
 * This class is a singleton bean in Spring context.
 *
 * @author DUCBRICK
 */
@Service
public class UserRegisterService {
  private final UserRepo userRepo;
  private final LoginService loginService;

  /**
   * Autowired constructor to obtain necessary dependencies.
   *
   * @param userRepo a {@link User} repository
   * @param loginService a {@link LoginService}
   */
  public UserRegisterService(UserRepo userRepo, LoginService loginService) {
    this.userRepo = userRepo;
    this.loginService = loginService;
  }

  /**
   * Register a new {@link User} into the Database.
   *
   * @param user a User with necessary information to register.
   *
   * @throws UserAlreadyExistException when another User with the same loginName exists
   * @throws IllegalCredentialsException when input User have illegal credentials, such as empty name etc
   */
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
    loginService.login(user);
  }
}
