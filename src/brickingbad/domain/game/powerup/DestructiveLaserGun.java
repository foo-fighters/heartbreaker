package brickingbad.domain.game.powerup;

import brickingbad.domain.game.*;
import brickingbad.domain.physics.Vector;

public class DestructiveLaserGun extends PowerUp {

    private int charges = GameConstants.laserGunCharges;

    public DestructiveLaserGun(Vector revealPosition) {
        this.position = new Vector(revealPosition.getX(), revealPosition.getY());
        this.shape = Shape.RECTANGLE;
        this.size = new Vector(GameConstants.powerupSize, GameConstants.powerupSize);
        this.velocity = new Vector(0, GameConstants.powerupFallSpeed);
        this.angle = 0.0;
        this.name = WrapperContent.DESTRUCTIVE_LASER_GUN;
        this.dynamic = true;
    }

    @Override
    public void activate() {
        double paddleX = Game.getInstance().getPaddle().getPosition().getX();
        Game.getInstance().shootLaserColumn(paddleX + Game.getInstance().getPaddle().paddleMountDistance);
        Game.getInstance().shootLaserColumn(paddleX - Game.getInstance().getPaddle().paddleMountDistance);
        charges--;
        if(charges == 0) deactivate();
    }

    @Override
    public void deactivate() {
        Game.getInstance().getStoredPowerUps().remove(this);
        destroy();
    }
}
