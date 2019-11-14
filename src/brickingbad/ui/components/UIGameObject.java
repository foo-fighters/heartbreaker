package brickingbad.ui.components;

import brickingbad.domain.game.GameObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIGameObject implements Drawable {

    private Point position;
    private Image sprite;
    private GameObject gameObject;
    private JPanel panel;

    @Override
    public void draw(Graphics g) {

    }

    public UIGameObject(Image sprite) {
        this.sprite = sprite;
    }
}
