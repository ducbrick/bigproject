package threeoone.bigproject;

import org.springframework.stereotype.Component;
import threeoone.bigproject.controllers.MasterController;
import threeoone.bigproject.controllers.helloworldcontroller.ConcreteHelloWorldController;
import threeoone.bigproject.controllers.helloworldcontroller.HelloWorldController;
import threeoone.bigproject.controllers.helloworldcontroller.UserInformation;
import threeoone.bigproject.views.HelloWorldView;
import threeoone.bigproject.views.View;

/**
 * A component that accepts API calls and resolves any returned <code>View</code>.
 * A <code>RequestDispatcher</code> implements <code>ViewResolver</code>
 * and <code>MasterController</code>.
 * <code>RequestDispatcher</code> delegates any API call to other Controller.
 * Thus, it acts as a proxy for every other Controller in the system.
 * The Views returned by API calls are handle by <code>resolveView</code> method.
 * This class is a singleton bean in Spring container.
 *
 * @author DUCBRICK
 * @see View
 */
@Component
public class RequestDispatcher implements ViewResolver, MasterController {
  private View view;
  private final HelloWorldController helloWorldController;

  /**
   * Autowired constructor that takes multiple other necessary Controllers as parameters.
   * This constructor will be invoked by Spring container.
   *
   * @param helloWorldController a {@code HelloWorldController}
   */
  public RequestDispatcher(HelloWorldController helloWorldController) {
    this.helloWorldController = helloWorldController;
  }

  /**
   * Replaces the current <code>View</code> with a new incoming <code>View</code>.
   * If the new <code>View</code> is different from the current <code>View</code>,
   * the current <code>View</code> is required to stop rendering before the new
   * <code>View</code> renders.
   *
   * @param view incoming <code>View</code> to be resolved
   */
  @Override
  public void resolveView(View view) {
    if (view == null) {
      return;
    }

    if (view != this.view && this.view != null) {
      this.view.stopRendering();
    }

    this.view = view;
    view.render(this);
  }

  /**
   * Request to greet user which takes an <code>UserInformation</code> as parameter.
   * This methods delegates to a <code>HelloWorldController</code>.
   * The returned <code>View</code> is resolved by <code>resolveView()</code>.
   *
   * @param userInformation the user's information
   * @return a <code>View</code> that will greet the user
   *
   * @see UserInformation
   * @see View
   */
  @Override
  public View helloWorld(UserInformation userInformation) {
    View view = helloWorldController.helloWorld(userInformation);
    resolveView(view);
    return view;
  }

  /**
   * Request to get user's information.
   * This methods delegates to a <code>HelloWorldController</code>.
   * The returned <code>View</code> is resolved by <code>resolveView()</code>.
   *
   * @return a <code>View</code> that will obtain user's information
   *
   * @see View
   */
  @Override
  public View getUserInfo() {
    View view = helloWorldController.getUserInfo();
    resolveView(view);
    return view;
  }
}
