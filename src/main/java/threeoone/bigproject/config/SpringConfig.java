package threeoone.bigproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.UserInfo;

/**
 * {@code Spring} configuration class.
 *
 * @author DUCBRICK
 */
@Configuration
public class SpringConfig {

  /**
   * Register a {@link RequestSender} into {@code Spring} context.
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender <UserInfo> helloWorldRequestSender() {
    return new RequestSender <UserInfo> ();
  }
}
