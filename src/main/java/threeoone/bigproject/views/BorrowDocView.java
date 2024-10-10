package threeoone.bigproject.views;

import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * View for borrowing new document(s).
 * Shows a list of documents available for borrowing, along with documents already being borrowed.
 * Todo: Reads user's information on already borrowing books.
 * Todo: Handles borrowing multiple books
 * Reads and confirms user's selection
 */
@Component
public class BorrowDocView implements View {
    /**
     * @param controller a {@code Controller} to grant access to other APIs
     */
    private List<String> booklist = new ArrayList<>(); //demonstration
    @Override
    public void render(Controller controller) {
        Scanner s = new Scanner(System.in);
        booklist.add("Metamorphosis");
        booklist.add("Inferno");
        booklist.add("The Stranger");
        System.out.println("Available books:");
        int i = 0;
        for(String book : booklist) {
            System.out.println(i + ". " + book);
        }
        System.out.println("Choose book to borrow or press 0 to return to menu");

        int n = s.nextInt();
        if (n == 0) controller.openMenu();
        else if (n > booklist.size() || n < 0) System.out.println("...Are you regarded?");
        else {
            System.out.println("Book borrowed :" + booklist.get(n));
        }

    }

    @Override
    public void stopRendering() {

    }
}
