package threeoone.bigproject.controller.controllers;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.controller.viewcontrollers.LoginController;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.exceptions.AlreadyLoggedInException;
import threeoone.bigproject.services.LoginService;

import static threeoone.bigproject.controller.SceneName.MAIN_MENU;

/**
 * Handles the interaction logic for the Login view.
 * <p>
 * Provides methods for validating User information,
 * authenticating User information through interacting with LoginService
 * </p>
 *
 * @author HUY1902
 */
@Component
public class UserLoginController {
  private final LoginController loginController;
  private final RequestSender<SwitchScene> switchSceneRequestSender;
  private LoginService loginService;

  private int failedAttempts = 0;
  private boolean isPauseOnMaxAttempts = false;
  private static final int MAX_ATTEMPTS = 3;
  private static final int LOCKOUT_DURATION = 5; //second

  /**
   * Constructor the logic resolving controller for LoginPage
   * @param loginController           the controller for login page
   * @param switchSceneRequestSender  the switch scene request sender
   */
  public UserLoginController(LoginController loginController, RequestSender<SwitchScene> switchSceneRequestSender) {
    this.loginController = loginController;
    this.switchSceneRequestSender = switchSceneRequestSender;
  }

  /**
   * Register loginService for controller
   * @param loginService : service handle login
   */
  @Autowired
  public void registerService(LoginService loginService) {
    this.loginService = loginService;
  }


  /**
   * Register request receiver for login request
   *
   * @param loginRequestReceiver login request receiver
   */
  @Autowired
  private void registerLoginRequestReceiver(RequestSender<User> loginRequestReceiver) {
    loginRequestReceiver.registerReceiver(this::authenticateLogin);
  }

  /**
   * Validates the information provided by the user.
   *
   * @return true if the username and password fields are not empty;
   * false otherwise
   */
  public boolean validateInformation(User user) {
    return !(user.getLoginName().isEmpty() || user.getPassword().isEmpty());
  }

  /**
   * <p>
   *   Handle all logic for Login page
   * </p>
   * <p>
   * Authenticating user login. If authentication succeeds, it switches scene to Menu page.
   * If authentication fails, it return login page with a descriptive message.
   * </p>
   * <p>
   * Validates the user's credentials,
   * sends the login request if valid, or displays appropriate messages if invalid.
   * Implements a lockout mechanism after a number of failed attempts.
   * </p>
   * @param user User login
   */
  public void authenticateLogin(User user) {
    if (failedAttempts >= MAX_ATTEMPTS) {
      if (isPauseOnMaxAttempts) {
        isPauseOnMaxAttempts = false;
        loginController.setMessage("Too many attempts. Please wait...");

        PauseTransition pause = new PauseTransition(Duration.seconds(LOCKOUT_DURATION));
        pause.setOnFinished(e -> {
          failedAttempts = 0;
          loginController.setMessage("");
        });
        pause.play();
      }
      return;
    }
    if (validateInformation(user)) {
      try {
        if (loginService.login(user)) {
          switchSceneRequestSender.send(new SwitchScene(MAIN_MENU));
          return;
        }
        else
        {
          ++failedAttempts;
          loginController.setMessage("Username or password doesn't match. Attempt " + failedAttempts);
        }
      } catch (AlreadyLoggedInException e) {
        loginController.setMessage(e.getMessage());
      }
    } else {
      ++failedAttempts;
      loginController.setMessage("Invalid credentials. Attempt " + failedAttempts);
    }

    if (failedAttempts >= MAX_ATTEMPTS) {
      isPauseOnMaxAttempts = true;
    }
  }

  /**
   * Get number of failed attempts. Its main purpose is for testing
   *
   * @return number of failed attempt
   */
  public int getFailedAttempts() {
    return failedAttempts;
  }
}