package brickingbad.domain.game;

import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.Vector;

public abstract class GameObject {

  protected Shape shape;
  protected Vector size;
  protected Vector position;
  protected Vector velocity;
  protected double angle;

  protected boolean colliding;
  protected GameObject collidedObject;
  protected Direction reflectionDirection;

  public void updatePosition() {
    position.addVector(velocity.product(1.0 / GameConstants.calculationsPerSecond));
  }

  public void reflect(GameObject object) { }

  public void destroy() {
    Game.getInstance().removeObject(this);
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

  public GameObject getCollidedObject() { return collidedObject; }

  public void setCollidedObject(GameObject collidedObject) { this.collidedObject = collidedObject; }

  public boolean isColliding() { return colliding; }

  public void setColliding(boolean colliding) { this.colliding = colliding; }

  public double getAngle() { return angle; }

  public void setAngle(double angle) { this.angle = angle; }

}
