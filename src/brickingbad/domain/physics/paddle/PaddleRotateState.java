package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.Paddle;

public abstract class PaddleRotateState {

  private final Paddle paddle;
  private final Direction direction;
  private final int rotateSpeed;

  public PaddleRotateState(Paddle paddle, Direction direction) {
    this.paddle = paddle;
    this.direction = direction;
    this.rotateSpeed = 10; // TODO: get rotateSpeed from some source.
  }

  abstract void updatePosition();

}
