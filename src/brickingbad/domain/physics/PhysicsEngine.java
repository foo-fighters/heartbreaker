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
    private static boolean running;

    private PhysicsEngine() {

    }

    public static PhysicsEngine getInstance(JPanel currentPanel) {
        if (instance == null) {
            instance = new PhysicsEngine();
        }
        instance.currentPanel = currentPanel;
        return instance;
    }

  public static PhysicsEngine getInstance() {
    if (instance == null) {
      instance = new PhysicsEngine();
      running = true;
    }
    return instance;
  }

  public void start() {
    (new Thread(instance)).start();
  }

  public void togglePauseResume() {
    if (running) {
      System.out.println("Physics engine paused.");
      running = false;
    } else {
      System.out.println("Physics engine resumed.");
      running = true;
    }
  }

  public void resumeIfPaused() {
    if (!running) {
      togglePauseResume();
    }
  }

  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(SLEEP_TIME);
      } catch (InterruptedException e) {
        System.out.println("Program interrupted.");
      }
      if (running) {
        handleCollisions();
        updatePositions();
      }
    }
  }

    private static void handleCollisions () {
      ArrayList<GameObject> objects = Game.getInstance().getObjects();
      ArrayList<Collision> collisions = checkCollisions(objects);
      performCollisions(collisions);
    }

    private static void updatePositions () {
      for (GameObject object : Game.getInstance().getObjects()) {
        if (object != null) {
          object.updatePosition();
        }
      }
    }

    private static boolean areColliding (GameObject o1, GameObject o2){
      double o1_posx = o1.getPosition().getX();
      double o1_posy = o1.getPosition().getY();
      double o2_posx = o2.getPosition().getX();
      double o2_posy = o2.getPosition().getY();
      double distx = (o1.getSize().getX() + o2.getSize().getX()) / 2.0;
      double disty = (o1.getSize().getY() + o2.getSize().getY()) / 2.0;

      if (o1.getShape() == Shape.RECTANGLE && o2.getShape() == Shape.RECTANGLE) {
        return Math.abs(o1_posx - o2_posx) < distx && Math.abs(o1_posy - o2_posy) < disty;
      } else if (o1.getShape() == Shape.CIRCLE && o2.getShape() == Shape.CIRCLE) {
        return Math.hypot(o1_posx - o1_posy, o1_posy - o2_posy) < 2 * distx;
      } else if (o1.getShape() == Shape.CIRCLE) {
        return mixedColliding(o1, o2);
      } else {
        return mixedColliding(o2, o1);
      }
    }

    private static boolean mixedColliding (GameObject circle, GameObject rect){
      double circle_x = circle.getPosition().getX();
      double circle_y = circle.getPosition().getY();
      double rect_x = rect.getPosition().getX();
      double rect_y = rect.getPosition().getY();
      double rect_angle = Math.toRadians(rect.getAngle());
      double rect_half_x = rect.getSize().getX() / 2.0;
      double rect_half_y = rect.getSize().getY() / 2.0;
      Vector rect_x1 = new Vector(rect_x - Math.cos(rect_angle) * rect_half_x, rect_y + Math.sin(rect_angle) * rect_half_x);
      Vector rect_x2 = new Vector(rect_x + Math.cos(rect_angle) * rect_half_x, rect_y - Math.sin(rect_angle) * rect_half_x);
      Vector rect_y1 = new Vector(rect_x + Math.sin(rect_angle) * rect_half_y, rect_y - Math.cos(rect_angle) * rect_half_y);
      Vector rect_y2 = new Vector(rect_x - Math.sin(rect_angle) * rect_half_y, rect_y + Math.cos(rect_angle) * rect_half_y);
      Line line_x1 = new Line(rect_x1, Math.PI / 2.0 - rect_angle);
      Line line_x2 = new Line(rect_x2, Math.PI / 2.0 - rect_angle);
      Line line_y1 = new Line(rect_y1, -rect_angle);
      Line line_y2 = new Line(rect_y2, -rect_angle);
      Vector point_x1y1 = line_x1.intersection(line_y1);
      Vector point_x1y2 = line_x1.intersection(line_y2);
      Vector point_x2y1 = line_x2.intersection(line_y1);
      Vector point_x2y2 = line_x2.intersection(line_y2);

      double radius = circle.getSize().getX() / 2.0;
      if (line_x1.isVectorLeft(circle.getPosition())) {
        if (line_y1.isVectorBelow(circle.getPosition())) {
          if (Math.hypot(circle_x - point_x1y1.getX(), circle_y - point_x1y1.getY()) < radius) {
            circle.setReflectionDirection(Direction.UP_LEFT);
            return true;
          } else return false;
        } else if (line_y2.isVectorAbove(circle.getPosition())) {
          if (Math.hypot(circle_x - point_x1y2.getX(), circle_y - point_x1y2.getY()) < radius) {
            circle.setReflectionDirection(Direction.UP);
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
            circle.setReflectionDirection(Direction.UP_RIGHT);
            return true;
          } else return false;
        } else if (line_y2.isVectorAbove(circle.getPosition())) {
          if (Math.hypot(circle_x - point_x2y2.getX(), circle_y - point_x2y2.getY()) < radius) {
            circle.setReflectionDirection(Direction.DOWN_RIGHT);
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
            circle.setReflectionDirection(Direction.UP);
            return true;
          } else return false;
        } else if (line_y2.isVectorAbove(circle.getPosition())) {
          if (line_y2.distanceToLine(circle.getPosition()) < radius) {
            circle.setReflectionDirection(Direction.DOWN);
            return true;
          } else return false;
        } else {
          circle.setReflectionDirection(Direction.DOWN);
          return true;
        }
      }
    }

    private static ArrayList<Collision> checkCollisions (ArrayList<GameObject> objects) {
      ArrayList<Collision> collisions = new ArrayList<>();
      for (int i = 0; i < objects.size(); i++) {
        for (int j = i + 1; j < objects.size(); j++) {
          if (areColliding(objects.get(i), objects.get(j))) {
            collisions.add(new Collision(objects.get(i), objects.get(j)));
          }
        }
      }
      return collisions;
    }

    private static void performCollisions (List < Collision > collisions) {
      for (Collision collision : collisions) {
        collision.collideObjects();
      }
    }

}
