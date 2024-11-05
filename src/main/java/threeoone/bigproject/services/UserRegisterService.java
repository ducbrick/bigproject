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
  private final IllegalCharacterFilterService illegalCharacterFilterService;

  /**
   * Autowired constructor to obtain necessary dependencies
   *
   * @param userRepo a User repository
   * @param loginService a {@link LoginService}
   * @param illegalCharacterFilterService User's loginName and password security check
   */
  public UserRegisterService(UserRepo userRepo, LoginService loginService,
      IllegalCharacterFilterService illegalCharacterFilterService) {
    this.userRepo = userRepo;
    this.loginService = loginService;
    this.illegalCharacterFilterService = illegalCharacterFilterService;
  }

  /**
   * Register a new {@link User} into the Database.
   * Not login when register successfully
   *
   * @param user a User with necessary information to register.
   *
   * @throws UserAlreadyExistException when another User with the same loginName exists
   * @throws IllegalCredentialsException when input User have illegal credentials, such as empty name etc
   * @throws RuntimeException when fails to save User into the database
   */
  public void register(User user) throws UserAlreadyExistException, IllegalCredentialsException {
    if (user == null) {
      throw new IllegalArgumentException("Tried to register with a NULL User");
    }

    if (loginService.getLoggedInUserId() != null) {
      throw new AlreadyLoggedInException("Users shouldn't register while currently logged in");
    }

    user.setLoginName(user.getLoginName().stripTrailing());
    user.setDisplayName(user.getDisplayName().stripTrailing());

    if (user.getLoginName().isEmpty()) {
      throw new IllegalCredentialsException("Username can't be empty");
    }

    if (user.getPassword().isEmpty()) {
      throw new IllegalCredentialsException("Password can't be empty");
    }

    if (user.getDisplayName().isEmpty()) {
      throw new IllegalCredentialsException("Display name can't be empty");
    }

    if (userRepo.findByLoginName(user.getLoginName()) != null) {
      throw new UserAlreadyExistException("There is another registered user with that username");
    }

    if (illegalCharacterFilterService.hasIllegalCharacter(user.getLoginName())) {
      throw new IllegalCredentialsException("Username can only contain alphabet characters and/or digits.");
    }

    if (illegalCharacterFilterService.hasIllegalCharacter(user.getPassword())) {
      throw new IllegalCredentialsException("Password can only contain alphabet characters and/or digits.");
    }

    userRepo.save(user);
  }
}
