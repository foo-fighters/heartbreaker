package brickingbad.ui.menu;

import brickingbad.controller.GameController;
import brickingbad.ui.BrickingBadFrame;
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
      GameController.getInstance().initializeGame();
    } else if (e.getSource().equals(helpButton)) {
      bbFrame.showHelpPanel();
    } else if (e.getSource().equals(loadGameButton)) {
      List<String> saveNames = GameController.getInstance().getSaveNames();

      String name = (String) JOptionPane.showInputDialog(null, "Choose a save: ",
              "Load Game", JOptionPane.QUESTION_MESSAGE, null, // Use
              // default
              // icon
              saveNames.toArray(), // Array of choices
              saveNames.toArray()[1]); // Initial choice

      GameController.getInstance().loadGame(name);
    } else if (e.getSource().equals(exitButton)) {
      System.exit(0);
    }
  }

}
