package brickingbad.domain.physics.ball;

import brickingbad.domain.game.GameObject;

public abstract class BallState {

    public abstract void collide(GameObject object);

}
