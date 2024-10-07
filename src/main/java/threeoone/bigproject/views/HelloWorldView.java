package threeoone.bigproject.views;

import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.Controller;
import threeoone.bigproject.controller.UserInformation;

/**
 * A {@code View} that greets the user.
 * For demonstration purposes only.
 * This class is a singleton bean in Spring container.
 *
 * @author DUCBRICK
 */
@Component
public class HelloWorldView implements View {
  private UserInformation userInformation;

  /**
   * Set the user's information prior to calling {@code render()}
   *
   * @param userInformation information of the user
   */
  public void setUserInformation(UserInformation userInformation) {
    this.userInformation = userInformation;
  }

  /**
   * Say hello to user, whose information was set using {@code setUserInformation()}.
   *
   * @param controller a {@code Controller} to give this {@code View} access to other APIs
   */
  @Override
  public void render(Controller controller) {
    System.out.println("Hello " + userInformation.name());
  }

  @Override
  public void stopRendering() {
  }
}
