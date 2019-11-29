package brickingbad.ui.game.effects;

import brickingbad.domain.game.GameConstants;

import java.awt.*;

public class MineBrickExplodeEffect implements EffectsStrategy {

  @Override
  public void showEffect(Point position, Graphics g) {
    int radius = (int) GameConstants.mineBrickExplosionRadius;
    g.setColor(Color.orange);
    g.fillOval((int) position.getX() - (radius - GameConstants.circularBrickSize)/2,
               (int) position.getY() - (radius - GameConstants.circularBrickSize)/2,
                  radius,
                  radius);
  }


}
