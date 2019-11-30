package brickingbad.ui.effects;

import brickingbad.domain.game.GameConstants;

import java.awt.*;

public class MineBrickExplodeEffect extends Effect {

  public MineBrickExplodeEffect(int x, int y) {
    super(x, y);
  }

  @Override
  public void activate(Graphics g) {
    int radius = (int) GameConstants.mineBrickExplosionRadius;
    g.setColor(Color.orange);
    g.fillOval((int) position.getX() - (radius - GameConstants.circularBrickSize)/2,
            (int) position.getY() - (radius - GameConstants.circularBrickSize)/2,
            radius,
            radius);
  }

}
