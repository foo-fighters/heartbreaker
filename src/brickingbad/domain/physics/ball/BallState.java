package brickingbad.domain.physics.ball;

public abstract class BallState {
    private BallState ball;
    abstract void reflect();
    abstract void startMovement(int angle);
}
