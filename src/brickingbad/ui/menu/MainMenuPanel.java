package brickingbad.ui.menu;

import brickingbad.ui.components.BBMenuButton;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {

  private static MainMenuPanel instance;

  private MainMenuPanel() { super(); };

  public static MainMenuPanel getInstance() {
    if (instance == null) {
      instance = new MainMenuPanel();
      initialize();
    }
    return instance;
  }

  private static void initialize() {
    BBMenuButton newGameButton = new BBMenuButton("NEW GAME");
    BBMenuButton loadGameButton = new BBMenuButton("LOAD GAME");
    BBMenuButton helpButton = new BBMenuButton("HELP");
    BBMenuButton exitButton = new BBMenuButton("EXIT");

    instance.add(newGameButton);
    instance.add(loadGameButton);
    instance.add(helpButton);
    instance.add(exitButton);
  }

}
