package brickingbad.services.persistence;

import brickingbad.domain.game.persistence.Save;
import brickingbad.services.DatabaseService;
import com.mongodb.client.MongoCollection;


public class GameRepository {

  private static MongoCollection<Save> gamesCollection = DatabaseService.getDatabase().getCollection("games", Save.class);

  public static void saveGame(Save save) {
    gamesCollection.insertOne(save);
  }

}
