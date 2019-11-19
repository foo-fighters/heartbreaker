package brickingbad.services.persistence;

import brickingbad.domain.game.persistence.Save;
import brickingbad.services.DatabaseService;
import com.mongodb.client.MongoCollection;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;


public class SaveRepository {

  private static MongoCollection<Save> savesCollection = DatabaseService.getDatabase().getCollection("games", Save.class);

  public static void addSave(Save save) {
    savesCollection.insertOne(save);
  }

  public static List<String> getSaveNames() {
    return null;
  }

  public static Save getSaveByName(String name) {
    return savesCollection.find(eq("name", name)).first();
  }

  public static void deleteSaveByName(String name) {

  }

}
