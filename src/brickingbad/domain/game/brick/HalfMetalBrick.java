package brickingbad.domain.game.brick;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Shape;
import brickingbad.domain.physics.Vector;

public class HalfMetalBrick extends Brick {

    private boolean isCracked;

    public HalfMetalBrick() {
        this.position = new Vector();
        this.shape = Shape.RECTANGLE;
        this.size = new Vector(GameConstants.rectangularBrickLength, GameConstants.rectangularBrickThickness);
        this.angle = 0.0;
        this.isCracked = false;
        this.velocity = new Vector();
        this.name = "HalfMetalBrick";

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

    public boolean isCracked() {
        return isCracked;
    }

    public void setCracked(boolean cracked) {
        isCracked = cracked;
        Game.getInstance().crackHalfMetalBrick(this);
    }

}
