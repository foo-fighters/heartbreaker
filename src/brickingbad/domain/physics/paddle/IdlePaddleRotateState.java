package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.Paddle;

public class IdlePaddleRotateState extends PaddleRotateState {

  public IdlePaddleRotateState(Paddle paddle, Direction direction) {
    super(paddle, direction);
  }

  @Override
  void updatePosition() {

  }

}
