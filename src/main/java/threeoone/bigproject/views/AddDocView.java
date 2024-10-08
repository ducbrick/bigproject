package threeoone.bigproject.views;

import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.Controller;
import threeoone.bigproject.controller.DocInformation;

import java.util.Scanner;

/**
 * This class represents a view that adds a document.
 * It implements the View interface to standardize the rendering process.
 *
 * @author HUY1902
 */

@Component
public class AddDocView implements View{
  /**
   * Renders the AddDocView which prompts the user to add a document and handles the associated interactions.
   *
   * @param controller a {@code Controller} to give this {@code View} access to other APIs for user interaction.
   */
  @Override
  public void render(Controller controller) {
    System.out.println("Add a document!!!");
    System.out.println("Write document name:");
    Scanner scanner = new Scanner(System.in);

    String name = scanner.nextLine();

    //DocInformation docInformation = new DocInformation(name);

    System.out.println("You have submitted this document:" + name);
    System.out.println("0. Exit");
    System.out.println("1. Another Document");

    int choice = scanner.nextInt();
    switch (choice) {
      case 0:
        System.out.println("Exiting...");
        controller.openMenu();
        break;
      case 1:
        System.out.println("Add a document selected.");
        controller.addDoc();
    }
  }

  /**
   * Stops the rendering process for the AddDocView. This method is invoked when the view
   * needs to reset its internal properties to their original values, free up resources,
   * or pause/stop any of its running threads/tasks.
   */
  @Override
  public void stopRendering() {

  }
}
