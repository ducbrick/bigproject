package threeoone.bigproject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import threeoone.bigproject.controller.viewcontrollers.GetNameController;
import threeoone.bigproject.controller.viewcontrollers.ViewController;
import threeoone.bigproject.view.ViewSwitcher;

public class JavaFxApplication extends Application {
  private ConfigurableApplicationContext context;

  @Override
  public void init() throws Exception {
    this.context = new SpringApplicationBuilder()
        .sources(BigprojectApplication.class)
        .run(getParameters().getRaw().toArray(new String[0]));
  }

  @Override
  public void start(Stage stage) throws Exception {
    ViewSwitcher viewSwitcher = context.getBean(ViewSwitcher.class);
    ViewController startController = context.getBean(GetNameController.class);
    viewSwitcher.setStage(stage, startController);
  }

  @Override
  public void stop() throws Exception {
    this.context.close();
    Platform.exit();
  }
}
