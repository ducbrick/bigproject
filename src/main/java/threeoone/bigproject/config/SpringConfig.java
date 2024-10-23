package threeoone.bigproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.SwitchScene;
import threeoone.bigproject.controller.requestbodies.UserInfo;
import threeoone.bigproject.entities.Document;

/**
 * {@code Spring} configuration class.
 *
 * @author DUCBRICK
 */
@Configuration
public class SpringConfig {

  /**
   * Register a {@link RequestSender} of type {@code helloWorldRequest} into {@code Spring} context.
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender <UserInfo> helloWorldRequestSender() {
    return new RequestSender <UserInfo> ();
  }

  @Bean
  public RequestSender <String> menuRequestSender() { return new RequestSender<String>(); }

  @Bean
  public RequestSender <SwitchScene> switchSceneRequestSender() { return new RequestSender<SwitchScene>();}

  @Bean
  public RequestSender <Document> documentRequestSender() { return new RequestSender<>(); }

}
