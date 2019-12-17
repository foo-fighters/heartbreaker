package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.gameobjects.Paddle;
import brickingbad.domain.physics.Direction;

public abstract class PaddleRotateState {

  protected Paddle paddle;
  protected Direction direction;

  public abstract void updatePosition();

}
