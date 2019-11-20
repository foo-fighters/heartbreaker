package brickingbad.domain.game.border;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.Shape;
import brickingbad.domain.physics.Vector;

public class Ground extends GameObject {

    public Ground() {
        position = new Vector(GameConstants.screenWidth / 2.0, GameConstants.screenHeight + 5.0);
        velocity = new Vector();
        size = new Vector(GameConstants.screenWidth, 10.0);
        shape = Shape.RECTANGLE;
    }

    @Override
    public void updatePosition() {
    }

}
