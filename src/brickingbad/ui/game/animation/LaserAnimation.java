package brickingbad.ui.game.animation;

import brickingbad.domain.game.AnimationListener;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.physics.Vector;

import java.awt.*;

public class LaserAnimation extends Animation {

    double size = GameConstants.rectangularBrickLength / 2;
    int startX;
    int startY = GameConstants.screenHeight - GameConstants.paddleHeightOnScreen;
    int endY;
    int width;
    int height;

    public LaserAnimation(AnimationListener listener, double startX, double endY) {
        this.listener = listener;
        this.endY = (int) endY;
        this.startX = (int) startX;
        this.height = startY - this.endY;
        totalTime = GameConstants.calculationsPerSecond / 4;
        remainingTime = totalTime;
    }

    @Override
    public void drawFrame(Graphics g) {
        width = (int) (size * remainingTime / totalTime);
        g.setColor(new Color(255, 0, 0, 200));
        g.fillRect(startX - width / 2, endY, width, height);
        remainingTime--;
        if(remainingTime == 0) {
            listener.stopAnimation(this);
        }
    }
}
