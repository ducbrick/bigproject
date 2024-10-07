package threeoone.bigproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class BigprojectApplication {

	/**
	 * Starts the Spring Boot application.
	 * @param args command line parameters (if exist)
	 */
	public static void main(String[] args) {
		SpringApplication.run(BigprojectApplication.class, args);
	}

	/**
	 * Autowired constructor that acts as entry point of the application.
	 * Requests the first {@code View}.
	 *
	 * @param requestDispatcher a {@code RequestDispatcher}
	 */
	public BigprojectApplication(RequestDispatcher requestDispatcher) {
		requestDispatcher.getUserInfo();
	}
}
