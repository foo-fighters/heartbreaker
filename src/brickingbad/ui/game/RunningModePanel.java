package brickingbad.ui.game;

import brickingbad.controller.GameController;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.GameObjectListener;
import brickingbad.domain.physics.PhysicsEngine;
import brickingbad.ui.components.UIGameObject;
import brickingbad.ui.components.containers.GameButtonPanel;
import brickingbad.ui.game.animation.Animator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RunningModePanel extends JPanel implements GameObjectListener {

  private static RunningModePanel instance;

  private ArrayList<UIGameObject> uiObjects;

  private GameButtonPanel gameButtonPanel;

  private BufferedImage background;

  private RunningModePanel() {
    Animator.getInstance(this).start();
    PhysicsEngine.getInstance().start();
    setLayout(new BorderLayout());
    uiObjects = new ArrayList<>();
    initUI();
    loadBackgroundImage();
    GameController.getInstance().addObjectListener(this);
    this.addKeyListener(new GameKeyboardListener());
  }

  public static RunningModePanel getInstance() {
    if (instance == null) {
      instance = new RunningModePanel();
    }
    return instance;
  }

  private void initUI() {
    gameButtonPanel = new GameButtonPanel();

    JPanel container = new JPanel(new BorderLayout());
    container.setOpaque(false);
    add(container, BorderLayout.PAGE_START);
    container.add(gameButtonPanel, BorderLayout.LINE_START);
  }

  @Override
  public void addObject(GameObject object) {
    uiObjects.add(new UIGameObject(object, this));
  }

  @Override
  public void removeObject(GameObject object) {
    uiObjects.removeIf(uiGameObject -> uiGameObject.containsObject(object));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(background, 0, 0, null);
    uiObjects.forEach((obj) -> obj.paintComponent(g));
  }

  private void loadBackgroundImage() {
    try {
      this.background = ImageIO.read(new File("resources/sprites/background.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
