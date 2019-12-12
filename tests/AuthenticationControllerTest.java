import brickingbad.controller.AuthenticationController;
import brickingbad.domain.game.authentication.User;
import brickingbad.services.Adapter;
import brickingbad.services.authentication.UserRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthenticationControllerTest {

  private AuthenticationController authenticationController;
  private final UserRepository userRepository = UserRepository.getInstance();
  private User mockUser;

  @Before
  public void setUp() {
    authenticationController = AuthenticationController.getInstance().adapt(Adapter.LOCAL);
    mockUser = new User("test", "test");
    userRepository.deleteUser(mockUser);
  }

  @Test
  public void getInstanceReturnsInstance() {
    assertNotNull(authenticationController);
  }

  @Test
  public void addsUser() {
    assertTrue(authenticationController.addUser(mockUser));
  }

  @Test
  public void doesntAddIfUserWithNameExists() {
    authenticationController.addUser(mockUser);
    assertFalse(authenticationController.addUser(mockUser));
  }

  @Test
  public void authenticatesUser() {
    authenticationController.addUser(mockUser);
    assertTrue(authenticationController.authenticate(mockUser.name, mockUser.password));
  }

  @Test
  public void doesntAuthenticateOnWrongPassword() {
    assertFalse(authenticationController.authenticate(mockUser.name, "ldsjfh"));
  }

  @Test
  public void doesntAuthenticationWrongUsername() {
    // what the password is doesn't matter
    assertFalse(authenticationController.authenticate("dfjghsdfjlg", mockUser.password));
  }

  @Test
  public void setsCurrentUser() {
    authenticationController.addUser(mockUser);
    authenticationController.authenticate(mockUser.name, mockUser.password);
    assertEquals(mockUser, authenticationController.getCurrentUser());
  }

}
