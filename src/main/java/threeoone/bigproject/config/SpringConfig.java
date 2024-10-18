package threeoone.bigproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.UserInfo;

@Configuration
public class SpringConfig {
  @Bean
  public RequestSender <UserInfo> helloWorldRequestSender() {
    return new RequestSender <UserInfo> ();
  }
}
