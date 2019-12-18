package brickingbad.ui.menu;

import brickingbad.ui.DefaultActionable;

import javax.swing.*;

public class LoadPanel extends JPanel implements DefaultActionable {

  private static LoadPanel instance;

  private LoadPanel() {

  }

  public static LoadPanel getInstance() {
    if (instance == null) {
      instance = new LoadPanel();
    }
    return instance;
  }

}
