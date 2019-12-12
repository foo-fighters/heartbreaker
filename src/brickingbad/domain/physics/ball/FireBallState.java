package brickingbad.domain.physics.ball;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.alien.Alien;
import brickingbad.domain.game.alien.ProtectingAlien;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.game.brick.HalfMetalBrick;
import brickingbad.domain.game.powerup.PowerUp;
import brickingbad.domain.physics.Direction;

public class FireBallState extends BallState {

    private Ball ball;

    public FireBallState(Ball ball) {
        this.ball = ball;
    }

    public void collide(GameObject object) {
        if(object instanceof Ball || object instanceof PowerUp) return;
        if(object instanceof Brick || object instanceof Alien) {
            if(object instanceof HalfMetalBrick) {
                Direction dir = ball.getReflectionDirection();
                if(dir == Direction.UP_LEFT || dir == Direction.UP || dir == Direction.UP_RIGHT) {
                    object.destroy();
                    Game.getInstance().destroyBricksInRadius(object.getPosition(), GameConstants.fireBallDestructionRadius);
                }else if((dir == Direction.LEFT || dir == Direction.RIGHT)
                        && ball.getPosition().getY() < object.getPosition().getY()) {
                    object.destroy();
                    Game.getInstance().destroyBricksInRadius(object.getPosition(), GameConstants.fireBallDestructionRadius);
                }else if(((HalfMetalBrick) object).isCracked()) {
                    object.destroy();
                    Game.getInstance().destroyBricksInRadius(object.getPosition(), GameConstants.fireBallDestructionRadius);
                }else {
                    ((HalfMetalBrick) object).setCracked(true);
                }
            }else if(object instanceof ProtectingAlien) {
                Direction dir = ball.getReflectionDirection();
                if(dir == Direction.UP_LEFT || dir == Direction.UP || dir == Direction.UP_RIGHT) {
                    object.destroy();
                }
            }else{
                object.destroy();
                Game.getInstance().destroyBricksInRadius(object.getPosition(), GameConstants.fireBallDestructionRadius);
            }
        }
        ball.reflect(object);
    }
}
