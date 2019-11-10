package brickingbad.ui.menu;

import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.components.BBMenuButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel implements ActionListener {

  private static MainMenuPanel instance;

  private final BrickingBadFrame bbFrame = BrickingBadFrame.getInstance();

  private static BBMenuButton newGameButton;
  private static BBMenuButton loadGameButton;
  private static BBMenuButton helpButton;
  private static BBMenuButton exitButton;

  private MainMenuPanel() {  };

  public static MainMenuPanel getInstance() {
    if (instance == null) {
      instance = new MainMenuPanel();
      initialize();
    }
    return instance;
  }

  private static void initialize() {
    newGameButton = new BBMenuButton("NEW GAME");
    loadGameButton = new BBMenuButton("LOAD GAME");
    helpButton = new BBMenuButton("HELP");
    exitButton = new BBMenuButton("EXIT");

    instance.add(newGameButton);
    instance.add(loadGameButton);
    instance.add(helpButton);
    instance.add(exitButton);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(newGameButton)) {
      bbFrame.showBuildingModePanel();
    } else if (e.getSource().equals(helpButton)) {
      bbFrame.showHelpPanel();
    } else if (e.getSource().equals(loadGameButton)) {
      bbFrame.showLoadGamePanel();
    }
  }

}
