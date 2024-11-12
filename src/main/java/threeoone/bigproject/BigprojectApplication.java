package threeoone.bigproject;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the application.
 *
 * @author DUCBRICK
 */
@SpringBootApplication
public class BigprojectApplication {
	/**
	 * Launches a Spring Boot {@link JavaFxApplication}.
	 *
	 * @param args arguments
	 */
	public static void main(String[] args) {
		Application.launch(JavaFxApplication.class, args);
	}
}
