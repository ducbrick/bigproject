package threeoone.bigproject;

import javafx.application.Application;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.viewcontrollers.ViewController;

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
