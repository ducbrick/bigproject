package threeoone.bigproject.views;

import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.Controller;

import java.util.Scanner;


/**
 * A {@code View} implementation that handles the removal of documents.
 * It prompts the user to specify the document to be removed and provides options
 * to remove additional documents.
 *
 * @author HUY1902
 */
@Component
public class RemoveDocView implements View{
  /**
   * @param controller a {@code Controller} to give this {@code View} access to other APIs
   */
  @Override
  public void render(Controller controller) {
    System.out.println("Write the document you want to remove: ");
    Scanner scanner = new Scanner(System.in);

    String docName = scanner.nextLine();

    System.out.println("You have removed " + docName);
    System.out.println("Do you want remove another document?");
    System.out.println("0. No");
    System.out.println("1. Yes");

    int choice = scanner.nextInt();

    switch (choice) {
      case 0:
        System.out.println("Exiting...");
        break;
      case 1:
        System.out.println("Add a document selected.");
        controller.removeDoc();
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
