package brickingbad.ui.game.animation;

import brickingbad.domain.game.listeners.AnimationListener;
import brickingbad.domain.game.GameConstants;

import java.awt.*;

public class CooperativeAlienRowAnimation extends Animation {

    int height = GameConstants.rectangularBrickThickness;
    int width = GameConstants.screenWidth;
    int startY;

    public CooperativeAlienRowAnimation(AnimationListener listener, double rowHeight) {
        this.listener = listener;
        this.startY = (int) rowHeight - height / 2;
    }

    @Override
    public void drawFrame(Graphics g) {
        g.setColor(new Color(255, 102, 255, 100));
        g.fillRect(0, startY, width, height);
    }
}
