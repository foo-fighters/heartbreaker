package brickingbad.ui.menu;

import brickingbad.controller.GameController;
import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.UIController;
import brickingbad.ui.components.BBMenuButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainMenuPanel extends JPanel implements ActionListener {

  private static MainMenuPanel instance;

  private final BrickingBadFrame bbFrame = BrickingBadFrame.getInstance();

  private static BBMenuButton newGameButton;
  private static BBMenuButton loadGameButton;
  private static BBMenuButton helpButton;
  private static BBMenuButton exitButton;

  private MainMenuPanel() {
    newGameButton = new BBMenuButton("NEW GAME", this);
    loadGameButton = new BBMenuButton("LOAD GAME", this);
    helpButton = new BBMenuButton("HELP", this);
    exitButton = new BBMenuButton("EXIT", this);

    add(newGameButton);
    add(loadGameButton);
    add(helpButton);
    add(exitButton);
  }

  public static MainMenuPanel getInstance() {
    if (instance == null) {
      instance = new MainMenuPanel();
    }
    return instance;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(newGameButton)) {
      bbFrame.showBuildingModePanel();
      GameController.getInstance().initializeGame(false);
    } else if (e.getSource().equals(helpButton)) {
      bbFrame.showHelpPanel();
    } else if (e.getSource().equals(loadGameButton)) {
      bbFrame.showLoadDialog();
      UIController.getInstance().resumeGameIfPaused();
    } else if (e.getSource().equals(exitButton)) {
      System.exit(0);
    }
  }

}
