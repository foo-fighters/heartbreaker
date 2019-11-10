package brickingbad.domain.physics.ball;

public abstract class BallState {

    private final Ball ball;

    public BallState(Ball ball) {
      this.ball = ball;
    }

    abstract void reflect();

}
