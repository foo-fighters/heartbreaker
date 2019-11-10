package brickingbad.controller;

import brickingbad.domain.physics.paddle.Direction;

public class GameController {

  private GameController instance;

  public GameController getInstance() {
    if (instance == null) {
      instance = new GameController();
    }
    return instance;
  }

  public void startGame() { }


  public void launchBalls() { }


  public void startPaddleMove(Direction direction) { }

  public void endPaddleMove(Direction direction) { }

  public void startPaddleRotate(Direction direction) { }

  public void endPaddleRotate(Direction direction) { }

}
