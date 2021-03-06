package brickingbad.ui.components.containers;

import brickingbad.controller.GameController;
import brickingbad.domain.game.GameConstants;
import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.components.BBButton;
import brickingbad.ui.components.BBGameButton;
import brickingbad.ui.game.BuildingModePanel;

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

    public BrickCountPanel() {
        setLayout(new GridLayout(5, 2));
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        setBackground(Color.darkGray);

        simpleBrickField = new JTextField("0", 2);
        halfMetalBrickField = new JTextField("0", 2);
        mineBrickField = new JTextField("0", 2);
        wrapperBrickField = new JTextField("0", 2);

        simpleBrickLabel = new JLabel("Simple Bricks");
        halfMetalBrickLabel = new JLabel("Half Metal Bricks");
        mineBrickLabel = new JLabel("Mine Bricks");
        wrapperBrickLabel = new JLabel("Wrapper Bricks");

        submitButton = new BBGameButton("Enter", this);

        simpleBrickLabel.setForeground(Color.WHITE);
        halfMetalBrickLabel.setForeground(Color.WHITE);
        mineBrickLabel.setForeground(Color.WHITE);
        wrapperBrickLabel.setForeground(Color.WHITE);

        simpleBrickField.setBackground(Color.GRAY);
        halfMetalBrickField.setBackground(Color.GRAY);
        mineBrickField.setBackground(Color.GRAY);
        wrapperBrickField.setBackground(Color.GRAY);

        simpleBrickField.setHorizontalAlignment(0);
        halfMetalBrickField.setHorizontalAlignment(0);
        mineBrickField.setHorizontalAlignment(0);
        wrapperBrickField.setHorizontalAlignment(0);

//        simpleBrickLabel.setFont();
//        halfMetalBrickLabel.setFont();
//        mineBrickLabel.setFont();
//        wrapperBrickLabel.setFont();

        add(simpleBrickLabel);
        add(simpleBrickField);

        add(halfMetalBrickLabel);
        add(halfMetalBrickField);

        add(mineBrickLabel);
        add(mineBrickField);

        add(wrapperBrickLabel);
        add(wrapperBrickField);

        add(submitButton);

        setFocusable(false);
    }

    public BBButton getSubmitButton() {
        return submitButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(submitButton)) {

            int numSimpleBricks = Integer.parseInt(simpleBrickField.getText());
            int numHalfMetalBricks = Integer.parseInt(halfMetalBrickField.getText());
            int numMineBricks = Integer.parseInt(mineBrickField.getText());
            int numWrapperBricks = Integer.parseInt(wrapperBrickField.getText());

            GameController.getInstance().createBricks(numSimpleBricks,numHalfMetalBricks,numMineBricks,numWrapperBricks);
        }
    }


}
