package brickingbad.domain.physics.collisions;

import brickingbad.domain.game.GameObject;

public class DestructionStrategy implements ICollisionStrategy {

  public void collide(GameObject o1, GameObject o2) {
    o1.reflect(o2);
    o2.destroy();
  }

}
