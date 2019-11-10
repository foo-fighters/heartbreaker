package brickingbad.domain.game;

import brickingbad.domain.physics.paddle.Direction;
import brickingbad.domain.physics.paddle.PaddleMoveState;

public class Paddle extends GameObject {

  private Ball[] currentBalls;
  private PaddleMoveState moveState;
  private PaddleMoveState rotateState;
  private double angle;
  private int angularVelocity;
  private boolean isMagnetized;

  public void launchBalls() { }


  public void updatePosition() { }

  public void reflect(GameObject object) { }

  public void destroy() { }


  public void startMove(Direction direction) { }

  public void endMove(Direction direction){ }

  public void setIdleMove(){ }


  public void startRotate(Direction direction){ }

  public void endRotate(Direction direction) { }

  public void setIdleRotate(){ }


  private void clearBalls() { }

}