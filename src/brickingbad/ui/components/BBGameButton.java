package brickingbad.ui.components;

import java.awt.*;
import java.awt.event.ActionListener;

public class BBGameButton extends BBButton {

  public BBGameButton(String text, ActionListener listener) {
    super(text, listener);
    this.setBackground(Color.darkGray);
    this.setForeground(Color.WHITE);
    this.setBorderPainted(false);
  }

}
