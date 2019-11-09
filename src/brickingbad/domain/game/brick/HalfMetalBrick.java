package brickingbad.domain.game.brick;

import brickingbad.domain.game.brick.Brick;

public class HalfMetalBrick extends Brick {

    public HalfMetalBrick() {
    }

    private boolean isCracked = false;

    public boolean isCracked() {
        return isCracked;
    }

    public void setCracked(boolean cracked) {
        isCracked = cracked;
    }
}
