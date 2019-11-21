package brickingbad.ui.game.animation;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.brick.Brick;
import brickingbad.ui.BrickingBadFrame;

import javax.swing.*;
import java.util.Vector;

public class Animator implements Runnable {

  private final int SLEEP_TIME = 1000 / GameConstants.framesPerSecond;

  private static Animator instance;
  private static Thread thread;
  private boolean running;

  private Animator() {
  }

  public static Animator getInstance() {
    if (instance == null) {
      instance = new Animator();
    }
    return instance;
  }

  public void start() {
    running = true;
    thread = new Thread(instance);
    System.out.println(thread.getState());
    thread.start();
  }

  public void stop() {
    running = false;
  }

  @Override
  public void run() {
    while (running) {
      try {
        Thread.sleep(SLEEP_TIME);
      } catch (InterruptedException e) {
        System.out.println("Program interrupted.");
      }
      BrickingBadFrame.getInstance().repaint();
    }
  }

}