package threeoone.bigproject.views;

import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.Controller;

import java.util.Scanner;

/**
 * MenuView is an implementation of the View interface that presents a menu to the user
 * and processes their input. It provides options to add a document or exit the application.
 *
 * @author HUY1902
 */
@Component
public class MenuView implements View {
  /**
   * @param controller a {@code Controller} to give this {@code View} access to other APIs
   */
  @Override
  public void render(Controller controller) {
    System.out.println("0. Exit");
    System.out.println("1. Add a document");
    System.out.println("2. Remove a document");
    System.out.println("3. Borrow a document");
    Scanner scanner = new Scanner(System.in);
    int choice = scanner.nextInt();
    switch (choice) {
      case 0:
        System.out.println("Exiting...");
        break;
      case 1:
        System.out.println("Add a document selected.");
        controller.addDoc();
        break;
      case 2:
        System.out.println("Remove a document selected.");
        controller.removeDoc();
        break;
      case 3:
        System.out.println("Remove a document selected.");
        controller.borrowDoc();
        break;
      default:
        System.out.println("Invalid choice. Please try again.");
        controller.openMenu();
        break;
    }
  }

  /**
   *
   */
  @Override
  public void stopRendering() {

  }
}
