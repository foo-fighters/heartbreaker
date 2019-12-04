package brickingbad.ui.components;

import brickingbad.controller.EffectsController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BBButton extends JButton {

  public BBButton(String text, ActionListener listener) {
    setText(text);
    addActionListener(listener);
    addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        EffectsController.getInstance().playAudio("button");
      }
    });
  }


}
