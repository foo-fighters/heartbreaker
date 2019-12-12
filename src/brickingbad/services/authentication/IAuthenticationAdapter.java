package brickingbad.services.authentication;

import brickingbad.domain.game.authentication.User;

import java.util.ArrayList;

public interface IAuthenticationAdapter {

  void addUser(User user) throws IllegalArgumentException;
  void deleteUser(User user);
  User findUserByName(String name) throws IllegalArgumentException;
  ArrayList<String> getNames();

}
