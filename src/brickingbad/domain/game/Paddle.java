package brickingbad.domain.game;

import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.Vector;
import brickingbad.domain.physics.paddle.*;

import java.util.ArrayList;

public class Paddle extends GameObject {

  private ArrayList<Ball> currentBalls;
  private PaddleMoveState moveState;
  private PaddleMoveState rotateState;
  private double angle;
  private double angularVelocity;
  public boolean isMagnetized;

  public Paddle(){
    setIdleMove();
    setIdleRotate();
    position = new Vector(GameConstants.screenWidth / 2, GameConstants.screenHeight - GameConstants.paddleHeight);
    velocity = new Vector();
    size = new Vector(GameConstants.paddleLength, GameConstants.paddleThickness);
    shape = Shape.RECTANGLE;
    angle = 0.0;
    angularVelocity = 0.0;
    isMagnetized = false;
    currentBalls = new ArrayList<>();
  }

  public Vector getPosition() {
    return position;
  }

  public void setPosition(int x, int y) {
    position.setVector(x,y);
  }

  public void launchBalls() {
    for (Ball ball: currentBalls) {
      ball.startMovement(angle);
      currentBalls.remove(ball);
    }
  }

  private void catchBall(Ball ball) {
    currentBalls.add(ball);
    ball.stopMovement();
  }

  public Vector getBallStartPosition() {
    double distance = (GameConstants.paddleThickness + GameConstants.ballSize) / 2.0;
    Vector offset = new Vector((int)Math.round(distance * Math.sin(angle)), -(int)Math.round(distance * Math.cos(angle)));
    offset.addVector(this.position);
    return offset;
  }

  public void updatePosition() {
    moveState.updatePosition();
    rotateState.updatePosition();
  }

  public void startMove(Direction direction) {
    moveState = new ActivePaddleMoveState(this, direction);
  }

  public void endMove(Direction direction){
    moveState = new EndPaddleMoveState(this, direction, position.getX());
  }

  public void setIdleMove(){
    moveState = new IdlePaddleMoveState(this);
  }


  public void startRotate(Direction direction){ }

  public void endRotate(Direction direction) { }

  public void setIdleRotate(){ }

}