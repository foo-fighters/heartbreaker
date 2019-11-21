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

  private static CollisionStrategyFactory instance;

  private CollisionStrategyFactory() {

  }

  public static CollisionStrategyFactory getInstance() {
    if (instance == null) {
      instance = new CollisionStrategyFactory();
    }
    return instance;
  }

  public ICollisionStrategy getCollisionStrategy(String collisionType) {
    if(collisionType.equals("reflection")) {
      return new ReflectionStrategy();
    }else if(collisionType.equals("destruction")) {
      return new DestructionStrategy();
    }else {
      return null;
    }
  }



}
