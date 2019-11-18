package brickingbad.services.persistence;

import brickingbad.domain.game.Game;
import brickingbad.services.DatabaseService;
import com.mongodb.client.MongoCollection;

public class GameRepository {

  private static MongoCollection<Game> gamesCollection = DatabaseService.getDatabase().getCollection("games", Game.class);

  public void saveGame(Game game) {
    gamesCollection.insertOne(game);
  }

}
