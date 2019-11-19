package brickingbad.domain.game.brick;

import brickingbad.domain.physics.Vector;

public class SimpleBrick extends Brick {

    public SimpleBrick() {
        position = new Vector(10,10);
    }

    public SimpleBrick(Vector position) {
        this.position = position;
    }

}
