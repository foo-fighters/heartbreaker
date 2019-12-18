package brickingbad.ui.menu;

import brickingbad.controller.AuthenticationController;
import brickingbad.controller.SaveController;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.authentication.User;
import brickingbad.services.Adapter;
import brickingbad.services.AdapterHandler;
import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.components.BBMenuButton;
import brickingbad.ui.components.UIGameObject;
import brickingbad.ui.game.animation.Animation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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

  private BufferedImage background;
  private BufferedImage title;

  private LandingPanel() {
    usernameLabel = new JLabel("USERNAME");
    usernameLabel.setForeground(Color.WHITE);
    passwordLabel = new JLabel("PASSWORD");
    passwordLabel.setForeground(Color.WHITE);

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

    loadBackgroundImage("resources/sprites/background.png");
    loadTitle("resources/sprites/title.png");
  }

  public static LandingPanel getInstance() {
    if (instance == null) {
      instance = new LandingPanel();
    }
    return instance;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
    g.drawImage(title, getWidth() / 4, getHeight() * 61 / 128,
            getWidth() / 2, getHeight() * 6 / 128, null);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String name = usernameField.getText().toLowerCase();
    String password = passwordField.getText().toLowerCase();
    String adapterString = (String) adapterSelectionBox.getSelectedItem();

    Adapter adapter = Adapter.valueOf(adapterString);
    AdapterHandler.setCurrentAdapter(adapter);

    if (e.getSource().equals(registerButton)) {
      User user = new User(name, password);
      AuthenticationController.getInstance().adapt(adapter).addUser(user);
      AuthenticationController.getInstance().adapt(adapter).authenticate(name, password);
    } else if (e.getSource().equals(loginButton)) {
      boolean auth = AuthenticationController.getInstance().adapt(adapter).authenticate(name, password);
      if (auth) {
        bbFrame.showMainMenuPanel();
      } else {
        JOptionPane.showMessageDialog(this, "Wrong username and/or password.");
      }
    }
  }

  private void loadBackgroundImage(String path) {
    try {
      this.background = ImageIO.read(new File(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadTitle(String path) {
    try {
      this.title = ImageIO.read(new File(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
