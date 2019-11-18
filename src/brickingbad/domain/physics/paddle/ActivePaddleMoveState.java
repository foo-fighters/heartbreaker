package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Paddle;
import brickingbad.domain.physics.Vector;

public class ActivePaddleMoveState extends PaddleMoveState {

  private final int moveSpeed = GameConstants.regularPaddleMovementSpeed;

  public ActivePaddleMoveState(Paddle paddle, Direction direction) {
    this.paddle = paddle;
    this.direction = direction;
  }

  @Override
  public void updatePosition() {
    paddle.getPosition().addVector(new Vector(moveSpeed, 0));
  }

}
