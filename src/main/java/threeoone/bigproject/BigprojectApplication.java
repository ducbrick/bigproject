package threeoone.bigproject;

import javafx.application.Application;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@FxmlView("threeoone/bigproject/main-stage.fxml")
public class BigprojectApplication {
	/**
 	* Starts the Spring Boot application.
 	* @param args command line parameters (if exist)
 	*/
	public static void main(String[] args) {
		//SpringApplication.run(BigprojectApplication.class, args);
		Application.launch(javafxApplication.class, args);
	}

	/**
	 * Autowired constructor that acts as entry point of the application.
	 * Requests the first {@code View}.
	 *
	 * @param requestDispatcher a {@code RequestDispatcher}
	 */
	/**public BigprojectApplication(RequestDispatcher requestDispatcher) {
		requestDispatcher.getUserInfo();
	} */
}
