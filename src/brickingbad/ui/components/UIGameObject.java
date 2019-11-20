package brickingbad.ui.components;

import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.Paddle;
import brickingbad.ui.game.animation.Drawable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UIGameObject implements Drawable {

    private Point position;
    private BufferedImage sprite;
    private GameObject gameObject;
    private JPanel panel;
    private boolean rotated;

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
        Graphics2D g2d = (Graphics2D) g;

        if (this.gameObject instanceof Paddle) {
            UIGameObjectHelper.angle = ((Paddle) gameObject).getAngle();
            UIGameObjectHelper.rotateAxisX = gameObject.getPosition().getX();
            UIGameObjectHelper.rotateAxisY = gameObject.getPosition().getY();
            g2d.rotate(Math.toRadians(UIGameObjectHelper.angle), UIGameObjectHelper.rotateAxisX, UIGameObjectHelper.rotateAxisY);
            UIGameObjectHelper.rotated = true;
        } else {
            if (UIGameObjectHelper.rotated) {
                g2d.rotate(Math.toRadians(-UIGameObjectHelper.angle), UIGameObjectHelper.rotateAxisX, UIGameObjectHelper.rotateAxisY);
                UIGameObjectHelper.rotated = false;
            }
        }
        g2d.drawImage(sprite, position.x, position.y, null);
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
