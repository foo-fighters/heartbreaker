package brickingbad.services.authentication;

import brickingbad.domain.game.authentication.User;

import java.util.ArrayList;

public interface IAuthenticationAdapter {

  public void addUser(User user) throws IllegalArgumentException;
  public User findUserByName(String name) throws IllegalArgumentException;
  public ArrayList<String> getNames();

}
