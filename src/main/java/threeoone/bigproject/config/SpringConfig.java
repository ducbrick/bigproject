package threeoone.bigproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.entities.User;

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
   *
   * @return the {@link RequestSender} to be registered
   **/
  @Bean
  public RequestSender<SwitchScene> switchSceneRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Register a {@link RequestSender} of type {@code documentRequest} into {@code Spring} context.
   * Send Document entities {@link Document} from view to another view which need Document.
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<Document> documentRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Register a {@link RequestSender} of type {@code loginRequest} into {@code Spring} context.
   * Send a LoginRequest which holds {@link User} from 'LoginPage' to service.
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<User> loginRequestSender() {
    return new RequestSender<>();
  }
}
