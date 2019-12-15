package brickingbad.domain.physics;

import brickingbad.controller.GameController;
import brickingbad.domain.game.*;

import java.util.ArrayList;

/**
 * PhysicsEngine is responsible of detecting and delegating the collisions in the game.
 * It also handles the position updates for game objects.
 */
public class PhysicsEngine implements Runnable {
  // OVERVIEW: A physics engine that handles physical collisions and position calculations of game objects.

  /**
   * The time the thread sleeps at each iteration.
   */
  private final int SLEEP_TIME = 1000 / GameConstants.calculationsPerSecond;

  /**
   * Keeps the time passed since the engine started in seconds.
   */
  private int timePassed = 0;

  /**
   * The sole instance of the PhysicsEngine since it is implemented as a singleton.
   */
  private static PhysicsEngine instance;

  /**
   * True if thread is running, false otherwise.
   */
  private static boolean running;

  /**
   * Private constructor for singleton implementation.
   */
  private PhysicsEngine() {

  }

  /**
   * Straightforward method for singleton implementation.
   * @return the single PhysicsEngine instance
   */
  public static PhysicsEngine getInstance() {
    if (instance == null) {
      instance = new PhysicsEngine();
      running = true;
    }
    return instance;
  }

  /**
   * Starts the thread bound to the PhysicsEngine.
   */
  public void start() {
    // EFFECTS: starts a new thread from this instance.
    (new Thread(instance)).start();
  }

  /**
   * Changes the thread's state from running to paused and vice versa.
   */
  public void togglePauseResume() {
    // MODIFIES: field "running"
    // EFFECTS: changes the boolean field "running" of this instance.
    if (running) {
      System.out.println("Physics engine paused.");
      running = false;
    } else {
      System.out.println("Physics engine resumed.");
      running = true;
    }
  }

  /**
   * Resumes the thread if it is paused.
   */
  public void resumeIfPaused() {
    if (!running) {
      togglePauseResume();
    }
  }

  /**
   * The main method of the thread.
   * Calls {@link PhysicsEngine#handleCollisions(ArrayList)} and {@link PhysicsEngine#updatePositions(ArrayList)} at each iteration.
   */
  @Override
  public void run() {
    // REQUIRES: a thread is running from this instance.
    // EFFECTS: handles collisions and updates positions of each game objects during this frame.
    while (true) {
      try {
        Thread.sleep(SLEEP_TIME);
      } catch (InterruptedException e) {
        System.out.println("Program interrupted.");
      }
      if (running) {
        if (GameController.getInstance().inRunningMode()) {
          timePassed += SLEEP_TIME;
          ArrayList<GameObject> objects = Game.getInstance().getObjects();
          handleCollisions(objects);
          updatePositions(objects);
        }
      }
    }
  }

  public int getTimePassed() {
    return timePassed;
  }

  /**
   * Sets {@link PhysicsEngine#timePassed} to 0.
   */
  public void resetTimePassed() {
    this.timePassed = 0;
  }

  /**
   * Gets the list of {@link GameObject}s from the {@link Game} and checks if objects are colliding.
   * Adds the objects to their collidingObjects lists if they are colliding.
   * @param objects
   */
  private static void handleCollisions(ArrayList<GameObject> objects) {
    // MODIFIES: objects
    // EFFECTS: checks collisions between game objects, then calls each colliding object's collide function.
    ArrayList<GameObject> objectsCopy = objects;
    for(int i = 0; i < objectsCopy.size(); i++) {
      GameObject o1 = objectsCopy.get(i);
      if (o1.isDynamic()){
        for(int j = 0; j < objectsCopy.size(); j++) {
          GameObject o2 = objectsCopy.get(j);
          if(!o2.isDynamic() || (j > i)) {
            if(areColliding(o1, o2)) {
              if(!o1.getCollidedObjects().contains(o2) && !o1.getCollidedObjects().contains(o2)){
                o1.collide(o2);
                o2.collide(o1);
                o1.getCollidedObjects().add(o2);
                o2.getCollidedObjects().add(o1);
              }
            }else {
              o1.getCollidedObjects().remove(o2);
              o2.getCollidedObjects().remove(o1);
            }
          }
        }
      }
    }
  }

  /**
   * Gets the list of {@link GameObject}s from the {@link Game} and calls their {@link GameObject#updatePosition()} methods.
   * @param objects
   */
  public static void updatePositions(ArrayList<GameObject> objects) {
    // MODIFIES: objects
    // EFFECTS: each object's position is updated by adding their one-frame velocity to them.
    ArrayList<GameObject> objectsCopy = new ArrayList<>(objects);
    for (GameObject object : objectsCopy) {
      if (object != null) {
        object.updatePosition();
      }
    }
  }

  /**
   * Takes in two {@link GameObject} instances and returns true if they are colliding, false otherwise.
   * @param o1
   * @param o2
   * @return the result of the collision check between two objects.
   */
  public static boolean areColliding(GameObject o1, GameObject o2) {
    // REQUIRES: given objects are not null, their shapes, sizes, and positions are also not null.
    // EFFECTS: returns true if the given 2D objects would collide in a 2D space.
    double o1_posx = o1.getPosition().getX();
    double o1_posy = o1.getPosition().getY();
    double o2_posx = o2.getPosition().getX();
    double o2_posy = o2.getPosition().getY();
    double distx = (o1.getSize().getX() + o2.getSize().getX()) / 2.0;
    double disty = (o1.getSize().getY() + o2.getSize().getY()) / 2.0;

    if (o1.getShape() == Shape.RECTANGLE && o2.getShape() == Shape.RECTANGLE) {
      return Math.abs(o1_posx - o2_posx) < distx && Math.abs(o1_posy - o2_posy) < disty;
    } else if (o1.getShape() == Shape.CIRCLE && o2.getShape() == Shape.CIRCLE) {
      return Math.hypot(o1_posx - o2_posx, o1_posy - o2_posy) < distx;
    } else if (o1.getShape() == Shape.CIRCLE) {
      return mixedColliding(o1, o2);
    } else {
      return mixedColliding(o2, o1);
    }
  }

  /**
   * Checks two objects of different shapes (circle and rectangle) if they are colliding.
   * @param circle
   * @param rect
   * @return true if two objects are colliding, false otherwise.
   */
  public static boolean mixedColliding(GameObject circle, GameObject rect) {
    // REQUIRES: object "circle" has the shape Shape.CIRCLE and object "rect" has the shape Shape.RECTANGLE.
    // EFFECTS: returns true if the given circular and rectangular objects would collide in a 2-dimensional space.
    double circle_x = circle.getPosition().getX();
    double circle_y = circle.getPosition().getY();
    double radius = circle.getSize().getX() / 2.0;
    double rect_x = rect.getPosition().getX();
    double rect_y = rect.getPosition().getY();
    double rect_angle = Math.toRadians(rect.getAngle());
    double rect_half_x = rect.getSize().getX() / 2.0;
    double rect_half_y = rect.getSize().getY() / 2.0;
    Vector rect_x1 = new Vector(rect_x - Math.cos(rect_angle) * rect_half_x, rect_y + Math.sin(rect_angle) * rect_half_x);
    Vector rect_x2 = new Vector(rect_x + Math.cos(rect_angle) * rect_half_x, rect_y - Math.sin(rect_angle) * rect_half_x);
    Vector rect_y1 = new Vector(rect_x + Math.sin(rect_angle) * rect_half_y, rect_y + Math.cos(rect_angle) * rect_half_y);
    Vector rect_y2 = new Vector(rect_x - Math.sin(rect_angle) * rect_half_y, rect_y - Math.cos(rect_angle) * rect_half_y);
    Line line_x1 = new Line(rect_x1, Math.PI / 2.0 + rect_angle);
    Line line_x2 = new Line(rect_x2, Math.PI / 2.0 + rect_angle);
    Line line_y1 = new Line(rect_y1, rect_angle);
    Line line_y2 = new Line(rect_y2, rect_angle);
    Vector point_x1y1 = line_x1.intersection(line_y1);
    Vector point_x1y2 = line_x1.intersection(line_y2);
    Vector point_x2y1 = line_x2.intersection(line_y1);
    Vector point_x2y2 = line_x2.intersection(line_y2);

    if (line_x1.isVectorLeft(circle.getPosition())) {
      if (line_y1.isVectorBelow(circle.getPosition())) {
        if (Math.hypot(circle_x - point_x1y1.getX(), circle_y - point_x1y1.getY()) < radius) {
          circle.setReflectionDirection(Direction.DOWN_LEFT);
          return true;
        } else return false;
      } else if (line_y2.isVectorAbove(circle.getPosition())) {
        if (Math.hypot(circle_x - point_x1y2.getX(), circle_y - point_x1y2.getY()) < radius) {
          circle.setReflectionDirection(Direction.UP_LEFT);
          return true;
        } else return false;
      } else {
        if (line_x1.distanceToLine(circle.getPosition()) < radius) {
          circle.setReflectionDirection(Direction.LEFT);
          return true;
        } else return false;
      }
    } else if (line_x2.isVectorRight(circle.getPosition())) {
      if (line_y1.isVectorBelow(circle.getPosition())) {
        if (Math.hypot(circle_x - point_x2y1.getX(), circle_y - point_x2y1.getY()) < radius) {
          circle.setReflectionDirection(Direction.DOWN_RIGHT);
          return true;
        } else return false;
      } else if (line_y2.isVectorAbove(circle.getPosition())) {
        if (Math.hypot(circle_x - point_x2y2.getX(), circle_y - point_x2y2.getY()) < radius) {
          circle.setReflectionDirection(Direction.UP_RIGHT);
          return true;
        } else return false;
      } else {
        if (line_x2.distanceToLine(circle.getPosition()) < radius) {
          circle.setReflectionDirection(Direction.RIGHT);
          return true;
        } else return false;
      }
    } else {
      if (line_y1.isVectorBelow(circle.getPosition())) {
        if (line_y1.distanceToLine(circle.getPosition()) < radius) {
          circle.setReflectionDirection(Direction.DOWN);
          return true;
        } else return false;
      } else if (line_y2.isVectorAbove(circle.getPosition())) {
        if (line_y2.distanceToLine(circle.getPosition()) < radius) {
          circle.setReflectionDirection(Direction.UP);
          return true;
        } else return false;
      } else {
        circle.setReflectionDirection(Direction.DOWN);
        return true;
      }
    }
  }

}
