package brickingbad.domain.game.powerup;

import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.WrapperContent;

public abstract class PowerUp extends GameObject {

    protected WrapperContent name;

    public void activate() {
    }

    public void deactivate() {
    }

    public WrapperContent getName() {
        return name;
    }

}
