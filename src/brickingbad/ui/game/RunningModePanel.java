package brickingbad.ui.game;

import brickingbad.controller.GameController;
import brickingbad.domain.game.GameConstants;
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
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class RunningModePanel extends JPanel implements GameObjectListener {

  private static RunningModePanel instance;

  // a CopyOnArrayList generates a new copy of the ArrayList when there is a write operation, making it thread-safe
  // this is more costly but since the number of traversals heavily outnumber the number or writes in our case,
  //    and we are working with multiple threads, it is a logical choice.
  private CopyOnWriteArrayList<UIGameObject> uiObjects;

  private GameButtonPanel gameButtonPanel;

  private BufferedImage background;

  private RunningModePanel() {
    Animator.getInstance(this).start();
    PhysicsEngine.getInstance().start();
    setLayout(null);
    uiObjects = new CopyOnWriteArrayList<>();
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
    container.setSize(GameConstants.screenWidth, (int)GameConstants.menuAreaHeight);
    container.setOpaque(true);
    container.setBackground(Color.darkGray);
    add(container);
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
  protected void paintComponent(Graphics g) throws ConcurrentModificationException {
    super.paintComponent(g);
    g.drawImage(background, 0, 0, null);
    for (Iterator<UIGameObject> iterator = uiObjects.iterator(); iterator.hasNext(); ) {
      UIGameObject object = iterator.next();
      object.paintComponent(g);
    }
  }

  private void loadBackgroundImage() {
    try {
      this.background = ImageIO.read(new File("resources/sprites/background.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
