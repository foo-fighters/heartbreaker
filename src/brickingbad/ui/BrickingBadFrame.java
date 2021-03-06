package brickingbad.ui;

import brickingbad.audio.AudioController;
import brickingbad.controller.GameController;
import brickingbad.controller.SaveController;
import brickingbad.domain.game.GameConstants;
import brickingbad.services.Adapter;
import brickingbad.services.AdapterHandler;
import brickingbad.ui.components.BBButton;
import brickingbad.ui.components.Panel;
import brickingbad.ui.game.BuildingModePanel;
import brickingbad.ui.game.animation.Animator;
import brickingbad.ui.menu.HelpPanel;
import brickingbad.ui.game.RunningModePanel;
import brickingbad.ui.menu.LandingPanel;
import brickingbad.ui.menu.LoadPanel;
import brickingbad.ui.menu.MainMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
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
    instance.setDefaultButton();
    return instance;
  }

  private BrickingBadFrame() {
    AudioController.getInstance().playMenuMusic();
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
      Font sizedFont = font.deriveFont(12f);
      setUIFont (new javax.swing.plaf.FontUIResource(sizedFont));
    } catch(Exception e) {
      System.out.println("Problem importing font");
    }
  }

  private static void initializePanels() {

    currentPanelName = Panel.LANDING;
    panelsMap = new HashMap<>();
    panelsMap.put(Panel.MAIN_MENU, MainMenuPanel.getInstance());
    panelsMap.put(Panel.BUILDING_MODE, BuildingModePanel.getInstance());
    panelsMap.put(Panel.RUNNING_MODE, RunningModePanel.getInstance());
    panelsMap.put(Panel.LOAD_GAME, LoadPanel.getInstance());
    panelsMap.put(Panel.HELP, HelpPanel.getInstance());
    panelsMap.put(Panel.LANDING, LandingPanel.getInstance());

    panelsMap.forEach((panelEnum, panel) -> {
      panels.add(panel, panelEnum.name());
    });

    showPanel(Panel.LANDING);
  }

  public void showBuildingModePanel() {
    currentPanelName = Panel.BUILDING_MODE;
    showPanel(currentPanelName);
    setDefaultButton();
  }

  public void showRunningModePanel() {
    currentPanelName = Panel.RUNNING_MODE;
    UIController.getInstance().resumeUI();
    showPanel(currentPanelName);
  }

  public void showHelpPanel() {
    currentPanelName = Panel.HELP;
    showPanel(currentPanelName);
  }

  public void showMainMenuPanel() {
    currentPanelName = Panel.MAIN_MENU;
    UIController.getInstance().pauseUI();
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

  public void showYouAreDeadDialog() {
    JOptionPane.showMessageDialog(this, "All lives are lost!");
    showMainMenuPanel();
    AudioController.getInstance().playMenuMusic();
  }

  public void showSaveDialog() {
    if (Animator.getInstance().isRunning() &&
        getCurrentPanelName().equals(Panel.RUNNING_MODE)) {
      showPauseWarning();
    } else {
      Adapter adapter = AdapterHandler.getCurrentAdapter();
      String name = JOptionPane.showInputDialog("Save name: ");
      System.out.println(name);
      if (name != null) {
        boolean inRunningMode = getCurrentPanelName().equals(Panel.RUNNING_MODE);
        SaveController.getInstance().adapt(adapter).saveGame(name, inRunningMode);
      }
    }
  }

  public void showLoadDialog() {
    if (Animator.getInstance().isRunning() &&
        getCurrentPanelName().equals(Panel.RUNNING_MODE)) {
      showPauseWarning();
    } else {
      Adapter adapter = AdapterHandler.getCurrentAdapter();

      List<String> saveNames = SaveController.getInstance().adapt(adapter).getSaveNames();
      if (saveNames.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No saved games found for this account.");
      } else {
        String name = (String) JOptionPane.showInputDialog(null, "Choose a save: ",
                "Load Game", JOptionPane.QUESTION_MESSAGE, null, // Use
                // default
                // icon
                saveNames.toArray(), // Array of choices
                saveNames.toArray()[0]); // Initial choice

        if (name != null) {
          GameController.getInstance().initializeGame(true);
          Animator.getInstance().start();
          boolean loadRunningModePanel = SaveController.getInstance().adapt(adapter).loadGame(name);
          GameController.getInstance().setupGrid();
          if (loadRunningModePanel) {
            showRunningModePanel();
            UIController.getInstance().resumeUI();
          } else {
            showBuildingModePanel();
          }
        }
      }
    }
  }

  public void showGodModeDialog() {
    String answer = (String) JOptionPane.showInputDialog("What is the answer to life, universe and everything?");
    if (answer != null && answer.equals("42")) {
      RunningModePanel.getInstance().invokeGodMode();
      GameController.getInstance().invokeGodMode();
    } else {
      JOptionPane.showMessageDialog(getInstance(), "NO!");
    }
  }

  public void showWonDialog() {
      JOptionPane.showMessageDialog(this, "Congratz YOU HAVE WON" );
      showMainMenuPanel();
      AudioController.getInstance().playMenuMusic();
    }

  private void showPauseWarning() {
    JOptionPane.showMessageDialog(this, "The game should be paused for save/load. ");
  }

  private void setDefaultButton(){
      DefaultActionable bBButtonPanel = ((DefaultActionable) getCurrentPanel());
      if (bBButtonPanel == null){
        return;
      }
      BBButton defaultButton = bBButtonPanel.getDefaultButton();

      if (defaultButton == null){
        return;
      }
      rootPane.setDefaultButton(defaultButton);
  }

}
