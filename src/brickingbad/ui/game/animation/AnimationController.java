package brickingbad.ui.game.animation;

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

  public static void showMineBrickExplodeEffect() {

  }

}
