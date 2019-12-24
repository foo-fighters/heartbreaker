package brickingbad.domain.game.gameobjects.border;

import brickingbad.domain.game.gameobjects.Ball;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.gameobjects.GameObject;
import brickingbad.domain.game.Shape;
import brickingbad.domain.game.gameobjects.powerup.PowerUp;
import brickingbad.domain.physics.Vector;

public class Ground extends GameObject {

    public Ground() {
        position = new Vector(GameConstants.screenWidth / 2.0, GameConstants.screenHeight + 5.0);
        velocity = new Vector();
        size = new Vector(GameConstants.screenWidth, 10.0);
        shape = Shape.RECTANGLE;
        angle = 0.0;
    }

    @Override
    public void collide(GameObject object) {
        if(object instanceof Ball || object instanceof PowerUp) {
            object.destroy();
        }
    }

    @Override
    public void updatePosition() {
    }

}
