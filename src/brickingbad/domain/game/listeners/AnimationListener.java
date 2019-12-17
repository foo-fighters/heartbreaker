package brickingbad.domain.game.listeners;

import brickingbad.ui.game.animation.Animation;
import java.lang.reflect.InvocationTargetException;

public interface AnimationListener {
    void addAnimation(String animationName, Object... args)
            throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException;
    void removeAnimation(String animationName);
    void stopAnimation(Animation animation);
    void clearAllAnimations();
}
