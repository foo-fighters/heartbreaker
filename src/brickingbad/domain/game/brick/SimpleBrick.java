package brickingbad.domain.game.brick;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Shape;
import brickingbad.domain.physics.Vector;

public class SimpleBrick extends Brick {

    public SimpleBrick() {
        this.position = new Vector();
        this.shape = Shape.RECTANGLE;
        this.size = new Vector(GameConstants.rectangularBrickLength, GameConstants.rectangularBrickThickness);
        this.angle = 0.0;
        double rand = Math.random();
        if (rand < GameConstants.brickMovementProbability) {
            this.dynamic = true;
            if(rand < GameConstants.brickMovementProbability / 2) {
                this.velocity = new Vector(GameConstants.brickMovementSpeed, 0.0);
            }else {
                this.velocity = new Vector(-GameConstants.brickMovementSpeed, 0.0);
            }
        }else {
            this.velocity = new Vector();
        }
    }

    @Override
    public void startMovement() {
        double rand = Math.random();
        if (rand < GameConstants.brickMovementProbability) {
            this.dynamic = true;
            if(rand < GameConstants.brickMovementProbability / 2) {
                this.velocity = new Vector(GameConstants.brickMovementSpeed, 0.0);
            }else {
                this.velocity = new Vector(-GameConstants.brickMovementSpeed, 0.0);
            }
        }
    }

}
