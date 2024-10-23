package threeoone.bigproject.view;

import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.viewcontrollers.*;

/**
 * A component responsible for loading the application's FXML files into their {@link ViewController}.
 * This class is a singleton bean in {@code Spring} context.
 *
 * @see ViewController
 * @see FxWeaver
 *
 * @author DUCBRICK
 */
@Component
public class FxmlControllerLoader {
  /**
   * Loads FXML files into {@link ViewController} using {@link FxWeaver}.
   *
   * @param fxWeaver autowired {@link FxWeaver} bean to load FXML files
   */
  public FxmlControllerLoader(FxWeaver fxWeaver) {
    fxWeaver.load(HelloWorldController.class);
    fxWeaver.load(GetNameController.class);
    fxWeaver.load(MenuController.class);
    fxWeaver.load(YourBooksController.class);
    fxWeaver.load(DocumentDetailController.class);
    fxWeaver.load(DocOverviewController.class);
  }
}
