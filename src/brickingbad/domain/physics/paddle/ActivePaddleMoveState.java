package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Paddle;
import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.Vector;

public class ActivePaddleMoveState extends PaddleMoveState {

  private final double moveSpeed = GameConstants.regularPaddleMovementSpeed;

  public ActivePaddleMoveState(Paddle paddle, Direction direction) {
    this.paddle = paddle;
    this.direction = direction;
  }

  @Override
  public void updatePosition() {
    if(direction == Direction.LEFT) {
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
