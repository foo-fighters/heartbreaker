package brickingbad.domain.physics.collisions;

import brickingbad.domain.game.GameObject;

public interface ICollisionStrategy {
    public boolean areColliding(GameObject o1, GameObject o2);
}
