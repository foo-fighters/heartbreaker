package brickingbad.controller;

import brickingbad.domain.game.GameLogic;
import brickingbad.domain.game.gameobjects.GameObjectFactory;
import brickingbad.domain.game.Level;
import brickingbad.domain.game.listeners.AnimationListener;
import brickingbad.domain.game.listeners.GameListener;
import brickingbad.domain.game.WrapperContent;
import brickingbad.domain.physics.Direction;

public class GameController {

    private static GameController instance;

    private GameController() {
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void initializeGame(boolean fromSave) {
        Level.getInstance().initialize(fromSave);
    }

    public void startGame() {
        Level.getInstance().play();
    }

    public void launchBalls() {
        Level.getInstance().getPaddle().launchBalls();
    }

    public void startPaddleMove(Direction direction) {
        Level.getInstance().getPaddle().startMove(direction);
    }

    public void endPaddleMove(Direction direction) {
        Level.getInstance().getPaddle().endMove(direction);
    }

    public void startPaddleRotate(Direction direction) {
        Level.getInstance().getPaddle().startRotate(direction);
    }

    public void endPaddleRotate(Direction direction) {
        Level.getInstance().getPaddle().endRotate(direction);
    }

    public void createBricks(int simple, int halfMetal, int mine, int wrapper) {
        GameObjectFactory.getInstance().createBricks(simple, halfMetal, mine, wrapper);
    }

    public String checkBrickCount() {
        return GameLogic.checkBrickCount();
    }

    public void addObjectListener(GameListener listener) {
        Level.getInstance().addGameListener(listener);
    }

    public void addAnimationListener(AnimationListener listener) {
        Level.getInstance().addAnimationListener(listener);
    }

    public void invokeGodMode() {
        GameLogic.invokeGodMode();
    }

    public void usePowerUp(WrapperContent name) {
        Level.getInstance().usePowerUp(name);
    }

    public void resetScore() {
        Level.getInstance().setScore(0);
    }

    public void resetBalls() { GameLogic.destroyAllBalls(); }

}
