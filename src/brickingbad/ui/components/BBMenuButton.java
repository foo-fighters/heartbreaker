package brickingbad.ui.components;

import brickingbad.ui.menu.MainMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BBMenuButton extends BBButton {

  public BBMenuButton(String text, ActionListener listener) {
    super(text, listener);
    this.setBackground(Color.darkGray);
    this.setForeground(Color.WHITE);
    this.setBorderPainted(false);
  }

}
