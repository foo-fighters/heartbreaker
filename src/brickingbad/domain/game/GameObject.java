package brickingbad.domain.game;

import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.Vector;

import java.util.ArrayList;

public abstract class GameObject implements Comparable {

  protected Shape shape;
  protected Vector size;
  protected Vector position;
  protected Vector velocity;
  protected double angle = 0.0;

  private ArrayList<GameObject> collidedObjects = new ArrayList<>();
  Direction reflectionDirection;

  public void updatePosition() { position = position.sum(velocity.product(1.0 / GameConstants.calculationsPerSecond)); }

  public void collide(GameObject object) { }

  public void destroy() {
    Game.getInstance().removeObject(this);
    if (this instanceof Ball){
      Game.getInstance().anyBallLeft();
    }
  }

  public Shape getShape() {
    return shape;
  }

  public void setShape(Shape shape) {
    this.shape = shape;
  }

  public Vector getSize() {
    return size;
  }

  public void setSize(Vector size) {
    this.size = size;
  }

  public Vector getPosition() {
    return position;
  }

  public void setPosition(Vector position) {
    this.position = position;
  }

  public Vector getVelocity() {
    return velocity;
  }

  public void setVelocity(Vector velocity) {
    this.velocity = velocity;
  }

  public Direction getReflectionDirection() {
    return reflectionDirection;
  }

  public void setReflectionDirection(Direction direction) {
    this.reflectionDirection = direction;
  }

  public ArrayList<GameObject> getCollidedObjects() { return collidedObjects; }

  public void addCollidedObject(GameObject collidedObject) { collidedObjects.add(collidedObject); }

  public void removeCollidedObject(GameObject collidedObject) { collidedObjects.remove(collidedObject); }

  public double getAngle() { return angle; }

  public void setAngle(double angle) { this.angle = angle; }

  @Override
  public int compareTo(Object obj) {
    double distance = Math.pow(position.getX(), 2) + Math.pow(position.getY(), 2);
    GameObject object = (GameObject) obj;
    double compareDistance = Math.pow(object.getPosition().getX(), 2) + Math.pow(object.getPosition().getY(), 2);
    return Double.compare(distance, compareDistance);
  }

}
