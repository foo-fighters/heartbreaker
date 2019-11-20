package brickingbad.domain.physics;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameObject;
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
  }

  private static void updatePositions() {
    for (GameObject object: Game.getInstance().getObjects()) {
      object.updatePosition();
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
