package threeoone.bigproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import threeoone.bigproject.controllers.helloworldcontroller.UserInformation;

@SpringBootApplication
public class BigprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigprojectApplication.class, args);

		RequestDispatcher requestDispatcher = new RequestDispatcher();
		requestDispatcher.getUserInfo();
	}

}
