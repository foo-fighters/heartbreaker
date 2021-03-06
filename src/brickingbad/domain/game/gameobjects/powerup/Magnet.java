package brickingbad.domain.game.gameobjects.powerup;

import brickingbad.domain.game.Level;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Shape;
import brickingbad.domain.game.WrapperContent;
import brickingbad.domain.physics.Vector;

public class Magnet extends PowerUp {

    private int startTime;

    public Magnet(Vector revealPosition) {
        this.position = new Vector(revealPosition.getX(), revealPosition.getY());
        this.shape = Shape.RECTANGLE;
        this.size = new Vector(GameConstants.powerupSize, GameConstants.powerupSize);
        this.velocity = new Vector(0, GameConstants.powerupFallSpeed);
        this.angle = 0.0;
        this.name = WrapperContent.MAGNET;
        this.duration = GameConstants.magnetDuration;
        this.dynamic = true;
    }

    @Override
    public void activate() {
        super.activate();
        Level.getInstance().getPaddle().setMagnetized(true);
    }

    @Override
    public void deactivate() {
        Level.getInstance().getPaddle().setMagnetized(false);
        super.deactivate();
    }
}
