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

    public ExplosionAnimation(AnimationListener listener, Vector center, double radius) {
        this.listener = listener;
        this.size = radius;
        centerX = (int) center.getX();
        centerY = (int) center.getY();
        totalTime = GameConstants.calculationsPerSecond / 2;
        remainingTime = totalTime;
    }

    @Override
    public void drawFrame(Graphics g) {
        radius = (int) (size * remainingTime / totalTime);
        g.setColor(new Color(255, 102, 0, 128));
        g.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        remainingTime--;
        if(remainingTime == 0) {
            listener.stopAnimation(this);
        }
    }
}
