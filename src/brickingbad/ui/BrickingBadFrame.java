package brickingbad.ui;

import brickingbad.controller.GameController;
import brickingbad.domain.game.GameConstants;
import brickingbad.ui.components.Panel;
import brickingbad.ui.game.BuildingModePanel;
import brickingbad.ui.menu.HelpPanel;
import brickingbad.ui.game.RunningModePanel;
import brickingbad.ui.menu.LoadPanel;
import brickingbad.ui.menu.MainMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
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

    File font_file = new File("resources/fonts/ARCADECLASSIC.TTF");

    setTitle("Bricking Bad");
    getContentPane().setPreferredSize(new Dimension(GameConstants.screenWidth, GameConstants.screenHeight));
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panels = new JPanel(new CardLayout());
    add(panels);

    setVisible(true);
    setResizable(false);

    try{
      Font font = Font.createFont(Font.TRUETYPE_FONT, font_file);
      Font sizedFont = font.deriveFont(14f);
      setUIFont (new javax.swing.plaf.FontUIResource(sizedFont));
    } catch(Exception e) {
      System.out.println("Problem importing font");
    }
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

  public Panel getCurrentPanelName() {
    return currentPanelName;
  }

  private static void showPanel(Panel panel) {
    CardLayout layout = (CardLayout) panels.getLayout();
    layout.show(panels, panel.name());
    panelsMap.get(panel).requestFocus();
  }

  public static void setUIFont (javax.swing.plaf.FontUIResource f){
    java.util.Enumeration keys = UIManager.getDefaults().keys();
    while (keys.hasMoreElements()) {
      Object key = keys.nextElement();
      Object value = UIManager.get (key);
      if (value instanceof javax.swing.plaf.FontUIResource)
        UIManager.put (key, f);
    }
  }

  public void showSaveDialog() {
    String name = JOptionPane.showInputDialog("Save name: ");
    if (name != null) {
      GameController.getInstance().saveGame(name);
    }
  }

  public void showLoadDialog() {
    List<String> saveNames = GameController.getInstance().getSaveNames();

    String name = (String) JOptionPane.showInputDialog(null, "Choose a save: ",
            "Load Game", JOptionPane.QUESTION_MESSAGE, null, // Use
            // default
            // icon
            saveNames.toArray(), // Array of choices
            saveNames.toArray()[0]); // Initial choice

    if (name != null) {
      GameController.getInstance().initializeGame();
      GameController.getInstance().loadGame(name);
      BrickingBadFrame.getInstance().showRunningModePanel();
    }
  }

}
