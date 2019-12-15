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

  public boolean addUser(User user) throws IllegalArgumentException {
    return adapter.addUser(user);
  }

  public User findUserByName(String name) throws IllegalArgumentException {
    return adapter.findUserByName(name);
  }

  public void deleteUser(User user) {
    adapter.deleteUser(user);
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
    instance.adapter = new MongoDBAuthenticationAdapter();
    return this;
  }

  private UserRepository adaptLocal() {
    instance.adapter = new LocalAuthenticationAdapter();
    return this;
  }

  public IAuthenticationAdapter getAdapter() {
    return adapter;
  }

}
