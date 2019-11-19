package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Paddle;
import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.Vector;

public class EndPaddleMoveState extends PaddleMoveState{

  private final int startX;
  private final int moveSpeed = GameConstants.slowPaddleMovementSpeed;
  private final int stopDistance = GameConstants.paddleStopDistance;

  public EndPaddleMoveState(Paddle paddle, Direction direction, int startX) {
    this.paddle = paddle;
    this.direction = direction;
    this.startX = startX;
  }

  @Override
  public void updatePosition() {
    if (Math.abs(paddle.getPosition().getX() - startX) > stopDistance) {
      paddle.setIdleMove();
    }else{
      paddle.getPosition().addVector(new Vector(moveSpeed, 0));
    }
  }

}
