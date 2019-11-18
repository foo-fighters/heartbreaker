package brickingbad.ui.game;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.GameObjectListener;
import brickingbad.ui.components.UIGameObject;

import javax.swing.*;
import java.util.ArrayList;

public class RunningModePanel extends JPanel implements GameObjectListener {

  private static RunningModePanel instance;

  private ArrayList<UIGameObject> uiObjects;

  private RunningModePanel() {
    uiObjects = new ArrayList<>();
    Game.getInstance().addObjectListener(this);
  }

  public static RunningModePanel getInstance() {
    if (instance == null) {
      instance = new RunningModePanel();
    }
    return instance;
  }

  @Override
  public void addObject(GameObject object) {
    uiObjects.add(new UIGameObject(object, this));
  }

  @Override
  public void removeObject(GameObject object) {

  }

}
