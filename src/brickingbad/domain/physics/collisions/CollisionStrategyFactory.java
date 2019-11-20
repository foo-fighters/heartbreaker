package brickingbad.domain.physics.collisions;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.Paddle;
import brickingbad.domain.game.alien.Alien;
import brickingbad.domain.game.border.Ground;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.game.powerup.PowerUp;
import brickingbad.domain.physics.PhysicsEngine;

public class CollisionStrategyFactory {

  private CollisionStrategyFactory instance;

  private CollisionStrategyFactory() {

  }

  public CollisionStrategyFactory getInstance() {
    if (instance == null) {
      instance = new CollisionStrategyFactory();
    }
    return instance;
  }

  public ICollisionStrategy getCollisionStrategy(GameObject o1, GameObject o2) {
    if(checkClassPair(o1, o2, Ball.class, Brick.class) ||
            checkClassPair(o1, o2, Ball.class, Alien.class) ||
            checkClassPair(o1, o2, PowerUp.class, Paddle.class) ||
            checkClassPair(o1, o2, Ball.class, Ground.class) ||
            checkClassPair(o1, o2, PowerUp.class, Ground.class)) {
      return new DestructionStrategy();
    }else {
      return new ReflectionStrategy();
    }
  }

  private boolean checkClassPair(GameObject o1, GameObject o2, Class<?> c1, Class<?> c2) {
    return (c1.isInstance(o1) || c1.isInstance(o2)) && (c2.isInstance(o1) || c2.isInstance(o2));
  }

}
