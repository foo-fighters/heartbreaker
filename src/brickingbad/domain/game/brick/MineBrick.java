package brickingbad.domain.game.brick;

import brickingbad.domain.game.brick.Brick;

public class MineBrick extends Brick {

    public MineBrick() {
    }

    private int explosiveRadius;

    public int getExplosiveRadius() {
        return explosiveRadius;
    }
}
