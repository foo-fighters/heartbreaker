package brickingbad.domain.game.brick;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Shape;
import brickingbad.domain.physics.Vector;

public class SimpleBrick extends Brick {

    public SimpleBrick() {
        this.shape = Shape.RECTANGLE;
        this.size = new Vector(GameConstants.rectangularBrickLength, GameConstants.rectangularBrickThickness);
        this.velocity = new Vector(0,0);
    }

    public SimpleBrick(Vector position) {
        this.position = position;
    }


}
