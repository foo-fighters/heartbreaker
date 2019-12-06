package brickingbad.ui.menu;

import brickingbad.controller.AuthenticationController;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.authentication.User;
import brickingbad.services.Adapter;
import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.components.BBMenuButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LandingPanel extends JPanel implements ActionListener {

  private static LandingPanel instance;

  private final BrickingBadFrame bbFrame = BrickingBadFrame.getInstance();

  private static JLabel usernameLabel;
  private static JLabel passwordLabel;
  private static JTextField usernameField;
  private static JPasswordField passwordField;

  private static JComboBox adapterSelectionBox;

  private static BBMenuButton registerButton;
  private static BBMenuButton loginButton;

  private LandingPanel() {
    setSize(GameConstants.screenWidth / 2, GameConstants.screenHeight / 2);

    usernameLabel = new JLabel("USERNAME");
    passwordLabel = new JLabel("PASSWORD");

    usernameField = new JTextField("", 20);
    passwordField = new JPasswordField("", 20);
    passwordField.setEchoChar('*');

    String[] selections = {Adapter.LOCAL.name(), Adapter.MONGODB.name()};
    adapterSelectionBox = new JComboBox(selections);

    registerButton = new BBMenuButton("CREATE AN ACCOUNT", this);
    loginButton = new BBMenuButton("LOG IN", this);

    add(usernameLabel);
    add(usernameField);
    add(passwordLabel);
    add(passwordField);
    add(adapterSelectionBox);
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
    String name = usernameField.getText();
    String password = passwordField.getText();
    String adapter = (String) adapterSelectionBox.getSelectedItem();
    if (e.getSource().equals(registerButton)) {
      User user = new User(name, password);
      AuthenticationController.getInstance().adapt(Adapter.valueOf(adapter)).addUser(user);
    } else if (e.getSource().equals(loginButton)) {
      boolean auth = AuthenticationController.getInstance().adapt(Adapter.valueOf(adapter)).authenticate(name, password);
      if (auth) {
        bbFrame.showMainMenuPanel();
      } else {
        JOptionPane.showMessageDialog(this, "Wrong username and/or password.");
      }
    }
  }

}
