package brickingbad.domain.game.brick;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameObject;

public abstract class Brick extends GameObject {
    @Override
    public void destroy() {
        Game.getInstance().anyBricksLeft();
        Game.getInstance().brickDestroyed();
        super.destroy();
    }
}
