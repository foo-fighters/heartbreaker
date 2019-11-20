package brickingbad.ui.components.containers;

import brickingbad.controller.GameController;
import brickingbad.ui.components.BBGameButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BrickCountPanel extends JPanel implements ActionListener {

    private JTextField simpleBrickField;
    private JTextField halfMetalBrickField;
    private JTextField mineBrickField;
    private JTextField wrapperBrickField;

    private JLabel simpleBrickLabel;
    private JLabel halfMetalBrickLabel;
    private JLabel mineBrickLabel;
    private JLabel wrapperBrickLabel;

    private BBGameButton submitButton;

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(submitButton)) {

            int numSimpleBricks = Integer.parseInt(simpleBrickField.getText());
            int numHalfMetalBricks = Integer.parseInt(halfMetalBrickField.getText());
            int numMineBricks = Integer.parseInt(mineBrickField.getText());
            int numWrapperBricks = Integer.parseInt(wrapperBrickField.getText());

            GameController.getInstance().createBricks(numSimpleBricks,numHalfMetalBricks,numMineBricks,numWrapperBricks);
        }

        // if source is submit button
        //  call game controller -> create bricks
    }

    public BrickCountPanel() {
        setLayout(new GridLayout(5, 2));
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        simpleBrickField = new JTextField("0", 2);
        halfMetalBrickField = new JTextField("0", 2);
        mineBrickField = new JTextField("0", 2);
        wrapperBrickField = new JTextField("0", 2);

        simpleBrickLabel = new JLabel("Simple Bricks");
        halfMetalBrickLabel = new JLabel("Half-metal Bricks");
        mineBrickLabel = new JLabel("Mine Bricks");
        wrapperBrickLabel = new JLabel("Wrapper Bricks");

        submitButton = new BBGameButton("Enter", this);

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
