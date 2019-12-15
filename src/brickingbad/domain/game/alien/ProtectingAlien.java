package brickingbad.domain.game.alien;

import brickingbad.domain.game.*;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.game.powerup.PowerUp;
import brickingbad.domain.physics.Vector;
import brickingbad.domain.physics.alien.ProtectingAlienState;

public class ProtectingAlien extends Alien {

    public ProtectingAlien() {
        this.position= new Vector();
        this.velocity = new Vector();
        this.shape= Shape.RECTANGLE;
        this.angle = 0.0;
        this.size = new Vector(GameConstants.alienSize, GameConstants.alienSize);
        this.dynamic = true;
        this.alienState = new ProtectingAlienState(this);
        this.name = WrapperContent.PROTECTING_ALIEN;
    }

}
