package threeoone.bigproject;

import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.Controller;
import threeoone.bigproject.controller.UserInformation;
import threeoone.bigproject.views.GetUserInfoView;
import threeoone.bigproject.views.HelloWorldView;
import threeoone.bigproject.views.View;

/**
 * A component that accepts API calls and resolves any generated {@code View}.
 * This class is a singleton bean in Spring container.
 *
 * @see View
 *
 * @author DUCBRICK
 */
@Component
public class RequestDispatcher implements ViewResolver, Controller {
  private final HelloWorldView helloWorldView;
  private final GetUserInfoView getUserInfoView;

  private View currentView;

  /**
   * Autowired constructor that takes other necessary {@code View} as parameter.
   * This constructor is invoked by Spring container.
   *
   * @param helloWorldView a {@code HelloWorldView}
   * @param getUserInfoView a {@code GetUserInfoView}
   */
  public RequestDispatcher(HelloWorldView helloWorldView, GetUserInfoView getUserInfoView) {
    this.helloWorldView = helloWorldView;
    this.getUserInfoView = getUserInfoView;
  }

  /**
   * Replaces the current {@code View} with a new incoming {@code View}.
   * If the new {@code View} is different from the current {@code View},
   * the current {@code View} is required to stop rendering before the new
   * {@code View} renders.
   *
   * @param view incoming {@code View} to be resolved
   */
  @Override
  public void resolveView(View view) {
    if (view == null) {
      return;
    }

    if (view != currentView && currentView != null) {
      currentView.stopRendering();
    }

    currentView = view;
    view.render(this);
  }

  @Override
  public void helloWorld(UserInformation userInformation) {
    helloWorldView.setUserInformation(userInformation);
    resolveView(helloWorldView);
  }

  @Override
  public void getUserInfo() {
    resolveView(getUserInfoView);
  }
}
