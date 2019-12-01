package brickingbad.ui.effects;

import java.awt.*;

public abstract class Effect {

  Point position;
  boolean active;

  public Effect(double x, double y) {
    position = new Point((int) x, (int) y);
  }

  public void activate(Graphics g) {

  }

  public boolean isActive() {
    return active;
  }

}
