package threeoone.bigproject.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.SceneName;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.controller.viewcontrollers.RegisterController;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.exceptions.AlreadyLoggedInException;
import threeoone.bigproject.exceptions.IllegalCredentialsException;
import threeoone.bigproject.exceptions.UserAlreadyExistException;
import threeoone.bigproject.services.UserRegisterService;

/**
 * This class handles all logic for register
 *
 * @author HUY1902
 */
@Component
public class UserRegisterController {
  private final UserRegisterService userRegisterService;
  private final RegisterController registerController;
  private final RequestSender<SwitchScene> switchSceneRequestSender;

  /**
   * Constructor for logic resolver class for Register Page
   *
   * @param userRegisterService : interacting with register service
   * @param registerController : interacting with register view controller
   * @param switchSceneRequestSender : switch scene request sender
   */
  public UserRegisterController(UserRegisterService userRegisterService,
                                RegisterController registerController,
                                RequestSender<SwitchScene> switchSceneRequestSender) {
    this.userRegisterService = userRegisterService;
    this.registerController = registerController;
    this.switchSceneRequestSender = switchSceneRequestSender;
  }

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

      switchSceneRequestSender.send(new SwitchScene(SceneName.LOGIN));
    } catch (UserAlreadyExistException | IllegalCredentialsException | AlreadyLoggedInException e) {
      registerController.setConfirmMessage(e.getMessage());
    }
  }
}
