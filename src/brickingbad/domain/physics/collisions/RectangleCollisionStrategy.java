package brickingbad.domain.physics.collisions;

import brickingbad.domain.game.gameobjects.GameObject;

public class RectangleCollisionStrategy implements ICollisionStrategy {
    @Override
    public boolean areColliding(GameObject o1, GameObject o2) {
        double o1_posx = o1.getPosition().getX();
        double o1_posy = o1.getPosition().getY();
        double o2_posx = o2.getPosition().getX();
        double o2_posy = o2.getPosition().getY();
        double distx = (o1.getSize().getX() + o2.getSize().getX()) / 2.0;
        double disty = (o1.getSize().getY() + o2.getSize().getY()) / 2.0;
        return Math.abs(o1_posx - o2_posx) < distx && Math.abs(o1_posy - o2_posy) < disty;
    }
}
