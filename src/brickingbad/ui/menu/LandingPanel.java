package brickingbad.ui.menu;

import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.components.BBMenuButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LandingPanel extends JPanel implements ActionListener {

  private static LandingPanel instance;

  private final BrickingBadFrame bbFrame = BrickingBadFrame.getInstance();

  private static BBMenuButton registerButton;
  private static BBMenuButton loginButton;

  private LandingPanel() {
    registerButton = new BBMenuButton("CREATE AN ACCOUNT", this);
    loginButton = new BBMenuButton("LOG IN", this);

    add(registerButton);
    add(loginButton);
  }

  public static LandingPanel getInstance() {
    if (instance == null) {
      instance = new LandingPanel();
    }
    return instance;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(registerButton)) {
      bbFrame.showRegisterDialog();
    } else if (e.getSource().equals(loginButton)) {
      bbFrame.showLoginDialog();
    }
  }

}
