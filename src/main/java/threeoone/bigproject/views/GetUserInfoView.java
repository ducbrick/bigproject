package threeoone.bigproject.views;

import java.util.Scanner;
import threeoone.bigproject.controllers.MasterController;
import threeoone.bigproject.controllers.helloworldcontroller.UserInformation;
import threeoone.bigproject.controllers.helloworldcontroller.HelloWorldController;

/**
 * A <code>View</code> to obtain user's information and then call
 * <code>HelloWorldController.helloWorld()</code> API to greet user.
 * For demonstration purposes only.
 *
 * @see HelloWorldController
 * @author DUCBRICK
 */
public class GetUserInfoView implements View {
  private final MasterController controller;

  public GetUserInfoView(MasterController controller) {
    this.controller = controller;
  }

  /**
   * Wait for user to enter their name, then call <code>HelloWorldController.helloWorld()</code> API.
   *
   * @see HelloWorldView
   */
  @Override
  public void render() {
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
