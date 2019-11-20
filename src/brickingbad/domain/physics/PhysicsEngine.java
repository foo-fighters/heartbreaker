package brickingbad.domain.physics;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.Shape;
import brickingbad.domain.physics.collisions.Collision;
import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.game.animation.Animator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PhysicsEngine implements Runnable {

  private final int SLEEP_TIME = 1000 / GameConstants.calculationsPerSecond;

  private static PhysicsEngine instance;

  private JPanel currentPanel;

  private PhysicsEngine() {

  }

  public static PhysicsEngine getInstance(JPanel currentPanel) {
    if (instance == null) {
      instance = new PhysicsEngine();
    }
    instance.currentPanel = currentPanel;
    return instance;
  }

  public void start() {
    (new Thread(instance)).start();
  }

  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(SLEEP_TIME);
      } catch (InterruptedException e) {
        System.out.println("Program interrupted.");
      }
      handleCollisions();
      updatePositions();
    }
  }

  private static void handleCollisions() {
    ArrayList<GameObject> objects = Game.getInstance().getObjects();
    ArrayList<Collision> collisions = checkCollisions(objects);
    performCollisions(collisions);
  }

  private static void updatePositions() {
    for (GameObject object: Game.getInstance().getObjects()) {
      object.updatePosition();
    }
  }

  private static boolean areColliding(GameObject o1, GameObject o2) {
    double o1_posx = o1.getPosition().getX();
    double o1_posy = o1.getPosition().getY();
    double o2_posx = o2.getPosition().getX();
    double o2_posy = o2.getPosition().getY();
    double distx = (o1.getSize().getX() + o2.getSize().getX()) / 2.0;
    double disty = (o1.getSize().getY() + o2.getSize().getY()) / 2.0;

    if(o1.getShape() == Shape.RECTANGLE && o2.getShape() == Shape.RECTANGLE) {
      return Math.abs(o1_posx - o2_posx) < distx && Math.abs(o1_posy - o2_posy) < disty;
    }else if(o1.getShape() == Shape.CIRCLE && o2.getShape() == Shape.CIRCLE) {
      return Math.hypot(o1_posx - o1_posy, o1_posy - o2_posy) < 2 * distx;
    }else if(o1.getShape() == Shape.CIRCLE) {
      return mixedColliding(o1, o2);
    }else {
      return mixedColliding(o2, o1);
    }
  }

  private static boolean mixedColliding(GameObject circle, GameObject rect) {
    double circle_x = circle.getPosition().getX();
    double circle_y = circle.getPosition().getY();
    double rect_x1 = rect.getPosition().getX() - rect.getSize().getX() / 2.0;
    double rect_x2 = rect.getPosition().getX() + rect.getSize().getX() / 2.0;
    double rect_y1 = rect.getPosition().getY() - rect.getSize().getY() / 2.0;
    double rect_y2 = rect.getPosition().getY() + rect.getSize().getY() / 2.0;
    double radius = circle.getSize().getX() / 2.0;

    if(circle_x < rect_x1) {
      if(circle_y < rect_y1) {
        circle.setReflectionDirection(Direction.UP_LEFT);
        return Math.hypot(circle_x - rect_x1, circle_y - rect_y1) < radius;
      }else if(circle_y > rect_y2) {
        circle.setReflectionDirection(Direction.DOWN_LEFT);
        return Math.hypot(circle_x - rect_x1, circle_y - rect_y2) < radius;
      }else {
        circle.setReflectionDirection(Direction.LEFT);
        return Math.abs(circle_x - rect_x1) < radius;
      }
    }else if(circle_x > rect_x2) {
      if(circle_y < rect_y1) {
        circle.setReflectionDirection(Direction.UP_RIGHT);
        return Math.hypot(circle_x - rect_x2, circle_y - rect_y1) < radius;
      }else if(circle_y > rect_y2) {
        circle.setReflectionDirection(Direction.DOWN_RIGHT);
        return Math.hypot(circle_x - rect_x2, circle_y - rect_y2) < radius;
      }else {
        circle.setReflectionDirection(Direction.RIGHT);
        return Math.abs(circle_x - rect_x2) < radius;
      }
    }else {
      if(circle_y < rect_y1) {
        circle.setReflectionDirection(Direction.UP);
        return Math.abs(circle_y - rect_y1) < radius;
      }else if(circle_y > rect_y2) {
        circle.setReflectionDirection(Direction.DOWN);
        return Math.abs(circle_y - rect_y2) < radius;
      }else {
        circle.setReflectionDirection(Direction.DOWN);
        return true;
      }
    }
  }

  private static ArrayList<Collision> checkCollisions(ArrayList<GameObject> objects) {
    ArrayList<Collision> collisions = new ArrayList<>();
    for (int i = 0; i < objects.size(); i++) {
      for (int j = i + 1; j < objects.size(); j++) {
        if(areColliding(objects.get(i), objects.get(j))) {
          collisions.add(new Collision(objects.get(i), objects.get(j)));
        }
      }
    }
    return collisions;
  }

  private static void performCollisions(List<Collision> collisions) {
    for(Collision collision: collisions) {
      collision.collideObjects();
    }
  }

}
