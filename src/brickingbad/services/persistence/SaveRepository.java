package brickingbad.services.persistence;

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

  public Save getSaveByName(String name) {
    return adapter.getSaveByName(name);
  }

  public void save(Save save) {
    adapter.save(save);
  }

  public List<String> getSaveNames() {
    return adapter.getSaveNames();
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
