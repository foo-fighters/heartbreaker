package brickingbad.domain.physics.ball;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.GameObject;

public abstract class BallState {

    abstract void reflect(GameObject object);

}
