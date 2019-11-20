package brickingbad.ui.components;

import brickingbad.domain.game.GameObject;
import brickingbad.ui.game.animation.Drawable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UIGameObject implements Drawable {

    private Point position;
    private BufferedImage sprite;
    private GameObject gameObject;
    private JPanel panel;

    public UIGameObject(GameObject gameObject, JPanel panel) {
        this.gameObject = gameObject;
        this.panel = panel;
        this.position = new Point();
        this.setSprite();
    }

    @Override
    public void draw(Graphics g) {
        position.setLocation((int)gameObject.getPosition().getX(), (int)gameObject.getPosition().getY());
        position.translate((int)-gameObject.getSize().getX() / 2, (int)-gameObject.getSize().getY() / 2);
        //g.drawRect(position.x, position.y, gameObject.getSize().getX(), gameObject.getSize().getY());
        g.drawImage(sprite, position.x, position.y, null);
    }

    private void setSprite() {
        try {
            String spritePath = String.format("resources/sprites/%s.png", gameObject.getClass().getSimpleName().toLowerCase());
            sprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean containsObject(GameObject object){
        return object.equals(gameObject);
    }

}
