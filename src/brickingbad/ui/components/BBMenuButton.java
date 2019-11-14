package brickingbad.ui.components;

import brickingbad.ui.menu.MainMenuPanel;

import javax.swing.*;
import java.awt.event.ActionListener;

public class BBMenuButton extends JButton {

  public BBMenuButton(String text, ActionListener listener) {
    setText(text);
    setSize(100, 70);
    addActionListener(listener);
  }

}
