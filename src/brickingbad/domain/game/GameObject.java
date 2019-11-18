package brickingbad.domain.game;

public abstract  class GameObject {

  protected Shape shape;
  protected int xsize;
  protected int ysize;
  protected int xpos;
  protected int ypos;
  protected int xvel;
  protected int yvel;

  public void updatePosition() {
    xpos += xvel;
    ypos += yvel;
  }

  public void reflect(GameObject object) { }

  public void destroy() { }

}
