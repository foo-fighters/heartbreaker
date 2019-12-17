package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.gameobjects.Ball;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.gameobjects.Paddle;
import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.Vector;

/**
 * ActivePaddleMoveState is responsible for one of the paddle state since the state pattern is implemented
 */
public class ActivePaddleMoveState extends PaddleMoveState {

  /**
   * The private value for the paddle move speed
   */
  private final double moveSpeed = GameConstants.regularPaddleMovementSpeed;
  /**
   * The private value for the delta distance between each frame
   */
  private final Vector deltaPos;

  public ActivePaddleMoveState(Paddle paddle, Direction direction) {
    this.paddle = paddle;
    this.direction = direction;
    this.deltaPos = new Vector(moveSpeed / GameConstants.calculationsPerSecond, 0.0);
  }

  /**
   * Updates the paddle position in accordance to its required state
   */
  @Override
  public void updatePosition() {
    // REQUIRES: field "direction" is Direction.LEFT or Direction.RIGHT
    // MODIFIES: field "paddle"
    // EFFECTS: updates paddle's position according to its movement direction.
    if(direction == Direction.LEFT) {
      if(paddle.getPosition().getX() <= paddle.getSize().getX() / 2.0) {
        paddle.setPosition(paddle.getSize().getX() / 2.0, paddle.getPosition().getY());
        return;
      }
      paddle.getPosition().addVector(deltaPos.product(-1.0));
      for(Ball ball: paddle.getCurrentBalls()) {
        ball.getPosition().addVector(deltaPos.product(-1.0));
      }
    }else {
      if(paddle.getPosition().getX() >= GameConstants. screenWidth - paddle.getSize().getX() / 2.0) {
        paddle.setPosition(GameConstants. screenWidth - paddle.getSize().getX() / 2.0, paddle.getPosition().getY());
        return;
      }
      paddle.getPosition().addVector(deltaPos);
      for(Ball ball: paddle.getCurrentBalls()) {
        ball.getPosition().addVector(deltaPos);
      }
    }
  }

}
