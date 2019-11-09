package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.Paddle;

public class ActivePaddleMoveState extends PaddleMoveState {

  public ActivePaddleMoveState(Paddle paddle, Direction direction) {
    super(paddle, direction);
  }

  @Override
  void updatePosition() {
  }

}
