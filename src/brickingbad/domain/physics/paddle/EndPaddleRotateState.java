package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.Paddle;
import brickingbad.domain.physics.Direction;

public class EndPaddleRotateState extends PaddleRotateState {

  public EndPaddleRotateState(Paddle paddle, Direction direction) {
    super(paddle, direction);
  }

  @Override
  public void updatePosition() {
  }

}
