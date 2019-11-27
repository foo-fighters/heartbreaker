package brickingbad.domain.game.powerup;

import brickingbad.domain.game.*;
import brickingbad.domain.physics.Vector;

public class ChemicalBall extends PowerUp {

    public ChemicalBall(Vector revealPosition) {
        this.position = new Vector(revealPosition.getX(), revealPosition.getY());
        this.shape = Shape.RECTANGLE;
        this.size = new Vector(GameConstants.powerupSize, GameConstants.powerupSize);
        this.velocity = new Vector(0, GameConstants.powerupFallSpeed);
        this.angle = 0.0;
        this.name = WrapperContent.CHEMICAL_BALL;
    }

    @Override
    public void activate() {
        for(GameObject object: Game.getInstance().getObjects()) {
            if(object instanceof Ball) {
                ((Ball) object).setChemical();
            }
        }
    }
}
