package brickingbad.ui.game.effects;

import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.brick.MineBrick;
import brickingbad.ui.components.UIGameObject;

public class EffectsStrategyFactory {

  public static EffectsStrategyFactory instance;

  public static EffectsStrategyFactory getInstance() {
    if (instance == null) {
      instance = new EffectsStrategyFactory();
    }
    return instance;
  }

  public EffectsStrategy getStrategy(UIGameObject object) {
    GameObject domainObject = object.getGameObject();
    if (domainObject instanceof MineBrick) {
      return new MineBrickExplodeEffect();
    } else {
      return new DefaultEffect();
    }

  }

}
