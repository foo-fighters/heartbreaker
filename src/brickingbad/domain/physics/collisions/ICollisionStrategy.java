package brickingbad.domain.physics.collisions;

import brickingbad.domain.game.GameObject;

public interface ICollisionStrategy {

  void collide(GameObject o1, GameObject o2);

}
