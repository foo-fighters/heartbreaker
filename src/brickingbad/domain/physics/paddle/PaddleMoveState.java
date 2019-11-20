package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.Paddle;
import brickingbad.domain.physics.Direction;

public abstract class PaddleMoveState {

  protected Paddle paddle;
  protected Direction direction;

  public abstract void updatePosition();

  public Direction getDirection() {
    return direction;
  }
}
