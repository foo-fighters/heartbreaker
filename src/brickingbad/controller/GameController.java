package brickingbad.controller;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameObjectListener;
import brickingbad.domain.game.WrapperContent;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.game.brick.BrickFactory;
import brickingbad.domain.game.persistence.Save;
import brickingbad.domain.game.persistence.SaveAssembler;
import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.PhysicsEngine;
import brickingbad.services.persistence.SaveRepository;
import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.game.BuildingModePanel;
import brickingbad.ui.game.RunningModePanel;
import brickingbad.ui.components.Panel;

import brickingbad.ui.game.animation.Animator;

import java.util.ArrayList;
import java.util.List;


public class GameController {

    private static GameController instance;
    private static SaveRepository saveRepository;

    private GameController() {
        saveRepository = SaveRepository.getInstance().adaptLocal();
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

    public void saveGame(String name, boolean inRunningMode) {
        Game game = Game.getInstance();
        Save save = SaveAssembler.assemble(game, name);
        save.inRunningMode = inRunningMode;
        saveRepository.save(save);
    }

    public void loadGame(String name) {
        Save save = saveRepository.getSaveByName(name);
        if (save.inRunningMode) {
            BrickingBadFrame.getInstance().showRunningModePanel();
        } else {
            BrickingBadFrame.getInstance().showBuildingModePanel();
        }
        SaveAssembler.disassemble(save);
        Game.getInstance().play();
    }

    public List<String> getSaveNames() {
      return saveRepository.getSaveNames();
    }

    public void initializeGame(boolean fromSave) {
        Game.getInstance().initialize(fromSave);
    }

    public void startGame() {
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
        if (Game.getInstance().getBricks().size() + simple + halfMetal + mine + wrapper < 919){
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

    public void addObjectListener(GameObjectListener listener) {
        Game.getInstance().addObjectListener(listener);
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
        BrickingBadFrame.getInstance().showYouAreDeadDialog();
    }

    public void showWinDialog() {
        BrickingBadFrame.getInstance().showWonDialog();
    }
}
