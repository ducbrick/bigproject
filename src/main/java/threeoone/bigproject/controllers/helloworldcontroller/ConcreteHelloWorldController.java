package threeoone.bigproject.controllers.helloworldcontroller;

import threeoone.bigproject.controllers.MasterController;
import threeoone.bigproject.views.GetUserInfoView;
import threeoone.bigproject.views.HelloWorldView;
import threeoone.bigproject.views.View;

/**
 * An implementation of <code>HelloWorldController</code> interface.
 *
 * @author DUCBRICK
 */
public class ConcreteHelloWorldController implements HelloWorldController {
  private final HelloWorldView helloWorldView;
  private final GetUserInfoView getUserInfoView;

  /**
   * Contructor that takes a <code>MasterController</code> as parameter which will be
   * supplied to any <code>View</code> that requires it.
   *
   * @param masterController a <code>MasterController</code> to be supplied to any <code>View</code>
   *                         that requires it
   * @see MasterController
   * @see View
   */
  public ConcreteHelloWorldController(MasterController masterController) {
    helloWorldView = new HelloWorldView();
    getUserInfoView = new GetUserInfoView(masterController);
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
