package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.Paddle;

public abstract class PaddleMoveState {

  protected Paddle paddle;
  protected Direction direction;

  public abstract void updatePosition();

}
