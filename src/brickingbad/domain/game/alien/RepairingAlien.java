package brickingbad.domain.game.alien;

import brickingbad.controller.EffectsController;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Shape;
import brickingbad.domain.game.WrapperContent;
import brickingbad.domain.physics.Vector;
import brickingbad.domain.physics.alien.RepairingAlienState;

public class RepairingAlien extends Alien {

    public RepairingAlien() {
        this.position= new Vector();
        this.shape= Shape.RECTANGLE;
        this.velocity=new Vector();
        this.angle = 0.0;
        this.size = new Vector(GameConstants.alienSize, GameConstants.alienSize);
        this.alienState = new RepairingAlienState(this);
        this.name = WrapperContent.REPAIRING_ALIEN;
        EffectsController.getInstance().playAudio("alienSpawn");
    }

}
