package brickingbad.domain.physics.collisions;

import brickingbad.domain.game.gameobjects.GameObject;

public interface ICollisionStrategy {
    boolean areColliding(GameObject o1, GameObject o2);
}
