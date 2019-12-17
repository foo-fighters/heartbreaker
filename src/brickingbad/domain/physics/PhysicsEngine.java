package brickingbad.domain.physics;

import brickingbad.controller.GameController;
import brickingbad.domain.game.*;
import brickingbad.domain.game.gameobjects.brick.Brick;
import brickingbad.domain.game.gameobjects.brick.HalfMetalBrick;
import brickingbad.domain.game.gameobjects.brick.SimpleBrick;
import brickingbad.domain.game.gameobjects.GameObject;
import brickingbad.domain.physics.collisions.CircleCollisionStrategy;
import brickingbad.domain.physics.collisions.ICollisionStrategy;
import brickingbad.domain.physics.collisions.MixedCollisionStrategy;
import brickingbad.domain.physics.collisions.RectangleCollisionStrategy;

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
  private static boolean running = false;

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
      System.out.println("Physics engine resumed.");
      running = true;
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
        timePassed += SLEEP_TIME;
        ArrayList<GameObject> objects = Level.getInstance().getObjects();
        handleCollisions(objects);
        updatePositions(objects);
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
   * Gets the list of {@link GameObject}s from the {@link Level} and checks if objects are colliding.
   * Adds the objects to their collidingObjects lists if they are colliding.
   * @param objects
   */
  private void handleCollisions(ArrayList<GameObject> objects) {
    // MODIFIES: objects
    // EFFECTS: checks collisions between game objects, then calls each colliding object's collide function.
    ArrayList<GameObject> objectsCopy = objects;
    for(int i = 0; i < objectsCopy.size(); i++) {
      GameObject o1 = objectsCopy.get(i);
      if (o1.isDynamic()){
        for(int j = 0; j < objectsCopy.size(); j++) {
          GameObject o2 = objectsCopy.get(j);
          if(!o2.isDynamic() || (j > i)) {
            if((o1 instanceof SimpleBrick || o1 instanceof HalfMetalBrick)
                    && (o2 instanceof SimpleBrick || o2 instanceof HalfMetalBrick)
                    && (((Brick) o1).getCellY() != ((Brick) o2).getCellY())) continue;
            if(areColliding(o1, o2)) {
              if(!o1.getCollidedObjects().contains(o2) && !o1.getCollidedObjects().contains(o2)) {
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
   * Gets the list of {@link GameObject}s from the {@link Level} and calls their {@link GameObject#updatePosition()} methods.
   * @param objects
   */
  public void updatePositions(ArrayList<GameObject> objects) {
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
  public boolean areColliding(GameObject o1, GameObject o2) {
    // REQUIRES: given objects are not null, their shapes, sizes, and positions are also not null.
    // EFFECTS: returns true if the given 2D objects would collide in a 2D space.
    ICollisionStrategy collisionStrategy;
    if (o1.getShape() == Shape.RECTANGLE && o2.getShape() == Shape.RECTANGLE) {
      collisionStrategy = new RectangleCollisionStrategy();
    } else if (o1.getShape() == Shape.CIRCLE && o2.getShape() == Shape.CIRCLE) {
      collisionStrategy = new CircleCollisionStrategy();
    } else {
      collisionStrategy = new MixedCollisionStrategy();
    }
    return collisionStrategy.areColliding(o1, o2);
  }

  /**
   * Checks two objects of different shapes (circle and rectangle) if they are colliding.
   * @param circle
   * @param rect
   * @return true if two objects are colliding, false otherwise.
   */


}
