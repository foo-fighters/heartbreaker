package brickingbad.domain.game;

import brickingbad.domain.game.brick.HalfMetalBrick;
import brickingbad.ui.game.animation.Animation;
import java.lang.reflect.InvocationTargetException;

public interface AnimationListener {
    void addAnimation(String animationName, Object... args)
            throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException;
    void removeAnimation(String animationName);
    void stopAnimation(Animation animation);
    void updateBalls(String stateModifier);
    void crackHalfMetalBrick(HalfMetalBrick brick);
}
