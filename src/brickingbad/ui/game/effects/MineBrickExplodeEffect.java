package brickingbad.ui.game.effects;

import brickingbad.domain.game.GameConstants;

import java.awt.*;

public class MineBrickExplodeEffect implements EffectsStrategy {

  @Override
  public void showEffect(Point position, Graphics g) {
    g.fillOval((int) position.getX(),
               (int) position.getY(),
               (int) GameConstants.mineBrickExplosionRadius,
               (int) GameConstants.mineBrickExplosionRadius);
  }


}
