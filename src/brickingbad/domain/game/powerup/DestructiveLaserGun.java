package brickingbad.domain.game.powerup;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Shape;
import brickingbad.domain.game.WrapperContent;
import brickingbad.domain.physics.Vector;

public class DestructiveLaserGun extends PowerUp {

    public DestructiveLaserGun(Vector revealPosition) {
        this.position = new Vector(revealPosition.getX(), revealPosition.getY());
        this.shape = Shape.RECTANGLE;
        this.size = new Vector(GameConstants.powerupSize, GameConstants.powerupSize);
        this.velocity = new Vector(0, GameConstants.powerupFallSpeed);
        this.angle = 0.0;
        this.name = WrapperContent.DESTRUCTIVE_LASER_GUN;
    }

    @Override
    public void activate() {
        //super.activate();
    }
}
