package brickingbad.domain.game.listeners;

import java.lang.reflect.InvocationTargetException;

public interface AnimationListener {
    void addAnimation(String animationName, Object... args)
            throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException;
    void removeAnimation(String animationName);
    void stopAnimation(Object animation);
    void clearAllAnimations();
}
