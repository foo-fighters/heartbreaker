package brickingbad.ui.game.animation;
import brickingbad.domain.game.listeners.AnimationListener;
import java.awt.*;

public abstract class Animation {
    protected AnimationListener listener;
    protected int totalTime;
    protected int remainingTime;
    public abstract void drawFrame(Graphics g);
}
