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
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<SwitchScene> switchSceneRequestSender() {
    return new RequestSender<SwitchScene>();
  }

  /**
   * Register a {@link RequestSender} of type {@code documentRequest} into {@code Spring} context.
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<Document> documentRequestSender() {
    return new RequestSender<>();
  }
}
