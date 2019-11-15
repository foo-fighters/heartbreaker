package brickingbad.ui.game;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.GameObjectListener;
import brickingbad.ui.components.UIGameObject;

import javax.swing.*;
import java.util.ArrayList;

public class BuildingModePanel extends JPanel implements GameObjectListener {

  private ArrayList<UIGameObject> uiObjects;

  public BuildingModePanel() {
    uiObjects = new ArrayList<>();
    Game.getInstance().addObjectListener(this);

    JLabel tempLabel = new JLabel("THIS IS THE BUILDING MODE!");
    add(tempLabel);
  }

  @Override
  public void addObject(GameObject object) {
    uiObjects.add(new UIGameObject(object, this));
  }

  @Override
  public void removeObject(GameObject object) {

  }
}
