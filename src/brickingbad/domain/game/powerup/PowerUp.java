package brickingbad.domain.game.powerup;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.WrapperContent;
import brickingbad.domain.physics.Vector;

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

    public static PowerUp getByName(String name) {
        if (name.equals(WrapperContent.CHEMICAL_BALL.name())) {
            return new ChemicalBall(new Vector(0, 0));
        } else if (name.equals(WrapperContent.DESTRUCTIVE_LASER_GUN.name())) {
            return new DestructiveLaserGun(new Vector(0, 0));
        } else if (name.equals(WrapperContent.FIREBALL.name())) {
            return new Fireball(new Vector(0, 0));
        } else if (name.equals(WrapperContent.TALLER_PADDLE.name())) {
            return new TallerPaddle(new Vector(0, 0));
        } else if (name.equals(WrapperContent.MAGNET.name())) {
            return new Magnet(new Vector(0, 0));
        } else {
            return null;
        }
    }

}
