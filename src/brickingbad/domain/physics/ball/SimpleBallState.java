package brickingbad.domain.physics.ball;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.alien.AlienStrategy;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.game.brick.HalfMetalBrick;
import brickingbad.domain.physics.Direction;

public class SimpleBallState extends BallState {

    private Ball ball;

    public SimpleBallState(Ball ball){
        this.ball = ball;
    }

    public void collide(GameObject object) {
        if(object instanceof Brick || object instanceof AlienStrategy) {
            if(object instanceof HalfMetalBrick) {
                Direction dir = ball.getReflectionDirection();
                if(dir == Direction.UP_LEFT || dir == Direction.UP || dir == Direction.UP_RIGHT) {
                    object.destroy();
                }else if((dir == Direction.LEFT || dir == Direction.RIGHT)
                        && ball.getPosition().getY() < object.getPosition().getY()) {
                    object.destroy();
                }
            }else{
                object.destroy();
            }
        }
        ball.reflect(object);


    }

}
