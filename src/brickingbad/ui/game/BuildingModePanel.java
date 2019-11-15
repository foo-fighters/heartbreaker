package brickingbad.ui.game;

import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.GameObjectListener;
import brickingbad.ui.components.UIGameObject;

import javax.swing.*;
import java.util.ArrayList;

public class BuildingModePanel extends JPanel implements GameObjectListener {

  private ArrayList<UIGameObject> uiObjects;

  public BuildingModePanel() {
    uiObjects = new ArrayList<>();

    JLabel tempLabel = new JLabel("THIS IS THE BUILDING MODE!");
    add(tempLabel);
  }

  public void addUIObject(GameObject gameObject) {
    uiObjects.add(new UIGameObject(gameObject, this));
  }

  @Override
  public void addObject(GameObject object) {

  }

  @Override
  public void removeObject(GameObject object) {

  }
}
