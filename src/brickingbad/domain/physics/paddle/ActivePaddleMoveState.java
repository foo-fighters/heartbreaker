package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Paddle;
import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.Vector;

public class ActivePaddleMoveState extends PaddleMoveState {

  private final double moveSpeed = GameConstants.regularPaddleMovementSpeed;
  private final Vector deltaPos;

  public ActivePaddleMoveState(Paddle paddle, Direction direction) {
    this.paddle = paddle;
    this.direction = direction;
    this.deltaPos = new Vector(moveSpeed / GameConstants.calculationsPerSecond, 0.0);
  }

  @Override
  public void updatePosition() {
    if(direction == Direction.LEFT) {
      if(paddle.getPosition().getX() <= paddle.getSize().getX() / 2.0) {
        paddle.setIdleMove();
        return;
      }
      paddle.getPosition().addVector(deltaPos.product(-1.0));
      for(Ball ball: paddle.getCurrentBalls()) {
        ball.getPosition().addVector(deltaPos.product(-1.0));
      }
    }else{
      if(paddle.getPosition().getX() >= GameConstants. screenWidth - paddle.getSize().getX() / 2.0) {
        paddle.setIdleMove();
        return;
      }
      paddle.getPosition().addVector(deltaPos);
      for(Ball ball: paddle.getCurrentBalls()) {
        ball.getPosition().addVector(deltaPos);
      }
    }
  }

}
