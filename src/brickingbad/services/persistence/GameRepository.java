package brickingbad.services.persistence;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.Game;
import brickingbad.domain.game.Paddle;
import brickingbad.domain.game.brick.SimpleBrick;
import brickingbad.domain.game.persistence.Save;
import brickingbad.domain.game.persistence.SaveAssembler;
import brickingbad.domain.physics.Vector;
import brickingbad.services.DatabaseService;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;

public class GameRepository {

  private static MongoCollection<Save> gamesCollection = DatabaseService.getDatabase().getCollection("games", Save.class);

  public static void saveGame(Save save) {
    gamesCollection.insertOne(save);
  }

  public static void main(String[] args) {
    Save save = new Save();
    int[] temp = {1, 2, 3};
    save.setName("mock save");
    save.ballVelX.add(3);
    save.ballVelX.add(5);
    save.setScore(5);
    BasicDBObject saveObject = new BasicDBObject(save.getName(), save);
    DatabaseService.getDatabase().getCollection("games").insertOne(new Document(saveObject.toMap()));
  }

}
