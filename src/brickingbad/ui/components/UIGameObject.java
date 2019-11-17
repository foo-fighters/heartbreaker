package brickingbad.ui.components;

import brickingbad.domain.game.GameObject;
import brickingbad.ui.game.animation.Drawable;

import javax.swing.*;
import java.awt.*;

public class UIGameObject implements Drawable {

    private Point position;
    private Image sprite;
    private GameObject gameObject;
    private JPanel panel;

    public UIGameObject(GameObject gameObject, JPanel panel) {
        this.gameObject = gameObject;
        this.panel = panel;
    }

    @Override
    public void draw(Graphics g) {

    }

    private void createSprite() {
        // check gameobject class
        // obtain sprite
    }


}
