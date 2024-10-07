package threeoone.bigproject.views;

import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controllers.MasterController;
import threeoone.bigproject.controllers.helloworldcontroller.HelloWorldController;
import threeoone.bigproject.controllers.helloworldcontroller.UserInformation;

/**
 * A <code>View</code> to obtain user's information and then call
 * <code>HelloWorldController.helloWorld()</code> API to greet user.
 * For demonstration purposes only.
 * This class is a singleton bean in Spring container.
 *
 * @see HelloWorldController
 * @author DUCBRICK
 */
@Component
public class GetUserInfoView implements View {
  /**
   * Wait for user to enter their name, then requests <code>HelloWorldController.helloWorld()</code> API.
   *
   * @param controller a {@code MasterController} to give this {@code View} access to {@code HelloWorldController.helloWorld()}
   *
   * @see HelloWorldView
   */
  @Override
  public void render(MasterController controller) {
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
