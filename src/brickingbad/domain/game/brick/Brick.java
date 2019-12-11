package brickingbad.domain.game.brick;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.physics.Vector;

public abstract class Brick extends GameObject {
    @Override
    public void destroy() {
        Game.getInstance().anyBricksLeft();
        Game.getInstance().brickDestroyed();
        super.destroy();
    }

    @Override
    public void collide(GameObject object) {
        setVelocity(new Vector(-getVelocity().getX(), getVelocity().getY()));
    }

}
