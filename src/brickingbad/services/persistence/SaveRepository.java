package brickingbad.services.persistence;

import brickingbad.domain.game.persistence.Save;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;


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

  public void adaptMongoDB() {
    this.adapter = new MongoDBPersistenceAdapter();
  }

  public void adaptLocal() {
    this.adapter = new LocalPersistenceAdapter();
  }

  public IPersistenceAdapter getAdapter() {
    return adapter;
  }


}
