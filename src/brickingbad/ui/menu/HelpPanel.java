package brickingbad.ui.menu;

import brickingbad.ui.DefaultActionable;

import javax.swing.*;

public class HelpPanel extends JPanel implements DefaultActionable {

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
