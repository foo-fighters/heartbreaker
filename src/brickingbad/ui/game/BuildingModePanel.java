package brickingbad.ui.game;

import javax.swing.*;

public class BuildingModePanel extends JPanel {

  private static BuildingModePanel instance;

  private BuildingModePanel() { };

  public static BuildingModePanel getInstance() {
    if (instance == null) {
      instance = new BuildingModePanel();
    }
    initialize();
    return instance;
  }

  private static void initialize() {
    JLabel tempLabel = new JLabel("THIS IS THE BUILDING MODE!");
    instance.add(tempLabel);
  }

}
