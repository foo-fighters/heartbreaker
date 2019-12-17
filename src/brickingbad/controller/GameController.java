package brickingbad.controller;

import brickingbad.domain.game.GameLogic;
import brickingbad.domain.game.gameobjects.GameObjectFactory;
import brickingbad.domain.game.Level;
import brickingbad.domain.game.listeners.AnimationListener;
import brickingbad.domain.game.listeners.GameListener;
import brickingbad.domain.game.WrapperContent;
import brickingbad.domain.game.gameobjects.brick.Brick;
import brickingbad.domain.game.gameobjects.brick.BrickFactory;
import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.PhysicsEngine;
//import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.game.BuildingModePanel;
import brickingbad.ui.game.RunningModePanel;
import brickingbad.ui.components.Panel;
import brickingbad.ui.game.animation.Animator;
import java.util.ArrayList;


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

    public static void resetUI() {
        RunningModePanel.getInstance().resetUI();
        BuildingModePanel.getInstance().resetUI();
    }

    public void initializeGame(boolean fromSave) {
        Level.getInstance().initialize(fromSave);
    }

    public void startGame() {
        Level.getInstance().play();
    }

    public void togglePauseResume() {
        Animator.getInstance().togglePauseResume();
        PhysicsEngine.getInstance().togglePauseResume();
    }

    public void resumeGameIfPaused() {
        Animator.getInstance().resumeIfPaused();
        PhysicsEngine.getInstance().resumeIfPaused();
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
        if (Level.getInstance().getBricks().size() + simple + halfMetal + mine + wrapper
                <= Level.getInstance().getGridX() * Level.getInstance().getGridY()) {

            ArrayList<Brick> simpleBricks = BrickFactory.getInstance().createSimpleBricks(simple);
            simpleBricks.forEach((brick -> GameObjectFactory.getInstance().addBrick(brick)));

            ArrayList<Brick> halfMetalBricks = BrickFactory.getInstance().createHalfMetalBricks(halfMetal);
            halfMetalBricks.forEach((brick -> GameObjectFactory.getInstance().addBrick(brick)));

            ArrayList<Brick> mineBricks = BrickFactory.getInstance().createMineBricks(mine);
            mineBricks.forEach((brick -> GameObjectFactory.getInstance().addBrick(brick)));

            ArrayList<Brick> wrapperBricks = BrickFactory.getInstance().createWrapperBricks(wrapper);
            wrapperBricks.forEach((brick -> GameObjectFactory.getInstance().addBrick(brick)));
        }
    }

    public String checkBrickCount() {
        return GameLogic.checkBrickCount();
    }

    public void addObjectListener(GameListener listener) {
        Level.getInstance().addObjectListener(listener);
    }

    public void addAnimationListener(AnimationListener listener) {
        Level.getInstance().addAnimationListener(listener);
    }

    public void invokeGodMode() {
        RunningModePanel.getInstance().invokeGodMode();
        GameLogic.invokeGodMode();
    }

    public void usePowerUp(WrapperContent name) {
        Level.getInstance().usePowerUp(name);
    }

    public void stopAnimator() {
        Animator.getInstance().stop();
    }
  
//    public void showDeadDialog(){
//        BrickingBadFrame.getInstance().showYouAreDeadDialog();
//    }
//
//    public void showWinDialog() {
//        BrickingBadFrame.getInstance().showWonDialog();
//    }

    public void resetScore() {
        PhysicsEngine.getInstance().resetTimePassed();
        Level.getInstance().setScore(0);
        setUIScore(0);
    }

    public void setUIScore(int score){
        RunningModePanel.getInstance().setScore(score);
    }
}
