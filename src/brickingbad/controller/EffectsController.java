package brickingbad.controller;

import brickingbad.ui.effects.MineBrickExplodeEffect;
import brickingbad.ui.game.RunningModePanel;

public class EffectsController {

  private static EffectsController instance;

  private EffectsController() {

  }

  public static EffectsController getInstance() {
    if (instance == null) {
      instance = new EffectsController();
    }
    return instance;
  }

  public void showMineBrickExplodeEffect(double x, double y) {
    MineBrickExplodeEffect effect = new MineBrickExplodeEffect(x, y);
    RunningModePanel.getInstance().addEffect(effect);
  }

}
