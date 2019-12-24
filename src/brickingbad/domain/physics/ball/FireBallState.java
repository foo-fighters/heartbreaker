package brickingbad.domain.physics.ball;

import brickingbad.domain.game.*;
import brickingbad.domain.game.gameobjects.Ball;
import brickingbad.domain.game.gameobjects.alien.Alien;
import brickingbad.domain.game.gameobjects.alien.ProtectingAlien;
import brickingbad.domain.game.gameobjects.brick.Brick;
import brickingbad.domain.game.gameobjects.brick.HalfMetalBrick;
import brickingbad.domain.game.gameobjects.GameObject;
import brickingbad.domain.game.gameobjects.powerup.PowerUp;
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
                    GameLogic.destroyBricksInRadius(object.getPosition(), GameConstants.fireBallDestructionRadius);
                }else if((dir == Direction.LEFT || dir == Direction.RIGHT)
                        && ball.getPosition().getY() < object.getPosition().getY()) {
                    object.destroy();
                    GameLogic.destroyBricksInRadius(object.getPosition(), GameConstants.fireBallDestructionRadius);
                }else if(((HalfMetalBrick) object).isCracked()) {
                    object.destroy();
                    GameLogic.destroyBricksInRadius(object.getPosition(), GameConstants.fireBallDestructionRadius);
                }else {
                    ((HalfMetalBrick) object).setCracked(true);
                }
            }else if(object instanceof ProtectingAlien) {
                Direction dir = ball.getReflectionDirection();
                if(dir == Direction.UP) {
                    object.destroy();
                }
            }else{
                object.destroy();
                GameLogic.destroyBricksInRadius(object.getPosition(), GameConstants.fireBallDestructionRadius);
            }
        }
        ball.reflect(object);
    }
}
