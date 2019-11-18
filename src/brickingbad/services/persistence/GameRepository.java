package brickingbad.services.persistence;

import brickingbad.domain.game.Game;
import brickingbad.services.DatabaseService;
import com.mongodb.client.MongoCollection;
import org.bson.Document;


public class GameRepository {

  private static MongoCollection<Game> gamesCollection = DatabaseService.getDatabase().getCollection("games", Game.class);

  public static void saveGame(Game game) {
    gamesCollection.insertOne(game);
  }

  /**
   * Serializes a Game object into a MongoDB document.
   */
  public static Document serializeGame(Game game) {
    Document document = new Document();

  }

}
