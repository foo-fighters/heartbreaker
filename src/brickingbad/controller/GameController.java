package brickingbad.controller;

import brickingbad.domain.game.listeners.AnimationListener;
import brickingbad.domain.game.Game;
import brickingbad.domain.game.listeners.GameListener;
import brickingbad.domain.game.WrapperContent;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.game.brick.BrickFactory;
import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.PhysicsEngine;
import brickingbad.ui.BrickingBadFrame;
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

    public boolean inRunningMode() {
        Panel panel = BrickingBadFrame.getInstance().getCurrentPanelName();
        return panel == Panel.RUNNING_MODE;
    }

    public void initializeGame(boolean fromSave) {
        Game.getInstance().initialize(fromSave);
    }

    public void startGame() {
        EffectsController.getInstance().playAudio("start");
        Game.getInstance().play();
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
        Game.getInstance().getPaddle().launchBalls();
    }

    public void startPaddleMove(Direction direction) {
        Game.getInstance().getPaddle().startMove(direction);
    }

    public void endPaddleMove(Direction direction) {
        Game.getInstance().getPaddle().endMove(direction);
    }

    public void startPaddleRotate(Direction direction) {
        Game.getInstance().getPaddle().startRotate(direction);
    }

    public void endPaddleRotate(Direction direction) {
        Game.getInstance().getPaddle().endRotate(direction);
    }

    public void createBricks(int simple, int halfMetal, int mine, int wrapper) {
        if (Game.getInstance().getBricks().size() + simple + halfMetal + mine + wrapper <= Game.getInstance().getGridSize()) {
            ArrayList<Brick> simpleBricks = BrickFactory.getInstance().createSimpleBricks(simple);

            simpleBricks.forEach((brick -> {
                Game.getInstance().addBrick(brick);
            }));

            ArrayList<Brick> halfMetalBricks = BrickFactory.getInstance().createHalfMetalBricks(halfMetal);

            halfMetalBricks.forEach((brick -> {
                Game.getInstance().addBrick(brick);
            }));

            ArrayList<Brick> mineBricks = BrickFactory.getInstance().createMineBricks(mine);

            mineBricks.forEach((brick -> {
                Game.getInstance().addBrick(brick);
            }));

            ArrayList<Brick> wrapperBricks = BrickFactory.getInstance().createWrapperBricks(wrapper);

            wrapperBricks.forEach((brick -> {
                Game.getInstance().addBrick(brick);
            }));
        }
    }

    public boolean checkBrickCount(){
        return Game.getInstance().checkBrickCount();
    }

    public void addObjectListener(GameListener listener) {
        Game.getInstance().addObjectListener(listener);
    }

    public void addAnimationListener(AnimationListener listener) {
        Game.getInstance().addAnimationListener(listener);
    }

    public void invokeGodMode() {
        RunningModePanel.getInstance().invokeGodMode();
        Game.getInstance().invokeGodMode();
    }

    public void usePowerUp(WrapperContent name) {
        Game.getInstance().usePowerUp(name);
    }

    public void lifeLost() {
        Game.getInstance().lostLife();
    }

    public void stopAnimator() {
        Animator.getInstance().stop();
    }
  
    public void showDeadDialog(){
        EffectsController.getInstance().playAudio("endLaugh");
        BrickingBadFrame.getInstance().showYouAreDeadDialog();
    }

    public void showWinDialog() {
        EffectsController.getInstance().playAudio("congratz");
        BrickingBadFrame.getInstance().showWonDialog();
    }

    public void resetScore() {
        PhysicsEngine.getInstance().resetTimePassed();
        Game.getInstance().setScore(0);
        setUIScore(0);
    }

    public void setUIScore(int score){
        RunningModePanel.getInstance().setScore(score);
    }
}
