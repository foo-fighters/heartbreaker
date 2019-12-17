package brickingbad.domain.game.gameobjects.alien;

import brickingbad.domain.game.gameobjects.Ball;
import brickingbad.domain.game.Level;
import brickingbad.domain.game.gameobjects.GameObject;
import brickingbad.domain.game.WrapperContent;
import brickingbad.domain.game.gameobjects.brick.Brick;
import brickingbad.domain.game.powerup.PowerUp;
import brickingbad.domain.physics.Vector;
import brickingbad.domain.physics.alien.AlienState;

import java.lang.reflect.InvocationTargetException;

public abstract class Alien extends GameObject {

    protected AlienState alienState;
    protected WrapperContent name;

    @Override
    public void updatePosition() {
        super.updatePosition();
        performAction();
    }

    @Override
    public void collide(GameObject object) {
        if(object instanceof Ball || object instanceof PowerUp || object instanceof Brick) return;
        reflect();
    }

    public void reflect() {
        setVelocity(new Vector(-getVelocity().getX(), getVelocity().getY()));
    }

    @Override
    public void destroy() {
        finishAction();
        Level.getInstance().getActiveAliens().remove(this);
        super.destroy();
    }

    public void performAction() {
        alienState.performAction();
    }

    public void finishAction() { alienState.finishAction(); }

    public WrapperContent getName() {
        return name;
    }

    public String getTypeName() {
        return "Alien";
    }

    public static Alien getByType(String typeName) {
        Alien alien;
        try {
            alien = (Alien) Class.forName(typeName).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException |
                NoSuchMethodException |
                InstantiationException |
                IllegalAccessException |
                InvocationTargetException e){
            alien = null;
            e.printStackTrace();
        }
        return alien;
    }

    public AlienState getAlienState() {
        return alienState;
    }

}

