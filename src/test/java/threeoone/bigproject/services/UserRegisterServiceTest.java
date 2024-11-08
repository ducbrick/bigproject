package threeoone.bigproject.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.exceptions.AlreadyLoggedInException;
import threeoone.bigproject.exceptions.IllegalCredentialsException;
import threeoone.bigproject.exceptions.UserAlreadyExistException;
import threeoone.bigproject.repositories.UserRepo;

@ExtendWith(MockitoExtension.class)
class UserRegisterServiceTest {
  @Mock
  private UserRepo userRepo;
  @Mock
  private LoginService loginService;
  @Mock
  private IllegalCharacterFilterService illegalCharacterFilterService;

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
    User user = new User("username", "password");

    when(loginService.getLoggedInUserId()).thenReturn(1);

    assertThatThrownBy(() -> userRegisterService.register(user))
        .isInstanceOf(AlreadyLoggedInException.class);
  }

  @Test
  @DisplayName("Register a user with empty loginName")
  public void emptyLoginName() {
    User user = new User("  ", "password");

    when(loginService.getLoggedInUserId()).thenReturn(null);

    assertThatThrownBy(() -> userRegisterService.register(user))
        .isInstanceOf(IllegalCredentialsException.class);
  }

  @Test
  @DisplayName("Register a user with empty password")
  public void emptyPassword() {
    User user = new User("username", "");

    when(loginService.getLoggedInUserId()).thenReturn(null);

    assertThatThrownBy(() -> userRegisterService.register(user))
        .isInstanceOf(IllegalCredentialsException.class);
  }

  @Test
  @DisplayName("Register with the same username as another user")
  public void registerWithSameUsername() {
    User user = new User("username", "password");

    when(loginService.getLoggedInUserId()).thenReturn(null);
    when(userRepo.findByUsername(user.getUsername())).thenReturn(user);

    assertThatThrownBy(() -> userRegisterService.register(user))
        .isInstanceOf(UserAlreadyExistException.class);
  }

  @Test
  @DisplayName("Register with illegal username")
  public void illegalUsername() {
    User user = new User("username", "password");

    when(loginService.getLoggedInUserId()).thenReturn(null);
    when(userRepo.findByUsername(user.getUsername())).thenReturn(null);
    when(illegalCharacterFilterService.hasIllegalCharacter(user.getUsername())).thenReturn(true);

    assertThatThrownBy(() -> userRegisterService.register(user))
        .isInstanceOf(IllegalCredentialsException.class);
  }

  @Test
  @DisplayName("Register with illegal password")
  public void illegalPassword() {
    User user = new User("username", "password");

    when(loginService.getLoggedInUserId()).thenReturn(null);
    when(userRepo.findByUsername(user.getUsername())).thenReturn(null);
    when(illegalCharacterFilterService.hasIllegalCharacter(user.getUsername())).thenReturn(false);
    when(illegalCharacterFilterService.hasIllegalCharacter(user.getPassword())).thenReturn(true);

    assertThatThrownBy(() -> userRegisterService.register(user))
        .isInstanceOf(IllegalCredentialsException.class);
  }

  @Test
  @DisplayName("Happy path")
  public void happyPath() throws Exception {
    User user = new User("username", "password");

    when(loginService.getLoggedInUserId()).thenReturn(null);
    when(userRepo.findByUsername(user.getUsername())).thenReturn(null);

    userRegisterService.register(user);

    verify(userRepo, times(1)).save(user);
    verify(loginService, times(1)).login(user);
  }
}