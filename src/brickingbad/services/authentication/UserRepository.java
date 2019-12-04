package brickingbad.services.authentication;

import brickingbad.domain.game.authentication.User;
import brickingbad.services.Adapter;

public class UserRepository {

  IAuthenticationAdapter adapter = new LocalAuthenticationAdapter();

  private static UserRepository instance;

  private UserRepository() {

  }

  public static UserRepository getInstance() {
    if (instance == null) {
      instance = new UserRepository();
    }
    return instance;
  }

  // READ/WRITE METHODS

  public void addUser(User user) {
    try {
      adapter.addUser(user);
    } catch (IllegalArgumentException e) {
      // TODO: handle
    }
  }

  public User findUserByName(String name) {
    try {
      User user = adapter.findUserByName(name);
      return user;
    } catch (IllegalArgumentException e) {
      // TODO: handle
      return null;
    }
  }

  // ADAPTER CONTROLS

  public void adapt(Adapter adapter) {
    if (adapter.equals(Adapter.MONGODB)) {
      adaptMongoDB();
    } else if (adapter.equals(Adapter.LOCAL)) {
      adaptLocal();
    }
  }

  private UserRepository adaptMongoDB() {
    this.adapter = new MongoDBAuthenticationAdapter();
    return this;
  }

  private UserRepository adaptLocal() {
    this.adapter = new LocalAuthenticationAdapter();
    return this;
  }


}
