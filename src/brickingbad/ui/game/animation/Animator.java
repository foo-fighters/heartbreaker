package brickingbad.ui.game.animation;

import brickingbad.domain.game.brick.Brick;
import brickingbad.ui.BrickingBadFrame;

import javax.swing.*;
import java.util.Vector;

public class Animator implements Runnable {

  int sleepTime = 100;
  JPanel currentPanel;

  public Animator() {
    currentPanel = BrickingBadFrame.getInstance().getCurrentPanel();
    (new Thread(this)).start();
  }

  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(sleepTime);
      } catch (InterruptedException e) {
        System.out.println("Program interrupted.");
      }
      BrickingBadFrame.getInstance().repaint();
    }
  }

}