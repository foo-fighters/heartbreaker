package brickingbad.domain.game;

import java.lang.reflect.InvocationTargetException;

public interface AnimationListener {
    void addAnimation(String animationName, Object... args) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException;
}
