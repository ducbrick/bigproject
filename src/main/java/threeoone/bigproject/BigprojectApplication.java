package threeoone.bigproject;

import javafx.application.Application;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.viewcontrollers.ViewController;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.repositories.DocumentRepo;
import threeoone.bigproject.repositories.LendingDetailRepo;
import threeoone.bigproject.repositories.MemberRepo;
import threeoone.bigproject.repositories.UserRepo;
import threeoone.bigproject.services.DocumentPersistenceService;
import threeoone.bigproject.services.LendingPersistenceService;
import threeoone.bigproject.services.MemberEditingService;

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
