package brickingbad.domain.game.alien;

import brickingbad.domain.game.GameObject;

import java.lang.reflect.InvocationTargetException;

public abstract class Alien extends GameObject {

    protected AlienStrategy currentStrategy;

    @Override
    public void updatePosition() {
        super.updatePosition();
        performAction();
    }

    public void performAction() {
        currentStrategy.performAction();
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

}

