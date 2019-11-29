package brickingbad.domain.game.powerup;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.WrapperContent;

public abstract class PowerUp extends GameObject {

    protected WrapperContent name;
    protected long startTime;
    protected double duration;
    protected boolean active = false;

    public void activate() {
        startTime = Game.getInstance().getTime();
        active = true;
    }

    public void deactivate() {
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        if(active && Game.getInstance().getTime() - startTime > 1000 * duration) deactivate();
    }

    public WrapperContent getName() {
        return name;
    }

}
