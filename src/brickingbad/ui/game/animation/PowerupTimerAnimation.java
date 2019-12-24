package brickingbad.ui.game.animation;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.listeners.AnimationListener;
import brickingbad.domain.physics.Vector;

import java.awt.*;

public class PowerupTimerAnimation extends Animation {

    int centerX;
    int centerY;
    int length;
    double size;

    public PowerupTimerAnimation(AnimationListener listener, int tag, Vector center, double length, double duration) {
        this.listener = listener;
        this.tag = tag;
        this.size = length;
        centerX = (int) center.getX();
        centerY = (int) center.getY();
        totalTime = (int) duration * GameConstants.calculationsPerSecond;
        remainingTime = totalTime;
        front = true;
    }

    @Override
    public void drawFrame(Graphics g) {
        length = (int) (size * remainingTime / totalTime);
        g.setColor(new Color(0, 148, 17, 200));
        g.fillRect((int) (centerX - size * 0.5), (int) (centerY + size * 0.5 - length), (int)size, length);
        remainingTime--;
        if(remainingTime == 0) {
            listener.stopAnimation(this);
        }
    }
}
