package brickingbad.services.persistence;

import brickingbad.domain.game.authentication.User;
import brickingbad.domain.game.persistence.Save;
import brickingbad.services.Adapter;

import java.util.List;

public class SaveRepository {

  // set default adapter to use in case methods are called before setting an adapter explicitly
  IPersistenceAdapter adapter = new LocalPersistenceAdapter();

  private static SaveRepository instance;

  private SaveRepository() {

  }

  public static SaveRepository getInstance() {
    if (instance == null) {
      instance = new SaveRepository();
    }
    return instance;
  }

  // READ/WRITE METHODS

  public Save getSaveByName(String name, User user) {
    return adapter.getSaveByName(name, user);
  }

  public void save(Save save, User user) {
    adapter.save(save, user);
  }

  public List<String> getSaveNames(User user) {
    return adapter.getSaveNames(user);
  }

  // ADAPTER CONTROLS

  public IPersistenceAdapter getAdapter() {
    return adapter;
  }

  public void adapt(Adapter adapter) {
    if (adapter.equals(Adapter.MONGODB)) {
      adaptMongoDB();
    } else if (adapter.equals(Adapter.LOCAL)) {
      adaptLocal();
    }
  }

  private SaveRepository adaptMongoDB() {
    this.adapter = new MongoDBPersistenceAdapter();
    return this;
  }

  private SaveRepository adaptLocal() {
    this.adapter = new LocalPersistenceAdapter();
    return this;
  }




}
