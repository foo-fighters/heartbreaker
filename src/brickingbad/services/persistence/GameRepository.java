package brickingbad.services.persistence;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.Game;
import brickingbad.domain.game.Paddle;
import brickingbad.domain.game.brick.SimpleBrick;
import brickingbad.domain.game.persistence.Save;
import brickingbad.domain.game.persistence.SaveAssembler;
import brickingbad.domain.physics.Vector;
import brickingbad.services.DatabaseService;
import com.mongodb.client.MongoCollection;

import java.util.ArrayList;

public class GameRepository {

  private static MongoCollection<Save> gamesCollection = DatabaseService.getDatabase().getCollection("games", Save.class);

  public static void saveGame(Save save) {
    gamesCollection.insertOne(save);
  }

}
