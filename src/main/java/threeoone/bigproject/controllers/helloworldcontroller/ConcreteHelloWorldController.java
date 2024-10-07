package threeoone.bigproject.controllers.helloworldcontroller;

import org.springframework.stereotype.Component;
import threeoone.bigproject.controllers.MasterController;
import threeoone.bigproject.views.GetUserInfoView;
import threeoone.bigproject.views.HelloWorldView;
import threeoone.bigproject.views.View;

/**
 * An implementation of <code>HelloWorldController</code> interface.
 * This class is a singleton bean is Spring container.
 *
 * @author DUCBRICK
 */
@Component
public class ConcreteHelloWorldController implements HelloWorldController {
  private final HelloWorldView helloWorldView;
  private final GetUserInfoView getUserInfoView;

  /**
   * Autowired constructor that takes multiple necessary {@code View} as parameters.
   * This constructor will be invoked by Spring container.
   *
   * @param helloWorldView a {@code HelloWorldView}
   * @param getUserInfoView a {@code GetUserInfoView}
   */
  public ConcreteHelloWorldController(HelloWorldView helloWorldView,
      GetUserInfoView getUserInfoView) {
    this.helloWorldView = helloWorldView;
    this.getUserInfoView = getUserInfoView;
  }

  @Override
  public View helloWorld(UserInformation userInformation) {
    helloWorldView.setUsername(userInformation.name());
    return helloWorldView;
  }

  @Override
  public View getUserInfo() {
    return getUserInfoView;
  }
}
