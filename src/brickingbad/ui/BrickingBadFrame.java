package brickingbad.ui;

import brickingbad.domain.game.GameConstants;
import brickingbad.ui.components.Panel;
import brickingbad.ui.game.BuildingModePanel;
import brickingbad.ui.menu.HelpPanel;
import brickingbad.ui.game.RunningModePanel;
import brickingbad.ui.menu.LoadPanel;
import brickingbad.ui.menu.MainMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BrickingBadFrame extends JFrame {

  private static BrickingBadFrame instance;

  private static JPanel panels;

  private static Panel currentPanelName;
  private static Map<Panel, JPanel> panelsMap;

  public static BrickingBadFrame getInstance() {
    if (instance == null) {
      instance = new BrickingBadFrame();
      initializePanels();
    }
    return instance;
  }

  private BrickingBadFrame() {
    setTitle("Bricking Bad");
    setSize(GameConstants.screenWidth, GameConstants.screenHeight);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panels = new JPanel(new CardLayout());
    add(panels);

    setVisible(true);
    setResizable(false);
  }

  private static void initializePanels() {
    currentPanelName = Panel.MAIN_MENU;
    panelsMap = new HashMap<>();
    panelsMap.put(Panel.MAIN_MENU, MainMenuPanel.getInstance());
    panelsMap.put(Panel.BUILDING_MODE, BuildingModePanel.getInstance());
    panelsMap.put(Panel.RUNNING_MODE, RunningModePanel.getInstance());
    panelsMap.put(Panel.LOAD_GAME, LoadPanel.getInstance());
    panelsMap.put(Panel.HELP, HelpPanel.getInstance());

    panelsMap.forEach((panelEnum, panel) -> {
      panels.add(panel, panelEnum.name());
    });

    showPanel(Panel.MAIN_MENU);
  }

  public void showBuildingModePanel() {
    currentPanelName = Panel.BUILDING_MODE;
    showPanel(currentPanelName);
  }

  public void showRunningModePanel() {
    currentPanelName = Panel.RUNNING_MODE;
    showPanel(currentPanelName);
  }

  public void showHelpPanel() {
    currentPanelName = Panel.HELP;
    showPanel(currentPanelName);
  }

  public void showLoadGamePanel() {
    currentPanelName = Panel.LOAD_GAME;
    showPanel(currentPanelName);
  }

  public void showMainMenuPanel() {
    currentPanelName = Panel.MAIN_MENU;
    showPanel(currentPanelName);
  }

  public JPanel getCurrentPanel() {
    return panelsMap.get(currentPanelName);
  }

  private static void showPanel(Panel panel) {
    CardLayout layout = (CardLayout) panels.getLayout();
    layout.show(panels, panel.name());
    panelsMap.get(panel).requestFocus();
  }

}
