package brickingbad.ui;

import brickingbad.domain.game.listeners.GameStateListener;
import brickingbad.domain.physics.PhysicsEngine;
import brickingbad.ui.game.animation.Animator;

public class UIController implements GameStateListener {

    private static UIController instance;

    private UIController() {
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
