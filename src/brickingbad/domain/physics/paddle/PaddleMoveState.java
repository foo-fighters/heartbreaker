package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.Paddle;

public abstract class PaddleMoveState {

  private final Paddle paddle;
  private final Direction direction;

  public PaddleMoveState(Paddle paddle, Direction direction) {
    this.paddle = paddle;
    this.direction = direction;
  }

  public abstract void updatePosition();

}
