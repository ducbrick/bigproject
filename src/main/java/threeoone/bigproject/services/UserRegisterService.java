package threeoone.bigproject.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@RequiredArgsConstructor
public class UserRegisterService {
  private final UserRepo userRepo;
  private final LoginService loginService;
  private final IllegalCharacterFilterService illegalCharacterFilterService;

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
  @Transactional
  public void register(@NonNull User user) throws UserAlreadyExistException, IllegalCredentialsException {
    if (loginService.getLoggedInUserId() != null) {
      throw new AlreadyLoggedInException("Users shouldn't register while currently logged in");
    }

    user.setUsername(user.getUsername().stripTrailing());

    if (user.getUsername().isEmpty()) {
      throw new IllegalCredentialsException("Username can't be empty");
    }

    if (user.getPassword().isEmpty()) {
      throw new IllegalCredentialsException("Password can't be empty");
    }

    if (userRepo.findByUsername(user.getUsername()) != null) {
      throw new UserAlreadyExistException("There is another registered user with that username");
    }

    user.setId(null);
    userRepo.save(user);
  }
}
