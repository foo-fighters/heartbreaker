package brickingbad.ui.effects;

import java.awt.*;

public abstract class Effect {

  Point position;

  public Effect(int x, int y) {
    position = new Point(x, y);
  }

  public void activate(Graphics g) {

  }

}
