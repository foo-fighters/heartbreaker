package brickingbad.controller;

import brickingbad.domain.game.brick.MineBrick;
import brickingbad.ui.components.UIGameObject;
import brickingbad.ui.game.RunningModePanel;

import java.util.concurrent.CopyOnWriteArrayList;

public class AnimationController {

  private static AnimationController instance;

  private AnimationController() {

  }

  public static AnimationController getInstance() {
    if (instance == null) {
      instance = new AnimationController();
    }
    return instance;
  }

  public void showMineBrickExplodeEffect(MineBrick brick) {
    CopyOnWriteArrayList<UIGameObject> objects = RunningModePanel.getInstance().getUIObjects();
    for (UIGameObject object : objects) {
      if (object.getGameObject().equals(brick)) {
        object.activateEffect();
        try {
          Thread.sleep(10);
        } catch (Exception e) {
          System.out.println("sldkfhjsdlfkjsdlkfjsdlkfj");
        }
        object.deactivateEffect();
      }
    }
  }

}
