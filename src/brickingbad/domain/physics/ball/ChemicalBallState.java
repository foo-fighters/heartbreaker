package brickingbad.domain.physics.ball;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.GameObject;

public class ChemicalBallState extends BallState {

    private Ball ball;

    public ChemicalBallState(Ball ball) {
        this.ball = ball;
    }

    public void reflect(GameObject object) {

    }

}
