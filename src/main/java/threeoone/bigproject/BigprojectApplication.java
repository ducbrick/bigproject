package threeoone.bigproject;

import javafx.application.Application;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.UserInfo;
import threeoone.bigproject.controller.viewcontrollers.HelloWorldController;
import threeoone.bigproject.controller.viewcontrollers.ViewController;

@SpringBootApplication
public class BigprojectApplication {
	public static void main(String[] args) {
		Application.launch(JavaFxApplication.class, args);
	}
}
