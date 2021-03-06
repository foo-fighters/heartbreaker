package brickingbad.domain.physics.ball;

import brickingbad.domain.game.Level;
import brickingbad.domain.game.gameobjects.Ball;
import brickingbad.domain.game.gameobjects.GameObject;
import brickingbad.domain.game.gameobjects.alien.Alien;
import brickingbad.domain.game.gameobjects.alien.CooperativeAlien;
import brickingbad.domain.game.gameobjects.brick.Brick;
import brickingbad.domain.game.gameobjects.powerup.PowerUp;

public class ChemicalBallState extends BallState {

    private Ball ball;

    public ChemicalBallState(Ball ball) {
        this.ball = ball;
    }

    public void collide(GameObject object) {
        if(object instanceof Ball || object instanceof PowerUp) return;
        if(object instanceof Brick || object instanceof Alien) {
            if(object instanceof CooperativeAlien) {
                Level.getInstance().setCooperativeAlienIsKilled(true);
            }
            object.destroy();
            return;
        }
        ball.reflect(object);
    }

}
