package threeoone.bigproject.controller.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.controller.viewcontrollers.LoginController;
import threeoone.bigproject.controller.viewcontrollers.RegisterController;
import threeoone.bigproject.controller.viewcontrollers.ViewController;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.exceptions.AlreadyLoggedInException;
import threeoone.bigproject.exceptions.IllegalCredentialsException;
import threeoone.bigproject.exceptions.UserAlreadyExistException;
import threeoone.bigproject.services.UserRegisterService;
import threeoone.bigproject.view.ViewSwitcher;

/**
 * This class handles all logic for register
 *
 * @author HUY1902
 */
@Component
@RequiredArgsConstructor
public class UserRegisterController {
  private final UserRegisterService userRegisterService;
  private final RegisterController registerController;
  private final RequestSender<ViewController> switchToLogin;
  /**
   * Register receiver for registerRequestSender
   *
   * @param registerRequestSender request sender
   */
  @Autowired
  private void registerRegisterRequestReceiver(RequestSender<User> registerRequestSender) {
    registerRequestSender.registerReceiver(this::validateRegister);
  }

  /**
   * Handle exception from service and manipulate view through view controller
   *
   * @param user User's register information
   */
  public void validateRegister(User user) {
    try {
      registerController.resetAlert();

      userRegisterService.register(user);

      registerController.showSuccessDialog();

      switchToLogin.send(null);
    } catch (UserAlreadyExistException | AlreadyLoggedInException e) {
      registerController.setConfirmMessage(e.getMessage());
    }
  }
}
