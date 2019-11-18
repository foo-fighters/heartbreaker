package brickingbad.ui.components.containers;

import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.components.BBGameButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameButtonPanel extends JPanel implements ActionListener {

  private BBGameButton saveButton;
  private BBGameButton loadButton;
  private BBGameButton pauseButton;
  private BBGameButton quitButton;

  public GameButtonPanel() {
    setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    saveButton = new BBGameButton("SAVE", this);
    loadButton = new BBGameButton("LOAD", this);
    pauseButton = new BBGameButton("PAUSE", this);
    quitButton = new BBGameButton("QUIT", this);

    add(saveButton);
    add(loadButton);
    add(pauseButton);
    add(quitButton);

    setOpaque(false);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(saveButton)) {
      // TODO: perform save action
    } else if (e.getSource().equals(loadButton)) {
      // TODO: perform load action
    } else if (e.getSource().equals(pauseButton)) {
      // TODO: perform pause action
    } else if (e.getSource().equals(quitButton)) {
      BrickingBadFrame.getInstance().showMainMenuPanel();
    } else {
      throw new IllegalArgumentException();
    }
  }


}