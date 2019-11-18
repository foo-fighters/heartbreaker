package brickingbad.ui.menu;

import javax.swing.*;

public class LoadPanel extends JPanel {

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
