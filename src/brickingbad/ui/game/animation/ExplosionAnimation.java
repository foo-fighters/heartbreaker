package brickingbad.ui.game.animation;

import brickingbad.domain.game.AnimationListener;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.physics.Vector;

import java.awt.*;

public class ExplosionAnimation extends Animation {

    int centerX;
    int centerY;
    int radius;
    double size;

    public ExplosionAnimation(AnimationListener listener, Vector center, double size) {
        this.listener = listener;
        this.size = size;
        centerX = (int) center.getX();
        centerY = (int) center.getY();
        totalTime = 2 * GameConstants.calculationsPerSecond;
        remainingTime = totalTime;
    }

    @Override
    public void drawFrame(Graphics g) {
        radius = (int) (size * remainingTime / totalTime);
        g.drawOval(centerX, centerY, radius, radius);
        remainingTime--;
        if(remainingTime == 0) {
            listener.stopAnimation(this);
        }
    }
}
