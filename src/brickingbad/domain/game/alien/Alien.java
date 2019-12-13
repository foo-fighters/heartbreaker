package brickingbad.domain.game.alien;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.WrapperContent;
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
    public void destroy() {
        Game.getInstance().getActiveAliens().remove(this);
        super.destroy();
    }

    public void performAction() {
        alienState.performAction();
    }

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

