package brickingbad.ui.menu;

import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.DefaultActionable;
import brickingbad.ui.components.BBButton;
import brickingbad.ui.components.BBMenuButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpPanel extends JPanel implements DefaultActionable, ActionListener {

  private final String text = "WELCOME TO THE BRICKING BAD GAME! THIS IS THE HELP PAGE. \n" +
          "\n" +
          "YOUR GOAL IS TO DESTROY ALL THE BRICKS BEFORE THE ALIENS CAN BUILD A WALL.\n" +
          "\n" +
          "YOU MOVE THE PADDLE WITH <- AND -> KEYS, AND ROTATE WITH `A` AND `D`.\n" +
          "\n" +
          "YOU CAN SAVE THE GAME AND PLAY AGAIN LATER FROM WHERE YOU LEFT OFF.\n" +
          "\n" +
          "THERE ARE 4 TYPES OF BRICKS:\n" +
          "\t\n" +
          "\t1. SIMPLE BRICK => DOES NOTHING\n" +
          "\n" +
          "\t2. WRAPPER BRICKTHEY => DROPS STUFF AND SPAWNS ALIENS WHEN DESTROYED\n" +
          "\n" +
          "\t3. MINE BRICK => DESTROYES OTHER BRICKS WITH IT WHEN IT IS DESTROYED\n" +
          "\n" +
          "\t4. HALF METAL BRICK: => CAN ONLY BE DESTROYED FROM ABOVE, CRACKED BY FIREBALL\n" +
          "\n" +
          "POWERUPS:\n" +
          "\n" +
          "\t1. CHEMICAL BALL => BALL PIERCES THROUGH BRICKS.\n" +
          "\n" +
          "\t2. GANG OF BALLS => 10 BALLS SPAWN\n" +
          "\n" +
          "\t3. DESTRUCTIVE LASER GUN => SHOOT LASERS\n" +
          "\n" +
          "\t4. FIREBALL => BALL EXPLODES WHEN IT HITS A BRICK\n" +
          "\n" +
          "\t5. TALLER PADDLE => TALLER PADDLE\n" +
          "\n" +
          "\t6. MAGNET => PADDLE CATCHES BALLS INSTEAD OF REFLECTING\n" +
          "\n" +
          "ALIENS:\n" +
          "\n" +
          "\t1. COOPERATIVE ALIEN => DESTROYS BRICKS IN A ROW FOR YOU.\n" +
          "\n" +
          "\t2. PROTECTING ALIEN => KEEPS TO BALL FROM HITTING THE BRICKS\n" +
          "\n" +
          "\t3. REPAIRING ALIEN => REBUILDS BRICKS\n" +
          "\n" +
          "\t4. DRUNK ALIEN => DOES DIFFERENT THINGS\n";

  private static HelpPanel instance;

  private BBMenuButton exitButton;
  private JEditorPane textField;

  private HelpPanel() {
    setLayout(new BorderLayout());

    exitButton = new BBMenuButton("BACK TO MAIN MENU", this);
    add(exitButton, BorderLayout.SOUTH);

    textField = new JEditorPane();
    textField.setEditable(false);
    textField.setText(text);
    textField.setBackground(Color.lightGray);
    add(textField, BorderLayout.CENTER);
  }

  public static HelpPanel getInstance() {
    if (instance == null) {
      instance = new HelpPanel();
    }
    return instance;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(exitButton)) {
      BrickingBadFrame.getInstance().showMainMenuPanel();
    }
  }

}
