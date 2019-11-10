package brickingbad.ui.components;

import brickingbad.ui.menu.MainMenuPanel;

import javax.swing.*;

public class BBMenuButton extends JButton {

  public BBMenuButton(String text) {
    setText(text);
    setSize(100, 70);
    addActionListener(MainMenuPanel.getInstance());
  }

}
