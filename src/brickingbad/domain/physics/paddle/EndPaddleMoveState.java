package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Paddle;
import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.Vector;

public class EndPaddleMoveState extends PaddleMoveState{

  private final double startX;
  private final double moveSpeed = GameConstants.slowPaddleMovementSpeed;
  private final double stopDistance = GameConstants.paddleStopDistance;

  public EndPaddleMoveState(Paddle paddle, Direction direction, double startX) {
    this.paddle = paddle;
    this.direction = direction;
    this.startX = startX;
  }

  @Override
  public void updatePosition() {
    if (Math.abs(paddle.getPosition().getX() - startX) > stopDistance) {
      paddle.setIdleMove();
    }else if(direction == Direction.LEFT) {
      paddle.getPosition().addVector(new Vector(-moveSpeed / GameConstants.calculationsPerSecond, 0.0));
      for(Ball ball: paddle.getCurrentBalls()){
        ball.getPosition().addVector(new Vector(-moveSpeed / GameConstants.calculationsPerSecond, 0.0));
      }
    }else{
      paddle.getPosition().addVector(new Vector(moveSpeed / GameConstants.calculationsPerSecond, 0.0));
      for(Ball ball: paddle.getCurrentBalls()){
        ball.getPosition().addVector(new Vector(moveSpeed / GameConstants.calculationsPerSecond, 0.0));
      }
    }
  }

}
