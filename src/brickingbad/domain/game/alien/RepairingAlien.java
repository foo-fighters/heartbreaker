package brickingbad.domain.game.alien;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Shape;
import brickingbad.domain.game.alien.Alien;
import brickingbad.domain.physics.Vector;

public class RepairingAlien extends Alien {

    public RepairingAlien() {
        this.position= new Vector();
        this.shape= Shape.RECTANGLE;
        this.velocity=new Vector();
        this.angle = 0.0;
        this.size = new Vector(GameConstants.alienSize, GameConstants.alienSize);
    }

    public RepairingAlien(int repairCooldown) {
        this.repairCooldown = repairCooldown;
    }

    private int repairCooldown;

    public int getRepairCooldown() {
        return repairCooldown;
    }

    public void setRepairCooldown(int repairCooldown) {
        this.repairCooldown = repairCooldown;
    }

    @Override
    void performAction() {

    }
}
