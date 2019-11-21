package brickingbad.ui.game.animation;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.brick.Brick;
import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.game.GameKeyboardListener;

import javax.swing.*;
import java.util.Vector;

public class Animator implements Runnable {

  private final int SLEEP_TIME = 1000 / GameConstants.framesPerSecond;

  private static Animator instance;
  private static boolean running = true;

  private static JPanel panel;

  private Animator() {
  }

  public static Animator getInstance(JPanel currentPanel) {
    instance = getInstance();
    instance.setPanel(currentPanel);
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
      panel.addKeyListener(new GameKeyboardListener());
      System.out.println("Animator resumed.");
    }
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
        BrickingBadFrame.getInstance().repaint();
      }
    }
  }

  public void setPanel(JPanel panel) {
    this.panel = panel;
  }

}