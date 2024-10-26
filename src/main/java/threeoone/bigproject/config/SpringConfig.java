package threeoone.bigproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Document;

/**
 * {@code Spring} configuration class.
 *
 * @author DUCBRICK
 */
@Configuration
public class SpringConfig {
  /**
   * Register a {@link RequestSender} of type {@code switchSceneRequest} into {@code Spring} context.
   * Send a request to switch to a different scene.
   * The request is sent with the name of the scene to switch to.
   * Request body is {@link SwitchScene} which hold name of scene{@link threeoone.bigproject.controller.SceneName}
   * @return the {@link RequestSender} to be registered
   **/
  @Bean
  public RequestSender<SwitchScene> switchSceneRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Register a {@link RequestSender} of type {@code documentRequest} into {@code Spring} context.
   * Send Document entities {@link Document} from view to another view which need Document.
   * For example, {@link threeoone.bigproject.controller.viewcontrollers.DocumentDetailController}
   * need to know what Document user click on in
   * {@link threeoone.bigproject.controller.viewcontrollers.DocOverviewController} to show them.
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<Document> documentRequestSender() {
    return new RequestSender<>();
  }
}
