package brickingbad.ui.components.containers;

import brickingbad.audio.AudioController;
import brickingbad.controller.GameController;
import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.UIController;
import brickingbad.ui.components.BBGameButton;
import brickingbad.ui.game.BuildingModePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuildButtonPanel extends JPanel implements ActionListener {

    private BBGameButton saveButton;
    private BBGameButton loadButton;
    private BBGameButton playButton;
    private BBGameButton quitButton;

    public BuildButtonPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        saveButton = new BBGameButton("SAVE", this);
        loadButton = new BBGameButton("LOAD", this);
        playButton = new BBGameButton("PLAY", this);
        quitButton = new BBGameButton("QUIT", this);

        add(saveButton);
        add(loadButton);
        add(playButton);
        add(quitButton);

        setOpaque(false);
        setFocusable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(saveButton)) {
            BrickingBadFrame.getInstance().showSaveDialog();
        } else if (e.getSource().equals(loadButton)) {
            BrickingBadFrame.getInstance().showLoadDialog();
        } else if (e.getSource().equals(playButton)) {
            String checkedForCount = GameController.getInstance().checkBrickCount();
            if (checkedForCount.equals("")) {
                AudioController.getInstance().playTransitionMusic();
                GameController.getInstance().startGame();
                BrickingBadFrame.getInstance().showRunningModePanel();
            }else {
                BuildingModePanel.getInstance().showError(checkedForCount);
            }
            UIController.getInstance().resumeUI();
        } else if (e.getSource().equals(quitButton)) {
            BrickingBadFrame.getInstance().showMainMenuPanel();
        } else {
            throw new IllegalArgumentException();
        }
    }

}
