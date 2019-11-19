package brickingbad.controller;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.persistence.Save;
import brickingbad.domain.game.persistence.SaveAssembler;
import brickingbad.domain.physics.paddle.Direction;
import brickingbad.services.persistence.GameRepository;

public class GameController {

  private static GameController instance;

  private GameController() { };

  public static GameController getInstance() {
    if (instance == null) {
      instance = new GameController();
    }
    return instance;
  }

  public void saveGame(String name) {
    Game game = Game.getInstance();
    Save save = SaveAssembler.assemble(game, name);
    GameRepository.saveGame(save);
  }

  public void startGame() { }


  public void launchBalls() { }


  public void startPaddleMove(Direction direction) { }

  public void endPaddleMove(Direction direction) { }

  public void startPaddleRotate(Direction direction) { }

  public void endPaddleRotate(Direction direction) { }

}
