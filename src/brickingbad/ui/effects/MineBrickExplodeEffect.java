package brickingbad.ui.effects;

import brickingbad.domain.game.GameConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class MineBrickExplodeEffect extends Effect {

  private final int radius = (int) GameConstants.mineBrickExplosionRadius;
  private final Ellipse2D explosionCircle;

  public MineBrickExplodeEffect(double x, double y) {
    super(x, y);
    active = true;
    explosionCircle = new Ellipse2D.Double((int) position.getX() - (radius - GameConstants.circularBrickSize)/2.0,
                                           (int) position.getY() - (radius - GameConstants.circularBrickSize)/2.0,
                                              radius,
                                              radius);
  }

  @Override
  public void activate(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(new Color(1f, 0.7f, 0.4f, 0.5f));
    g2.fill(explosionCircle);
    Timer timer = new Timer(75, (actionEvent) -> {
      active = false;
      g2.dispose();
    });
    timer.setRepeats(false);
    timer.start();
  }

}
