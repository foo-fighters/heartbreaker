package brickingbad.ui;

import brickingbad.ui.components.Panel;
import brickingbad.ui.game.BuildingModePanel;
import brickingbad.ui.menu.HelpPanel;
import brickingbad.ui.game.RunningModePanel;
import brickingbad.ui.menu.LoadPanel;
import brickingbad.ui.menu.MainMenuPanel;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BrickingBadFrame extends JFrame {

  private static BrickingBadFrame instance;

  private static JPanel panels;

  private static Panel currentPanelName;
  private static Map<String, JPanel> panelsMap;

  public static BrickingBadFrame getInstance() {
    if (instance == null) {
      instance = new BrickingBadFrame();
      initializePanels();
    }
    return instance;
  }

  private BrickingBadFrame() {
    setTitle("Bricking Bad");
    setSize(1280, 720);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panels = new JPanel(new CardLayout());
    add(panels);

    setVisible(true);
    setResizable(false);
  }

  private static void initializePanels() {
    currentPanelName = Panel.MAIN_MENU;
    panelsMap = new HashMap<>();
    panelsMap.put(Panel.MAIN_MENU.name(), new MainMenuPanel());
    panelsMap.put(Panel.BUILDING_MODE.name(), BuildingModePanel.getInstance());
    panelsMap.put(Panel.RUNNING_MODE.name(), new RunningModePanel());
    panelsMap.put(Panel.LOAD_GAME.name(), new LoadPanel());
    panelsMap.put(Panel.HELP.name(), new HelpPanel());

    panelsMap.forEach((panelName, panel) -> {
      panels.add(panel, panelName);
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
    return panelsMap.get(currentPanelName.name());
  }

  private static void showPanel(Panel panel) {
    CardLayout layout = (CardLayout) panels.getLayout();
    layout.show(panels, panel.name());
  }

}
