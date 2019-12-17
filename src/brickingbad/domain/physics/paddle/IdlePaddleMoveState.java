package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.gameobjects.Paddle;

public class IdlePaddleMoveState extends PaddleMoveState {

  public IdlePaddleMoveState(Paddle paddle) {
    this.paddle = paddle;
  }

  @Override
  public void updatePosition() {
  }

}
