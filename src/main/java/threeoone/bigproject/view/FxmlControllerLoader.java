package threeoone.bigproject.view;

import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.viewcontrollers.GetNameController;
import threeoone.bigproject.controller.viewcontrollers.HelloWorldController;

@Component
public class FxmlControllerLoader {
  public FxmlControllerLoader(FxWeaver fxWeaver) {
    fxWeaver.load(HelloWorldController.class);
    fxWeaver.load(GetNameController.class);
  }
}
