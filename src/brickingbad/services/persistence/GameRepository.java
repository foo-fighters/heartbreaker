package brickingbad.services.persistence;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.Paddle;
import brickingbad.domain.game.persistence.GameDTO;
import brickingbad.services.DatabaseService;
import com.mongodb.client.MongoCollection;

import java.util.ArrayList;

public class GameRepository {

  private static MongoCollection<GameDTO> gamesCollection = DatabaseService.getDatabase().getCollection("games", GameDTO.class);

  public static void saveGame(GameDTO gameDTO) {
    gamesCollection.insertOne(gameDTO);
  }

  public static void main(String[] args) {
    Game mockGame = Game.getInstance();
    mockGame.score = 10;
    mockGame.lives = 5;
    mockGame.balls = new ArrayList<>();
    mockGame.bricks = new ArrayList<>();
    mockGame.paddle = Paddle.getI;
  }

}
