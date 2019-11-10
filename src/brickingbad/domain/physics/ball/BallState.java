package brickingbad.domain.physics.ball;

import brickingbad.domain.game.Ball;

public abstract class BallState {

    private final Ball ball;

    public BallState(Ball ball) {
      this.ball = ball;
    }

    abstract void reflect();

}
