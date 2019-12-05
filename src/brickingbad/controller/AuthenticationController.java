package brickingbad.controller;

import brickingbad.domain.game.authentication.User;
import brickingbad.services.Adapter;
import brickingbad.services.authentication.UserRepository;

public class AuthenticationController {

  private static AuthenticationController instance;

  private UserRepository userRepository;

  private User currentUser;

  private AuthenticationController() {
    userRepository = UserRepository.getInstance();
  }

  public static AuthenticationController getInstance() {
    if (instance == null) {
      instance = new AuthenticationController();
    }
    return instance;
  }

  public boolean authenticate(String name, String password) {
    try {
      User user = userRepository.findUserByName(name);
      if (user.password.equals(password)) {
        currentUser = user;
        return true;
      } else {
        return false;
      }
    } catch (IllegalArgumentException e) {
      // TODO: handle (show UI message - failed login)
      return false;
    }
  }

  public void addUser(User user) {
    try {
      userRepository.addUser(user);
    } catch (IllegalArgumentException e) {
      // TODO: handle (show UI message)
    }
  }

  public AuthenticationController adapt(Adapter adapter) {
    userRepository.adapt(adapter);
    return this;
  }

  public User getCurrentUser() {
    return currentUser;
  }

}
