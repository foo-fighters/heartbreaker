package brickingbad.domain.physics;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameObject;
import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.game.animation.Animator;

import javax.swing.*;
import java.util.List;

public class PhysicsEngine implements Runnable {

  private final int SLEEP_TIME = 1000 / GameConstants.calculationsPerSecond;

  private static PhysicsEngine instance;
  private static boolean running;

  private PhysicsEngine() {

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

  private static void handleCollisions() {

  }

  private static void updatePositions() {
    for (GameObject object: Game.getInstance().getObjects()) {
      if (object != null){
        object.updatePosition();
      }
    }
  }

  private static boolean areColliding(GameObject o1, GameObject o2) {
    return true;
  }

  private static List<List<GameObject>> checkCollisions(List<GameObject> objects) {
    return null;
  }

  private static void performCollisions(List<List<GameObject>> collidingPairs) {

  }

}
