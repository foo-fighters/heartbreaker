package brickingbad.ui;

import brickingbad.ui.components.Panel;
import brickingbad.ui.game.BuildingModePanel;
import brickingbad.ui.menu.MainMenuPanel;

import javax.swing.*;
import java.awt.*;

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
    panels.add(MainMenuPanel.getInstance(), brickingbad.ui.components.Panel.MAIN_MENU.name());
    panels.add(BuildingModePanel.getInstance(), brickingbad.ui.components.Panel.BUILDING_MODE.name());
  }

  public void showBuildingModePanel() {
    showPanel(brickingbad.ui.components.Panel.BUILDING_MODE);
  }

  public void showRunningModePanel() {
    showPanel(brickingbad.ui.components.Panel.RUNNING_MODE);
  }

  public void showHelpPanel() {
    showPanel(brickingbad.ui.components.Panel.HELP);
  }

  public void showLoadGamePanel() {
    showPanel(brickingbad.ui.components.Panel.LOAD_GAME);
  }

  private void showPanel(Panel panel) {
    CardLayout layout = (CardLayout) panels.getLayout();
    layout.show(panels, panel.name());
  }

}
