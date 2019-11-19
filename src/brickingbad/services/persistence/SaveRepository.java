package brickingbad.services.persistence;

import brickingbad.domain.game.persistence.Save;
import brickingbad.services.DatabaseService;
import com.mongodb.client.MongoCollection;


public class SaveRepository {

  private static MongoCollection<Save> gamesCollection = DatabaseService.getDatabase().getCollection("games", Save.class);

  public static void saveGame(Save save) {
    gamesCollection.insertOne(save);
  }

  public static Save loadGame(String name) {
    // TODO: query the db for the save with the given name
    return null;
  }

}
