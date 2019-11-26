package brickingbad.domain.physics.ball;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.alien.Alien;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.game.brick.HalfMetalBrick;
import brickingbad.domain.physics.Direction;

public class ChemicalBallState extends BallState {

    private Ball ball;

    public ChemicalBallState(Ball ball) {
        this.ball = ball;
    }

    public void collide(GameObject object) {
        if(object instanceof Brick || object instanceof Alien) {
            object.destroy();
        }else {
            ball.reflect(object);
        }
    }

}
