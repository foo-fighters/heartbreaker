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
  private final Vector deltaPos;

  public EndPaddleMoveState(Paddle paddle, Direction direction, double startX) {
    this.paddle = paddle;
    this.direction = direction;
    this.startX = startX;
    this.deltaPos = new Vector(moveSpeed / GameConstants.calculationsPerSecond, 0.0);
  }

  @Override
  public void updatePosition() {
    if (Math.abs(paddle.getPosition().getX() - startX) > stopDistance) {
      paddle.setIdleMove();
    }else if(direction == Direction.LEFT) {
      if(paddle.getPosition().getX() <= paddle.getSize().getX() / 2.0) {
        paddle.setPosition(paddle.getSize().getX() / 2.0, paddle.getPosition().getY());
        paddle.setIdleMove();
        return;
      }
      paddle.getPosition().addVector(deltaPos.product(-1.0));
      for(Ball ball: paddle.getCurrentBalls()){
        ball.getPosition().addVector(deltaPos.product(-1.0));
      }
    }else{
      if(paddle.getPosition().getX() >= GameConstants.screenWidth - paddle.getSize().getX() / 2.0) {
        paddle.setPosition(GameConstants. screenWidth - paddle.getSize().getX() / 2.0, paddle.getPosition().getY());
        paddle.setIdleMove();
        return;
      }
      paddle.getPosition().addVector(deltaPos);
      for(Ball ball: paddle.getCurrentBalls()){
        ball.getPosition().addVector(deltaPos);
      }
    }
  }

}
