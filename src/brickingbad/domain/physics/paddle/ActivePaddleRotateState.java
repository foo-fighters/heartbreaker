package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.gameobjects.Ball;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.gameobjects.Paddle;
import brickingbad.domain.physics.Direction;

public class ActivePaddleRotateState extends PaddleRotateState {

  private final double rotateSpeed = GameConstants.regularPaddleRotationSpeed;
  private final double deltaAng = rotateSpeed / GameConstants.calculationsPerSecond;
  private final double defaultY = GameConstants.screenHeight - GameConstants.paddleHeightOnScreen;
  private final double ballHeightOffset = (GameConstants.paddleThickness + GameConstants.ballSize) / 2.0;

  public ActivePaddleRotateState(Paddle paddle, Direction direction) {
    this.paddle = paddle;
    this.direction = direction;
  }

  @Override
  public void updatePosition() {
    updateRotation();
    setNewPositions();
  }

  private void updateRotation() {
    double currentAngle = paddle.getAngle();
    if(direction == Direction.LEFT) {
      if(currentAngle >= GameConstants.paddleRotationLimit) {
        paddle.setAngle(GameConstants.paddleRotationLimit);
        return;
      }
      paddle.setAngle(currentAngle + deltaAng);
    }else{
      if(currentAngle <= -GameConstants.paddleRotationLimit) {
        paddle.setAngle(-GameConstants.paddleRotationLimit);
        return;
      }
      paddle.setAngle(currentAngle - deltaAng);
    }
  }

  private void setNewPositions() {
    double newAngle = Math.toRadians(paddle.getAngle());
    paddle.setPosition(paddle.getPosition().getX(), defaultY - Math.sin(Math.abs(newAngle)) * paddle.getSize().getX() / 2.0);
    for(Ball ball: paddle.getCurrentBalls()) {
      double ballX = paddle.getPosition().getX() + Math.cos(newAngle) * ball.getPaddleOffset() - Math.sin(newAngle) * ballHeightOffset;
      double ballY = paddle.getPosition().getY() - Math.sin(newAngle) * ball.getPaddleOffset() - Math.cos(newAngle) * ballHeightOffset - 1.0;
      ball.getPosition().setVector(ballX, ballY);
    }
  }

}
