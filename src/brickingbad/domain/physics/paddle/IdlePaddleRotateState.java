package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.Paddle;
import brickingbad.domain.physics.Direction;

public class IdlePaddleRotateState extends PaddleRotateState {

  public IdlePaddleRotateState(Paddle paddle) {
    this.paddle = paddle;
  }

  @Override
  public void updatePosition() {
  }

}
