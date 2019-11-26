package brickingbad.domain.game.brick;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.Shape;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.physics.Vector;

import java.util.ArrayList;

public class MineBrick extends Brick {

    public MineBrick() {
        this.position = new Vector();
        this.shape = Shape.CIRCLE;
        this.size = new Vector(GameConstants.circularBrickSize, GameConstants.circularBrickSize);
        this.velocity = new Vector();
        this.angle = 0.0;
    }

    @Override
    public void destroy() {
        super.destroy();
        ArrayList<GameObject> objects = new ArrayList<>(Game.getInstance().getObjects());
        double xdist;
        double ydist;
        for (GameObject object: objects) {
            if(object instanceof Brick) {
                xdist = position.getX() - object.getPosition().getX();
                ydist = position.getY() - object.getPosition().getY();
                if(Math.hypot(xdist, ydist) < GameConstants.mineBrickExplosionRadius) {
                    object.destroy();
                }
            }
        }
    }
}
