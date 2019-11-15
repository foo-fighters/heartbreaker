package brickingbad.ui.game;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.GameObjectListener;
import brickingbad.ui.components.BBGameButton;
import brickingbad.ui.components.UIGameObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BuildingModePanel extends JPanel implements GameObjectListener, ActionListener {

  private ArrayList<UIGameObject> uiObjects;

  private BBGameButton saveButton;
  private BBGameButton loadButton;
  private BBGameButton pauseButton;
  private BBGameButton quitButton;

  public BuildingModePanel() {
    uiObjects = new ArrayList<>();
    initUI();
    Game.getInstance().addObjectListener(this);
  }

  private void initUI() {
    saveButton = new BBGameButton("SAVE", this);
    loadButton = new BBGameButton("LOAD", this);
    pauseButton = new BBGameButton("PAUSE", this);
    quitButton = new BBGameButton("QUIT", this);

    add(saveButton);
    add(loadButton);
    add(pauseButton);
    add(quitButton);
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

  }

}
