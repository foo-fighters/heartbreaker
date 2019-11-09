package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.Paddle;

public class IdlePaddleMoveState extends PaddleMoveState {

  public IdlePaddleMoveState(Paddle paddle, Direction direction) {
    super(paddle, direction);
  }

  @Override
  void updatePosition() {

  }

}
