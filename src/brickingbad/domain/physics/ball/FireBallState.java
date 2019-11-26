package brickingbad.domain.physics.ball;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.GameObject;

public class FireBallState extends BallState {

    private Ball ball;

    public FireBallState(Ball ball) {
        this.ball = ball;
    }

    public void collide(GameObject object) {
    }
}
