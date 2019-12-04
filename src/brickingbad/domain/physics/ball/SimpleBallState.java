package brickingbad.domain.physics.ball;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.Shape;
import brickingbad.domain.game.alien.Alien;
import brickingbad.domain.game.alien.ProtectingAlien;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.game.brick.HalfMetalBrick;
import brickingbad.domain.game.powerup.PowerUp;
import brickingbad.domain.physics.Direction;

public class SimpleBallState extends BallState {

    private Ball ball;

    public SimpleBallState(Ball ball){
        this.ball = ball;
    }

    public void collide(GameObject object) {
        if(object instanceof Ball || object instanceof PowerUp) return;
        if(object instanceof Brick || object instanceof Alien) {
            if(object instanceof HalfMetalBrick) {
                Direction dir = ball.getReflectionDirection();
                if(dir == Direction.UP_LEFT || dir == Direction.UP || dir == Direction.UP_RIGHT) {
                    object.destroy(true);
                }else if((dir == Direction.LEFT || dir == Direction.RIGHT)
                        && ball.getPosition().getY() < object.getPosition().getY()) {
                    object.destroy(true);
                }
            }else if(object instanceof ProtectingAlien) {
                Direction dir = ball.getReflectionDirection();
                if(dir == Direction.UP_LEFT || dir == Direction.UP || dir == Direction.UP_RIGHT) {
                    object.destroy();
                }
            }else{
                object.destroy(true);
            }
        }
        ball.reflect(object);
    }

}
