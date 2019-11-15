package brickingbad.ui.components;

import javax.swing.*;
import java.awt.event.ActionListener;

public class BBButton extends JButton {

  public BBButton(String text, ActionListener listener) {
    setText(text);
    addActionListener(listener);
  }

}
