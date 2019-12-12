package brickingbad.controller;

import brickingbad.domain.game.authentication.User;
import brickingbad.services.Adapter;
import brickingbad.services.authentication.UserRepository;

/**
 * AuthenticationController is responsible of handling requests related to authentication.
 *
 */
public class AuthenticationController {

  /**
   * Maintains the single instance of {@link AuthenticationController} since it is implemented as a singleton.
   */
  private static AuthenticationController instance;

  /**
   * The {@link UserRepository} that deals with the adapters on a lower level.
   */
  private UserRepository userRepository;

  /**
   * A reference to the current {@link User} logged in to the system.
   */
  private User currentUser;

  private AuthenticationController() {
    userRepository = UserRepository.getInstance();
  }

  /**
   * Straightforward implementation of a singleton method to obtain the instance.
   * @return the single instance of the class
   */
  public static AuthenticationController getInstance() {
    if (instance == null) {
      instance = new AuthenticationController();
    }
    return instance;
  }

  /**
   * tests the given username/password combination against the database.
   * @param name username entered by the user that wants to get authenticated
   * @param password password entered by the user that wants to get authenticated
   * @return true if authentication succeeds, false otherwise.
   */
  public boolean authenticate(String name, String password) {
    try {
      User user = userRepository.findUserByName(name);
      if (user.password.equals(password)) {
        System.out.println("Successful login: " + user.name);
        currentUser = user;
        return true;
      } else {
        System.out.println("Wrong password for: " + user.name);
        return false;
      }
    } catch (IllegalArgumentException e) {
      System.out.println("User doesn't exist: " + name);
      // TODO: handle (show UI message - failed login)
      return false;
    }
  }

  /**
   * commands the {@link UserRepository} to save the given {@link User} to the database.
   * @param user the user to be saved
   */
  public boolean addUser(User user) {
    try {
      System.out.println("Trying to create new account: " + user.name);
      return userRepository.addUser(user);
    } catch (IllegalArgumentException e) {
      System.out.println("User with name exists: " + user.name);
      return false;
    }
  }

  public void deleteUser(User user) {
    userRepository.deleteUser(user);
  }

  /**
   * adapts the {@link UserRepository} to the given adapter
   * @param adapter the enum value representing an adapter
   * @see Adapter
   * @see brickingbad.services.authentication.IAuthenticationAdapter
   * @return itself so that methods can be chained.
   */
  public AuthenticationController adapt(Adapter adapter) {
    userRepository.adapt(adapter);
    return this;
  }

  /**
   *
   * @return the user that is currently logged in
   */
  public User getCurrentUser() {
    return currentUser;
  }

}
