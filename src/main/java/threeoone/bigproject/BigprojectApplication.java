package threeoone.bigproject;

import javafx.application.Application;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import threeoone.bigproject.repositories.PasswordResetTokenRepo;
import threeoone.bigproject.services.resetpassword.PasswordResetAuthenticationService;

/**
 * Entry point of the application.
 * Enable Feign Clients for OpenFeign
 *
 * @author DUCBRICK
 */
@SpringBootApplication
@EnableFeignClients
public class BigprojectApplication {
	/**
	 * Launches a Spring Boot {@link JavaFxApplication}.
	 *
	 * @param args arguments
	 */
	public static void main(String[] args) {
		Application.launch(JavaFxApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(PasswordResetTokenRepo repo) {
		return args -> {
			System.out.println(repo.findByValue("2Bc+hI5QxweNvLqsCWIKZg(XkbgCP3TuRJ17(kKLXeAS0Och.tUzNwH)(Lw!+xDJ").getUser().getUsername());
		};
	}
}
