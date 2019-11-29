package brickingbad.domain.game;

import brickingbad.domain.game.powerup.PowerUp;
import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.Vector;
import brickingbad.domain.physics.paddle.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Paddle extends GameObject {

  private ArrayList<Ball> currentBalls;
  private PaddleMoveState moveState;
  private PaddleRotateState rotateState;
  public boolean isMagnetized;
  public boolean isGod;

  public Paddle(){
    setIdleMove();
    setIdleRotate();
    position = new Vector(GameConstants.screenWidth / 2.0, GameConstants.screenHeight - GameConstants.paddleHeightOnScreen);
    velocity = new Vector();
    size = new Vector(GameConstants.paddleLength, GameConstants.paddleThickness);
    shape = Shape.RECTANGLE;
    angle = 0.0;
    isMagnetized = false;
    currentBalls = new ArrayList<>();
  }

  public void setPosition(double x, double y) {
    position.setVector(x,y);
  }

  public List<Ball> getCurrentBalls() {
    return currentBalls;
  }

  public void launchBalls() {
    for (Ball ball: currentBalls) {
      ball.startMovement(angle, GameConstants.ballLaunchSpeed);
    }
    currentBalls.clear();
  }

  private void catchBall(Ball ball) {
    currentBalls.add(ball);
    ball.stopMovement();
    double ballHeightOffset = (GameConstants.paddleThickness + GameConstants.ballSize) / 2.0;
    double distX = ball.getPosition().getX() + ballHeightOffset * Math.sin(Math.toRadians(angle)) - position.getX();
    double distY = ball.getPosition().getY() + ballHeightOffset * Math.cos(Math.toRadians(angle)) - position.getY();
    ball.setPaddleOffset(Math.hypot(distX, distY) * Math.signum(distX));
  }

  public Vector getBallStartPosition() {
    double distance = (GameConstants.paddleThickness + GameConstants.ballSize) / 2.0;
    Vector offset = new Vector(-distance * Math.sin(angle), -distance * Math.cos(angle) - 1.0);
    offset.addVector(this.position);
    return offset;
  }

  @Override
  public void collide(GameObject object) {
    if(object instanceof PowerUp) {
      Game.getInstance().storePowerUp((PowerUp) object);
    }
    if(object instanceof Ball) {
      if((object.getReflectionDirection() == Direction.UP || object.getReflectionDirection() == Direction.UP_LEFT
              || object.getReflectionDirection() == Direction.UP_RIGHT) && isMagnetized) {
        catchBall((Ball) object);
      }
    }
  }

  public void updatePosition() {
    moveState.updatePosition();
    rotateState.updatePosition();
    if (isGod) {
      Game game = Game.getInstance();
      ArrayList<Ball> balls = game.getBalls();
      balls.forEach((ball) -> {
        if (Math.abs(ball.getPosition().getY() - position.getY()) < 50) {
          setPosition(ball.getPosition().getX(), position.getY());
          setAngle(0);
        } else {
          setAngle((getAngle() + 5) % 180);
        }
      });

    }
  }

  public void startMove(Direction direction) {
    moveState = new ActivePaddleMoveState(this, direction);
  }

  public void endMove(Direction direction) {
    if(moveState instanceof ActivePaddleMoveState && moveState.getDirection() != direction){
      return;
    }
    moveState = new EndPaddleMoveState(this, direction, position.getX());
  }

  public void setIdleMove() {
    moveState = new IdlePaddleMoveState(this);
  }


  public void startRotate(Direction direction){
    if((direction == Direction.LEFT && angle < 0.0) || (direction == Direction.RIGHT && angle > 0.0)){
      return;
    }
    rotateState = new ActivePaddleRotateState(this, direction);
  }

  public void endRotate(Direction direction) {
    if((direction == Direction.LEFT && angle < 0.0) || (direction == Direction.RIGHT && angle > 0.0)){
      return;
    }
    rotateState = new EndPaddleRotateState(this, direction);
  }

  public void setIdleRotate() {
    rotateState = new IdlePaddleRotateState(this);
  }

  public void god() {
    isGod = true;
  }

  public boolean isMagnetized() {
    return isMagnetized;
  }

  public void setMagnetized(boolean magnetized) {
    isMagnetized = magnetized;
  }

}