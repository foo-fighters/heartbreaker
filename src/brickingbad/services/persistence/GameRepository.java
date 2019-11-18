package brickingbad.services.persistence;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.persistence.GameDTO;
import brickingbad.services.DatabaseService;
import com.mongodb.client.MongoCollection;

public class GameRepository {

  private static MongoCollection<GameDTO> gamesCollection = DatabaseService.getDatabase().getCollection("games", GameDTO.class);

  public static void saveGame(GameDTO gameDTO) {
    gamesCollection.insertOne(gameDTO);
  }

}
