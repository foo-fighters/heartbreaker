package brickingbad.domain.game.alien;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Shape;
import brickingbad.domain.game.alien.Alien;
import brickingbad.domain.physics.Vector;

public class RepairingAlien extends Alien {

    public RepairingAlien() {
        this.position= new Vector();
        this.shape= Shape.RECTANGLE;
        this.velocity=new Vector();
        this.angle = 0.0;
        this.size = new Vector(GameConstants.alienSize, GameConstants.alienSize);
        this.dynamic = true;
        this.currentStrategy = new RepairingAlienStrategy();
    }



}
