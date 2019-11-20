package brickingbad.controller;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.persistence.Save;
import brickingbad.domain.game.persistence.SaveAssembler;
import brickingbad.domain.physics.Direction;
import brickingbad.services.persistence.SaveRepository;

import java.util.ArrayList;
import java.util.List;

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
    SaveRepository.addSave(save);
  }

  public void loadGame(String name) {
    Game game = Game.getInstance();
    Save save = SaveRepository.getSaveByName(name);
    SaveAssembler.disassemble(save, game);
  }

  public List<String> getSaveNames() {
    return SaveRepository.getSaveNames();
  }

  public void initializeGame() {
    Game.getInstance().initialize();
  }

  public void startGame() {
    Game.getInstance().play();
  }

  public void launchBalls() {
    Game.getInstance().getPaddle().launchBalls();
  }

  public void startPaddleMove(Direction direction) {
    Game.getInstance().getPaddle().startMove(direction);
  }

  public void endPaddleMove(Direction direction) {
    Game.getInstance().getPaddle().endMove(direction);
  }

  public void startPaddleRotate(Direction direction) {
    Game.getInstance().getPaddle().startRotate(direction);
  }

  public void endPaddleRotate(Direction direction) {
    Game.getInstance().getPaddle().endRotate(direction);
  }

}
