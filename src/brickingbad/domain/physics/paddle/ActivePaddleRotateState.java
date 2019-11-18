package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.Paddle;

public class ActivePaddleRotateState extends PaddleRotateState {

  public ActivePaddleRotateState(Paddle paddle, Direction direction) {
    super(paddle, direction);
  }

  @Override
  public void updatePosition() {
  }

}
