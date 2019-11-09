package brickingbad.domain.physics.paddle;

import brickingbad.domain.physics.ball.ChemicalBallState;

public class Paddle {
    private ChemicalBallState currentBalls;
    private PaddleMoveState moveState;
    private PaddleMoveState rotateState;
    private double angle;
    private int angularVelocity;
    private boolean isMagnitized;



    public void launchBalls() {
    }

    private void clearBalls() {
    }

    public void startMove(dir Direction) {
    }

    public void endMove(dir Direction) {
    }
    public void idleMove(){}
    public void startMove(){}
    public void endMove(dir Direction){}
    public void idleRotate(){}

}