package brickingbad.ui.game.animation;

import brickingbad.domain.game.GameConstants;
import brickingbad.ui.BrickingBadFrame;

import javax.swing.*;

public class Animator implements Runnable {

  private final int SLEEP_TIME = 1000 / GameConstants.framesPerSecond;

  private static Animator instance;
  private static boolean running = true;

  private Animator() {
  }

  public static Animator getInstance(JPanel currentPanel) {
    instance = getInstance();
    return instance;
  }

  public static Animator getInstance() {
    if (instance == null) {
      instance = new Animator();
    }
    return instance;
  }

  public void togglePauseResume() {
    if (running) {
      System.out.println("Animator paused.");
      running = false;
    } else {
      running = true;
      System.out.println("Animator resumed.");
    }
  }

  public void pause(){
    running = false;
  }

  public void resume() {
    running = true;
  }

  public void start() {
    running = true;
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
      if (running) {
        try {
          BrickingBadFrame.getInstance().getCurrentPanel().repaint();
        } catch (NullPointerException e) {
          // do nothing :)
        }
      }
    }
  }

  public boolean isRunning() {
    return running;
  }

}