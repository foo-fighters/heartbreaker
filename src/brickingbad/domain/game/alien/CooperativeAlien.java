package brickingbad.domain.game.alien;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Shape;
import brickingbad.domain.physics.Vector;

public class CooperativeAlien extends Alien {

    public CooperativeAlien() {
        this.position= new Vector();
        this.shape= Shape.RECTANGLE;
        this.velocity=new Vector();
        this.angle = 0.0;
        this.size = new Vector(GameConstants.alienSize, GameConstants.alienSize);
    }

    @Override
    void performAction() {

    }
}
