package brickingbad.ui;

import brickingbad.ui.menu.MainMenuPanel;

import javax.swing.*;
import java.awt.*;

public class BrickingBadFrame extends JFrame {

  private JPanel panels;

  public BrickingBadFrame() {
    setTitle("Bricking Bad");
    setSize(1200, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panels = new JPanel(new CardLayout());
    add(panels);

    panels.add(MainMenuPanel.getInstance(), "MENUPANEL");

    setVisible(true);
  }

  private void showPanel(String name) {
    CardLayout layout = (CardLayout) panels.getLayout();
    layout.show(panels, name);
  }

}
