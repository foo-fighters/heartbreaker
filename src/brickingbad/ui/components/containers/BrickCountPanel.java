package brickingbad.ui.components.containers;

import brickingbad.ui.components.BBGameButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class BrickCountPanel extends JPanel {

  private JTextField simpleBrickField;
  private JTextField halfMetalBrickField;
  private JTextField mineBrickField;
  private JTextField wrapperBrickField;

  private JLabel simpleBrickLabel;
  private JLabel halfMetalBrickLabel;
  private JLabel mineBrickLabel;
  private JLabel wrapperBrickLabel;

  private BBGameButton submitButton;

  public BrickCountPanel(ActionListener listener) {
    setLayout(new GridLayout(5, 2));
    setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black),
                                                 BorderFactory.createEmptyBorder(5, 5, 5, 5)));

    simpleBrickField = new JTextField(2);
    halfMetalBrickField = new JTextField(2);
    mineBrickField = new JTextField(2);
    wrapperBrickField = new JTextField(2);

    simpleBrickLabel = new JLabel("Simple Bricks");
    halfMetalBrickLabel = new JLabel("Half-metal Bricks");
    mineBrickLabel = new JLabel("Mine Bricks");
    wrapperBrickLabel = new JLabel("Wrapper Bricks");

    submitButton = new BBGameButton("Enter", listener);

    add(simpleBrickLabel);
    add(simpleBrickField);

    add(halfMetalBrickLabel);
    add(halfMetalBrickField);

    add(mineBrickLabel);
    add(mineBrickField);

    add(wrapperBrickLabel);
    add(wrapperBrickField);

    add(submitButton);

  }

}
