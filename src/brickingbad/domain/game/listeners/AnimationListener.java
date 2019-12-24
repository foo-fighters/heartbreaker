package brickingbad.domain.game.listeners;

import java.lang.reflect.InvocationTargetException;

public interface AnimationListener {
    void addAnimation(String animationName, int animationTag, Object... args)
            throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException;
    void removeAnimation(String animationName, int animationTag);
    void stopAnimation(Object animation);
    void clearAllAnimations();
}
