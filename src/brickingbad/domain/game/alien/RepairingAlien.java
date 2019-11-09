package brickingbad.domain.game.alien;

import brickingbad.domain.game.alien.Alien;

public class RepairingAlien extends Alien {

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
