package brickingbad.ui.game;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.GameObjectListener;
import brickingbad.ui.components.BBGameButton;
import brickingbad.ui.components.containers.BrickCountPanel;
import brickingbad.ui.components.UIGameObject;
import brickingbad.ui.components.containers.GameButtonPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BuildingModePanel extends JPanel implements GameObjectListener, ActionListener {

  private ArrayList<UIGameObject> uiObjects;

  private GameButtonPanel gameButtonPanel;
  private BrickCountPanel brickCountPanel;

  public BuildingModePanel() {
    setLayout(new BorderLayout());
    uiObjects = new ArrayList<>();
    initUI();
    Game.getInstance().addObjectListener(this);
  }

  private void initUI() {
    gameButtonPanel = new GameButtonPanel();
    brickCountPanel = new BrickCountPanel(this);

    JPanel container = new JPanel(new BorderLayout());
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

}
