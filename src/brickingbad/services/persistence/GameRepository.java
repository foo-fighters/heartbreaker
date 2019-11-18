package brickingbad.services.persistence;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.Game;
import brickingbad.domain.game.Paddle;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.game.powerup.PowerUp;
import brickingbad.services.DatabaseService;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;


public class GameRepository {

  private static MongoCollection<Game> gamesCollection = DatabaseService.getDatabase().getCollection("games", Game.class);

  public static void saveGame(GameDTO gameDTO) {
    gamesCollection.insertOne(gameDTO);
  }

}
