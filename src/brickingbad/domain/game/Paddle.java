package brickingbad.domain.game;

import brickingbad.domain.physics.paddle.*;

public class Paddle extends GameObject {

  private Ball[] currentBalls;
  private PaddleMoveState moveState;
  private PaddleMoveState rotateState;
  private double angle;
  private double angularVelocity;
  public boolean isMagnetized;

  private Paddle(){
    setIdleMove();
    setIdleRotate();
    angle = 0.0;
    angularVelocity = 0.0;
    isMagnetized = false;
  }

  public void launchBalls() {
    for (Ball ball: currentBalls) {
      ball.startMovement(angle);
    }
  }

  public void updatePosition() {
    moveState.updatePosition();
    rotateState.updatePosition();
  }

  public void reflect(GameObject object) { }

  public void startMove(Direction direction) {
    moveState = new ActivePaddleMoveState(this, direction);
  }

  public void endMove(Direction direction){
    moveState = new EndPaddleMoveState(this, direction, xpos, ypos);
  }

  public void setIdleMove(){
    moveState = new IdlePaddleMoveState(this);
  }


  public void startRotate(Direction direction){ }

  public void endRotate(Direction direction) { }

  public void setIdleRotate(){ }


  private void clearBalls() { }

}