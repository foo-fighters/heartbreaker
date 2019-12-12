package brickingbad.ui.game.animation;
import brickingbad.domain.game.AnimationListener;
import java.awt.*;

public abstract class Animation {
    private AnimationListener listener;
    private int remainingTime;
    public abstract void drawFrame(Graphics g);
}
