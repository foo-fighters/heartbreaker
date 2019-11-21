package brickingbad.ui.components;

import java.awt.*;
import java.awt.event.ActionListener;

public class BBGameButton extends BBButton {

  public BBGameButton(String text, ActionListener listener) {
    super(text, listener);
    this.setBackground(Color.darkGray);
    this.setForeground(Color.WHITE);
    this.setBorderPainted(false);
    setFocusable(false);
  }

  public void toggleText(String text1, String text2) {
    if (this.getText().equals(text1)) {
      this.setText(text2);
    } else if (this.getText().equals(text2)) {
      this.setText(text1);
    } else {
      throw new IllegalArgumentException();
    }
  }

}
