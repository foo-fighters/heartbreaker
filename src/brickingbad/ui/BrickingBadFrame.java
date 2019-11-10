package brickingbad.ui;

import brickingbad.ui.game.BuildingModePanel;
import brickingbad.ui.menu.MainMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrickingBadFrame extends JFrame   {

  private static BrickingBadFrame instance;

  private static JPanel panels;

  public static BrickingBadFrame getInstance() {
    if (instance == null) {
      instance = new BrickingBadFrame();
      initialize();
    }
    return instance;
  }

  private BrickingBadFrame() {
    setTitle("Bricking Bad");
    setSize(1200, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panels = new JPanel(new CardLayout());
    add(panels);

    setVisible(true);
  }

  private static void initialize() {
    panels.add(MainMenuPanel.getInstance(), "MENU_PANEL");
    panels.add(BuildingModePanel.getInstance(), "BUILDING_MODE_PANEL");
  }

  public void showRunningModePanel() {
    showPanel("RUNNING_MODE_PANEL");
  }

  public void showBuildingModePanel() {
    showPanel("BUILDING_MODE_PANEL");
  }

  public void showHelpPanel() {
    showPanel("HELP_PANEL");
  }

  public void showLoadGamePanel() {
    showPanel("LOAD_GAME_PANEL");
  }

  private void showPanel(String name) {
    CardLayout layout = (CardLayout) panels.getLayout();
    layout.show(panels, name);
  }

}
