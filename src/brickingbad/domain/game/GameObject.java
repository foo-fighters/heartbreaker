package brickingbad.domain.game;

import brickingbad.domain.physics.Vector;

public abstract  class GameObject {

  protected Shape shape;
  protected Vector size;
  protected Vector position;
  protected Vector velocity;

  public void updatePosition() {
    position.addVector(velocity);
  }

  public void reflect(GameObject object) { }

  public void destroy() { }

}
