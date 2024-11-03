package threeoone.bigproject.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.exceptions.AlreadyLoggedInException;
import threeoone.bigproject.exceptions.UserAlreadyExistException;
import threeoone.bigproject.repositories.UserRepo;

@ExtendWith(MockitoExtension.class)
class UserRegisterServiceTest {
  @Mock
  private UserRepo userRepo;
  @Mock
  private LoginService loginService;

  @InjectMocks
  private UserRegisterService userRegisterService;

  @Test
  @DisplayName("Register a NULL user")
  public void registerNull() {
    assertThatThrownBy(() -> userRegisterService.register(null)).isInstanceOf(
        IllegalArgumentException.class);
  }

  @Test
  @DisplayName("Register a user while another user is currently logged in")
  public void registerWhileLoggedIn() {
    User user = new User("username", "password", "Name");

    when(loginService.getLoggedInUserId()).thenReturn(1);

    assertThatThrownBy(() -> userRegisterService.register(user))
        .isInstanceOf(AlreadyLoggedInException.class);
  }

  @Test
  @DisplayName("Register with the same username as another user")
  public void registerWithSameUsername() {
    User user = new User("username", "password", "Name");

    when(loginService.getLoggedInUserId()).thenReturn(null);
    when(userRepo.findByLoginName(user.getLoginName())).thenReturn(user);

    assertThatThrownBy(() -> userRegisterService.register(user))
        .isInstanceOf(UserAlreadyExistException.class);
  }

  @Test
  @DisplayName("Happy path")
  public void happyPath() throws Exception {
    User user = new User("username", "password", "Name");

    when(loginService.getLoggedInUserId()).thenReturn(null);
    when(userRepo.findByLoginName(user.getLoginName())).thenReturn(null);

    userRegisterService.register(user);
  }
}