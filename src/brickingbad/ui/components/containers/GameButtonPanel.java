package brickingbad.ui.components.containers;

import brickingbad.ui.components.BBGameButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameButtonPanel extends JPanel {

  private BBGameButton saveButton;
  private BBGameButton loadButton;
  private BBGameButton pauseButton;
  private BBGameButton quitButton;

  public GameButtonPanel(ActionListener listener) {
    setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    saveButton = new BBGameButton("SAVE", listener);
    loadButton = new BBGameButton("LOAD", listener);
    pauseButton = new BBGameButton("PAUSE", listener);
    quitButton = new BBGameButton("QUIT", listener);

    add(saveButton);
    add(loadButton);
    add(pauseButton);
    add(quitButton);
  }

}
