package brickingbad.domain.physics.collisions;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.Paddle;
import brickingbad.domain.game.alien.Alien;
import brickingbad.domain.game.border.Ground;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.game.powerup.PowerUp;

import java.util.HashMap;
import java.util.Map;

public class Collision {

    private GameObject o1;
    private GameObject o2;
    private ICollisionStrategy collisionStrategy;
    private boolean collided;

    public Collision(GameObject o1, GameObject o2) {
        this.o1 = o1;
        this.o2 = o2;
        collided = false;
    }

    public void collideObjects() {
        checkDestructionPair(o1, o2, Ball.class, Brick.class);
        if(collided) return;
        checkDestructionPair(o1, o2, Ball.class, Alien.class);
        if(collided) return;
        checkDestructionPair(o1, o2, Ground.class, Ball.class);
        if(collided) return;
        checkDestructionPair(o1, o2, Ground.class, PowerUp.class);
        if(collided) return;
        checkDestructionPair(o1, o2, Paddle.class, PowerUp.class);
        if(collided) return;
        collisionStrategy = CollisionStrategyFactory.getInstance().getCollisionStrategy("reflection");
        collisionStrategy.collide(o1, o2);
    }

    private void checkDestructionPair(GameObject o1, GameObject o2, Class<?> reflected, Class<?> destroyed) {
        if(reflected.isInstance(o1) && destroyed.isInstance(o2)) {
            collisionStrategy = CollisionStrategyFactory.getInstance().getCollisionStrategy("destruction");
            collisionStrategy.collide(o1, o2);
            collided = true;
        }else if(reflected.isInstance(o2) && destroyed.isInstance(o1)) {
            collisionStrategy = CollisionStrategyFactory.getInstance().getCollisionStrategy("destruction");
            collisionStrategy.collide(o2, o1);
            collided = true;
        }
    }
}
