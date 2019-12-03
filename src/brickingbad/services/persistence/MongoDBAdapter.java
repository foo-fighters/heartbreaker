package brickingbad.services.persistence;

import brickingbad.domain.game.persistence.Save;
import brickingbad.services.MongoDBService;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoDBAdapter implements IPersistenceAdapter {

  private static MongoCollection<Save> savesCollection = MongoDBService.getDatabase().getCollection("games", Save.class);

  @Override
  public void save(Save save) {
    String name = save.name;
    if (getSaveNames().contains(name)) {
      savesCollection.deleteOne(eq("name", name));
    }
    savesCollection.insertOne(save);
  }

  @Override
  public List<String> getSaveNames() {
    List<String> names = new ArrayList<>();
    MongoCursor<Save> cursor = savesCollection.find().iterator();
    try {
      while (cursor.hasNext()) {
        names.add(cursor.next().name);
      }
    } finally {
      cursor.close();
    }
    return names;
  }

  @Override
    public Save getSaveByName(String name) {
    return savesCollection.find(eq("name", name)).first();
  }


}
