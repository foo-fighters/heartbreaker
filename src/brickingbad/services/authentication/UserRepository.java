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

  public void addUser(User user) throws IllegalArgumentException {
    adapter.addUser(user);
  }

  public User findUserByName(String name) throws IllegalArgumentException {
    return adapter.findUserByName(name);
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


}
