package threeoone.bigproject.views;

import java.util.Scanner;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.Controller;
import threeoone.bigproject.controller.UserInformation;

/**
 * A {@code View} to obtain user's information and then requests
 * {@code Controller.helloWorld()} to greet user.
 * For demonstration purposes only.
 * This class is a singleton bean in Spring container.
 *
 * @see Controller
 *
 * @author DUCBRICK
 */
@Component
public class GetUserInfoView implements View {
  /**
   * Wait for user to enter their name, then requests {@code Controller.helloWorld()}.
   *
   * @param controller a {@code Controller} to give this {@code View} access to {@code Controller.helloWorld()}
   *
   * @see Controller
   */
  @Override
  public void render(Controller controller) {
    System.out.print("Enter you name: ");
    Scanner scanner = new Scanner(System.in);

    String username = scanner.nextLine();

    UserInformation userInfo = new UserInformation(username);

    controller.helloWorld(userInfo);
  }

  @Override
  public void stopRendering() {

  }

}
