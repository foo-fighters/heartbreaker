package brickingbad.ui.game;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.GameObjectListener;
import brickingbad.ui.components.BBGameButton;
import brickingbad.ui.components.containers.BrickCountPanel;
import brickingbad.ui.components.UIGameObject;
import brickingbad.ui.components.containers.GameButtonPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BuildingModePanel extends JPanel implements GameObjectListener, ActionListener {

  private ArrayList<UIGameObject> uiObjects;

  private GameButtonPanel gameButtonPanel;
  private BrickCountPanel brickCountPanel;

  private BufferedImage background;

  public BuildingModePanel() {
    setLayout(new BorderLayout());
    uiObjects = new ArrayList<>();
    initUI();
    loadBackgroundImage();
    Game.getInstance().addObjectListener(this);
  }

  private void initUI() {
    gameButtonPanel = new GameButtonPanel();
    brickCountPanel = new BrickCountPanel(this);

    JPanel container = new JPanel(new BorderLayout());
    container.setOpaque(false);
    add(container, BorderLayout.PAGE_START);
    container.add(gameButtonPanel, BorderLayout.LINE_START);
    container.add(brickCountPanel, BorderLayout.LINE_END);
  }

  @Override
  public void addObject(GameObject object) {
    uiObjects.add(new UIGameObject(object, this));
  }

  @Override
  public void removeObject(GameObject object) {

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // if source is submit button
    //  call game controller -> create bricks
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(background, 0, 0, null);
  }

  private void loadBackgroundImage() {
    try {
      this.background = ImageIO.read(new File("resources/sprites/background.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
