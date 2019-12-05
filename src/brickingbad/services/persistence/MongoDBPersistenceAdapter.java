package brickingbad.services.persistence;

import brickingbad.domain.game.authentication.User;
import brickingbad.domain.game.persistence.Save;
import brickingbad.services.MongoDBService;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoDBPersistenceAdapter implements IPersistenceAdapter {

  private static final MongoCollection<Save> savesCollection = MongoDBService.getDatabase().getCollection("games", Save.class);

  @Override
  public void save(Save save, User user) {
    String name = save.name;
    if (getSaveNames(user).contains(name)) {
      savesCollection.deleteOne(eq("name", name));
    }
    savesCollection.insertOne(save);
  }

  @Override
  public List<String> getSaveNames(User user) {
    List<String> names = new ArrayList<>();
    try (MongoCursor<Save> cursor = savesCollection.find().iterator()) {
      while (cursor.hasNext()) {
        Save save = cursor.next();
        if (save.username.equals(user.name)) {
          names.add(save.name);
        }
      }
    }
    return names;
  }

  @Override
  public Save getSaveByName(String name, User user) {
    return savesCollection.find(Filters.and(
                                  eq("name", name),
                                  eq("username", user.name)))
                          .first();
  }

}
