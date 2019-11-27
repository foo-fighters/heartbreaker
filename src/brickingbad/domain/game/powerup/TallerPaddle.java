package brickingbad.domain.game.powerup;

import brickingbad.domain.game.*;
import brickingbad.domain.physics.Vector;

public class TallerPaddle extends PowerUp {

    public TallerPaddle(Vector revealPosition) {
        this.position = new Vector(revealPosition.getX(), revealPosition.getY());
        this.shape = Shape.RECTANGLE;
        this.size = new Vector(GameConstants.powerupSize, GameConstants.powerupSize);
        this.velocity = new Vector(0, GameConstants.powerupFallSpeed);
        this.angle = 0.0;
        this.name = WrapperContent.TALLER_PADDLE;
    }

    @Override
    public void activate() {
        Paddle paddle = Game.getInstance().getPaddle();
        paddle.getSize().setVector(paddle.getSize().getX() * 2.0, paddle.getSize().getY());
    }
}
