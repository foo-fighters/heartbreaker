package brickingbad.ui.components.containers;

import brickingbad.controller.GameController;
import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.brick.Brick;
import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.components.BBGameButton;
import brickingbad.ui.game.animation.Animator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameButtonPanel extends JPanel implements ActionListener {

  private BBGameButton saveButton;
  private BBGameButton loadButton;
  private BBGameButton pauseButton;
  private BBGameButton quitButton;
  private BBGameButton godModeButton;
  private JLabel scoreLabel;

  public GameButtonPanel() {
    setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    saveButton = new BBGameButton("SAVE", this);
    loadButton = new BBGameButton("LOAD", this);
    pauseButton = new BBGameButton("PAUSE", this);
    quitButton = new BBGameButton("QUIT", this);
    godModeButton = new BBGameButton("GOD MODE", this);
    scoreLabel = new JLabel();
    scoreLabel.setText("0");
    scoreLabel.setForeground(Color.white);
    scoreLabel.setBounds(getWidth()-250,getHeight()-50, GameConstants.heartSize,GameConstants.heartSize);

    add(saveButton);
    add(loadButton);
    add(pauseButton);
    add(quitButton);
    add(godModeButton);
    add(scoreLabel);

    setOpaque(false);
    setFocusable(false);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(saveButton)) {
      if (!Animator.getInstance().isRunning()) {
        BrickingBadFrame.getInstance().showSaveDialog();
      }
    } else if (e.getSource().equals(loadButton)) {
      if (!Animator.getInstance().isRunning()) {
        BrickingBadFrame.getInstance().showLoadDialog();
      }
    } else if (e.getSource().equals(pauseButton)) {
      pauseButton.toggleText("PAUSE", "RESUME");
      GameController.getInstance().togglePauseResume();
    } else if (e.getSource().equals(quitButton)) {
      GameController.getInstance().resetUI();
      GameController.getInstance().resetScore();
      BrickingBadFrame.getInstance().showMainMenuPanel();
      pauseButton.setText("PAUSE");
    } else if (e.getSource().equals(godModeButton)) {
      BrickingBadFrame.getInstance().showGodModeDialog();
    } else {
      throw new IllegalArgumentException();
    }
  }

  public void setUIScore(int score){
    scoreLabel.setText(Integer.toString(score));
  }

}
