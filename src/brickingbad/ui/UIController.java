package brickingbad.ui;

import brickingbad.domain.game.Level;
import brickingbad.domain.game.listeners.GameStateListener;
import brickingbad.domain.physics.PhysicsEngine;
import brickingbad.ui.game.BuildingModePanel;
import brickingbad.ui.game.RunningModePanel;
import brickingbad.ui.game.animation.Animator;

public class UIController implements GameStateListener {

    private boolean alreadyWon = false;
    private static UIController instance;

    private UIController() {
        Level.getInstance().setGameStateListener(this);
    }

    public static UIController getInstance() {
        if (instance == null) {
            instance = new UIController();
        }
        return instance;
    }

    public void togglePauseResume() {
        Animator.getInstance().togglePauseResume();
        PhysicsEngine.getInstance().togglePauseResume();
    }

    public void resumeGameIfPaused() {
        Animator.getInstance().resumeIfPaused();
        PhysicsEngine.getInstance().resumeIfPaused();
    }

    public void resetUI() {
        RunningModePanel.getInstance().resetUI();
        BuildingModePanel.getInstance().resetUI();
    }

    @Override
    public void winGame() {
        if (!alreadyWon){
            Animator.getInstance().stop();
            BrickingBadFrame.getInstance().showWonDialog();
            alreadyWon = true;
        }
    }

    @Override
    public void loseGame() {
        Animator.getInstance().stop();
        BrickingBadFrame.getInstance().showYouAreDeadDialog();
    }
}
