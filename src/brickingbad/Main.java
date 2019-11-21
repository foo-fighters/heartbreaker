package brickingbad;

import brickingbad.ui.BrickingBadFrame;

import javax.swing.*;

public class Main {

  public static void main(String[] args) {
    // Runs the code on the AWT thread. Which lets you modify the GUI from other threads.
    SwingUtilities.invokeLater(() ->
      BrickingBadFrame.getInstance());
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
