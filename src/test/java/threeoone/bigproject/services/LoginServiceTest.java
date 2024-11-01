package threeoone.bigproject.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.exceptions.AlreadyLoggedInException;
import threeoone.bigproject.repositories.UserRepo;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
  @Mock
  private UserRepo userRepo;

  @InjectMocks
  private LoginService loginService;

  @Test
  @DisplayName("Get non-existent logged in user id")
  public void getNonExistentLoggedInUserId() {
    assertThat(loginService.getLoggedInUserId()).isNull();
  }

  @Test
  @DisplayName("Log in with non-existent credentials")
  public void loginNonExistentCredentials() {
    User user = new User("name", "password", "Fancy Name");

    when(userRepo.findByLoginName(user.getLoginName())).thenReturn(null);

    assertThat(loginService.login(user)).isFalse();
  }

  @Test
  @DisplayName("Log in with incorrect credentials")
  public void loginIncorrectCredentials() {
    User user = new User("name", "password", "Fancy Name");
    User registered = new User("name", "correctPassword", "Fancy Name");

    when(userRepo.findByLoginName(user.getLoginName())).thenReturn(registered);

    assertThat(loginService.login(user)).isFalse();
  }

  @Test
  @DisplayName("Login with correct credentials")
  public void loginCorrectCredentials() {
    User user = new User("name", "password", "Fancy Name");

    int id = 0;

    User registered = new User("name", "password", "Fancy Name");
    registered.setId(id);

    when(userRepo.findByLoginName(user.getLoginName())).thenReturn(registered);

    assertThat(loginService.login(user)).isTrue();
    assertThat(loginService.getLoggedInUserId()).isEqualTo(id);
    assertThatThrownBy(() -> loginService.login(new User("abc", "def", "xyz")))
        .isInstanceOf(AlreadyLoggedInException.class);

    loginService.logout();

    assertThat(loginService.getLoggedInUserId()).isNull();
    loginService.login(new User("abc", "def", "xyz"));
  }
}