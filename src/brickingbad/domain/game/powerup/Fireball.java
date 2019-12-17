package brickingbad.domain.game.powerup;

import brickingbad.domain.game.*;
import brickingbad.domain.game.gameobjects.Ball;
import brickingbad.domain.game.gameobjects.GameObject;
import brickingbad.domain.physics.Vector;

import java.util.ArrayList;

public class Fireball extends PowerUp {

    public Fireball(Vector revealPosition) {
        this.position = new Vector(revealPosition.getX(), revealPosition.getY());
        this.shape = Shape.RECTANGLE;
        this.size = new Vector(GameConstants.powerupSize, GameConstants.powerupSize);
        this.velocity = new Vector(0, GameConstants.powerupFallSpeed);
        this.angle = 0.0;
        this.name = WrapperContent.FIREBALL;
        this.dynamic = true;
    }

    @Override
    public void activate() {
        ArrayList<PowerUp> powerUpsCopy = new ArrayList<>(Level.getInstance().getActivePowerUps());
        for(PowerUp power: powerUpsCopy) {
            if(power instanceof ChemicalBall) {
                power.deactivate();
            }
        }
        for(GameObject object: Level.getInstance().getObjects()) {
            if(object instanceof Ball) {
                ((Ball) object).setFireball();
            }
        }
        deactivate();
    }

    @Override
    public void updatePosition() {
        position = position.sum(velocity.product(1.0 / GameConstants.calculationsPerSecond));
    }
}
