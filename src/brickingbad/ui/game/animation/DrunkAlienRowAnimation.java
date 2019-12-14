package brickingbad.ui.game.animation;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.listeners.AnimationListener;

import java.awt.*;

public class DrunkAlienRowAnimation extends Animation {

    int height = GameConstants.rectangularBrickThickness;
    int width = GameConstants.screenWidth;
    int startY;

    public DrunkAlienRowAnimation(AnimationListener listener, double rowHeight) {
        this.listener = listener;
        this.startY = (int) rowHeight - height / 2;
    }

    @Override
    public void drawFrame(Graphics g) {
        g.setColor(new Color(255, 102, 255, 100));
        g.fillRect(0, startY, width, height);
    }

}
