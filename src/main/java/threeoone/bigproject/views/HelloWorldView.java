package threeoone.bigproject.views;

import org.springframework.stereotype.Component;
import threeoone.bigproject.controllers.MasterController;

/**
 * A <code>View</code> that greets the user.
 * For demonstration purposes only.
 * This class is a singleton bean in Spring container.
 *
 * @author DUCBRICK
 */
@Component
public class HelloWorldView implements View {
  private String username;

  /**
   * Set the username for the <code>HelloWorldView</code> to greet when calling <code>render()</code>.
   *
   * @param username name of the user for the <code>HelloWorldView</code> to greet
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Say hello to user, whose name was set using <code>setUsername()</code>.
   *
   * @param controller a {@code MasterController} to give this {@code View} access to other APIs
   */
  @Override
  public void render(MasterController controller) {
    System.out.println("Hello " + username);
  }

  @Override
  public void stopRendering() {
  }
}
