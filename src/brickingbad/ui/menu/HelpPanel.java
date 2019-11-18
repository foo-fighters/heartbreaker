package brickingbad.ui.menu;

import javax.swing.*;

public class HelpPanel extends JPanel {

  private static HelpPanel instance;

  private HelpPanel() {

  }

  public static HelpPanel getInstance() {
    if (instance == null) {
      instance = new HelpPanel();
    }
    return instance;
  }

}
