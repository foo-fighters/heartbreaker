package brickingbad.ui.game;

import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.GameObjectListener;
import brickingbad.ui.components.UIGameObject;

import javax.swing.*;
import java.util.ArrayList;

public class RunningModePanel extends JPanel implements GameObjectListener {

    private ArrayList<UIGameObject> uiObjects;

    public RunningModePanel() {
        uiObjects = new ArrayList<>();
    }

    public void addUIObject(GameObject gameObject) {
        uiObjects.add(new UIGameObject(gameObject, this));
    }

    @Override
    public void addObject(GameObject object) {

    }

    @Override
    public void removeObject(GameObject object) {

    }

}
