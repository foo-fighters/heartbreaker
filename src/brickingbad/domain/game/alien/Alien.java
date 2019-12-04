package brickingbad.domain.game.alien;

import brickingbad.domain.game.GameObject;

import java.lang.reflect.InvocationTargetException;

public abstract class Alien extends GameObject {

    abstract void performAction();

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

