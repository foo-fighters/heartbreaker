package brickingbad.domain.physics.ball;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.alien.Alien;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.game.powerup.PowerUp;

public class ChemicalBallState extends BallState {

    private Ball ball;

    public ChemicalBallState(Ball ball) {
        this.ball = ball;
    }

    public void collide(GameObject object) {
        if(object instanceof Ball || object instanceof PowerUp) return;
        if(object instanceof Brick || object instanceof Alien) {
            object.destroy();
            return;
        }
        ball.reflect(object);
    }

}
