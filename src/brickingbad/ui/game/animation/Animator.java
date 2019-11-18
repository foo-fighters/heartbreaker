package brickingbad.ui.game.animation;

import brickingbad.domain.game.brick.Brick;
import brickingbad.ui.BrickingBadFrame;

import javax.swing.*;
import java.util.Vector;

public class Animator implements Runnable {

  private final int SLEEP_TIME = 100;

  private static Animator instance;

  private JPanel currentPanel;

  private Animator() {

  }

  public static Animator getInstance(JPanel currentPanel) {
    if (instance == null) {
      instance = new Animator();
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
      BrickingBadFrame.getInstance().repaint();
    }
  }

}