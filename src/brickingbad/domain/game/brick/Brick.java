package brickingbad.domain.game.brick;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.Level;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.physics.Vector;

public abstract class Brick extends GameObject {

    protected int cellX;
    protected int cellY;

    @Override
    public void destroy() {
        Level.getInstance().anyBricksLeft();
        Level.getInstance().brickDestroyed();
        super.destroy();
    }

    @Override
    public void collide(GameObject object) {
        if(object instanceof Ball) return;
        reflect();
    }

    public void reflect() {
        setVelocity(new Vector(-getVelocity().getX(), getVelocity().getY()));
    }

    public void startMovement() {
    }

    public int getCellX() {
        return cellX;
    }

    public void setCellX(int cellX) {
        this.cellX = cellX;
    }

    public int getCellY() {
        return cellY;
    }

    public void setCellY(int cellY) {
        this.cellY = cellY;
    }
}
