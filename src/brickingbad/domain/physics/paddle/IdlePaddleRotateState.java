package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.gameobjects.Paddle;

public class IdlePaddleRotateState extends PaddleRotateState {

  public IdlePaddleRotateState(Paddle paddle) {
    this.paddle = paddle;
  }

  @Override
  public void updatePosition() {
  }

}
