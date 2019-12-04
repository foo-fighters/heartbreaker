package brickingbad.controller;

import brickingbad.domain.game.authentication.User;
import brickingbad.services.authentication.UserRepository;

public class AuthenticationController {

  private static AuthenticationController instance;

  private UserRepository userRepository;

  private AuthenticationController() {
    userRepository = UserRepository.getInstance();
  }

  public static AuthenticationController getInstance() {
    if (instance == null) {
      instance = new AuthenticationController();
    }
    return instance;
  }

  public boolean authenticate() {
    return true;
  }

  public void addUser(User user) {
    userRepository.addUser(user);
  }



}
