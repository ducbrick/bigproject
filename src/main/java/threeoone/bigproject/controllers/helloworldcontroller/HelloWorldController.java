package threeoone.bigproject.controllers.helloworldcontroller;

import threeoone.bigproject.views.View;

/**
 * A Controller for demonstration.
 *
 * @author DUCBRICK
 */
public interface HelloWorldController {

  /**
   * Requests to greet user which takes an <code>UserInformation</code> as parameter
   * and returns a <code>View</code> to greet the user.
   *
   * @param userInformation the user's information
   * @return a <code>View</code> that will greet the user
   *
   * @see UserInformation
   * @see View
   */
  public View helloWorld(UserInformation userInformation);

  /**
   * Requests to obtain user's information.
   *
   * @return a <code>View</code> that will obtain the user's information
   * @see View
   */
  public View getUserInfo();
}
