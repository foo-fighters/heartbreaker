package brickingbad.domain.game.powerup;

import brickingbad.domain.game.*;
import brickingbad.domain.physics.Vector;
import brickingbad.domain.physics.ball.ChemicalBallState;

public class ChemicalBall extends PowerUp {

    public ChemicalBall(Vector revealPosition) {
        this.position = new Vector(revealPosition.getX(), revealPosition.getY());
        this.shape = Shape.RECTANGLE;
        this.size = new Vector(GameConstants.powerupSize, GameConstants.powerupSize);
        this.velocity = new Vector(0, GameConstants.powerupFallSpeed);
        this.angle = 0.0;
        this.name = WrapperContent.CHEMICAL_BALL;
        this.duration = GameConstants.chemicalBallDuration;
        this.dynamic = true;
    }

    @Override
    public void activate() {
        super.activate();
        for(GameObject object: Game.getInstance().getObjects()) {
            if(object instanceof Ball) {
                ((Ball) object).setChemical();
            }
        }
    }

    @Override
    public void deactivate() {
        for(GameObject object: Game.getInstance().getObjects()) {
            if(object instanceof Ball && ((Ball) object).getBallState() instanceof ChemicalBallState) {
                ((Ball) object).setSimple();
            }
        }
        super.deactivate();
    }
}
