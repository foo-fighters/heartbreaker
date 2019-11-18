package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.Paddle;

public class IdlePaddleMoveState extends PaddleMoveState {

  public IdlePaddleMoveState(Paddle paddle) {
    super(paddle, Direction.STOP);
  }

  @Override
  public void updatePosition() {

  }

}
