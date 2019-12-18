package brickingbad.ui.menu;

import brickingbad.controller.GameController;
import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.UIController;
import brickingbad.ui.components.BBMenuButton;
import brickingbad.ui.components.UIGameObject;
import brickingbad.ui.game.animation.Animation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainMenuPanel extends JPanel implements ActionListener {

  private static MainMenuPanel instance;

  private final BrickingBadFrame bbFrame = BrickingBadFrame.getInstance();

  private static BBMenuButton newGameButton;
  private static BBMenuButton loadGameButton;
  private static BBMenuButton helpButton;
  private static BBMenuButton exitButton;

  private BufferedImage background;

  private MainMenuPanel() {
    newGameButton = new BBMenuButton("NEW GAME", this);
    loadGameButton = new BBMenuButton("LOAD GAME", this);
    helpButton = new BBMenuButton("HELP", this);
    exitButton = new BBMenuButton("EXIT", this);

    add(newGameButton);
    add(loadGameButton);
    add(helpButton);
    add(exitButton);

    loadBackgroundImage("resources/sprites/background.png");
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

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
  }

  private void loadBackgroundImage(String path) {
    try {
      this.background = ImageIO.read(new File(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
