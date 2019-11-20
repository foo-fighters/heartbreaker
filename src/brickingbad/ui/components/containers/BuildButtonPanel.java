package brickingbad.ui.components.containers;

import brickingbad.domain.game.Game;
import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.components.BBGameButton;

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
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        saveButton = new BBGameButton("SAVE", this);
        loadButton = new BBGameButton("LOAD", this);
        playButton = new BBGameButton("PLAY", this);
        quitButton = new BBGameButton("QUIT", this);

        add(saveButton);
        add(loadButton);
        add(playButton);
        add(quitButton);

        setOpaque(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(saveButton)) {
            // TODO: perform save action
        } else if (e.getSource().equals(loadButton)) {
            // TODO: perform load action
        } else if (e.getSource().equals(playButton)) {
            BrickingBadFrame.getInstance().showRunningModePanel();
            Game.getInstance().play();
        } else if (e.getSource().equals(quitButton)) {
            BrickingBadFrame.getInstance().showMainMenuPanel();
        } else {
            throw new IllegalArgumentException();
        }
    }

}