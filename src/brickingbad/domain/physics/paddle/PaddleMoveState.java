package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.Paddle;

public abstract class PaddleMoveState {

  private final Paddle paddle;
  private final Direction direction;
  private final int moveSpeed;

  public PaddleMoveState(Paddle paddle, Direction direction) {
    this.paddle = paddle;
    this.direction = direction;
    this.moveSpeed = 10; // TODO: get moveSpeed from some source
  }

  abstract void updatePosition();

}
