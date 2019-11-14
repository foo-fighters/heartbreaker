package brickingbad.ui.game;

import brickingbad.ui.components.UIGameObject;

import javax.swing.*;
import java.util.ArrayList;

public class BuildingModePanel extends JPanel {
  private ArrayList<UIGameObject> uiObjects;
  public BuildingModePanel() {
    JLabel tempLabel = new JLabel("THIS IS THE BUILDING MODE!");
    add(tempLabel);
  }

}
