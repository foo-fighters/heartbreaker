package brickingbad.ui.components;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.Paddle;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.physics.Vector;
import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.game.BuildingModePanel;
import brickingbad.ui.game.animation.Drawable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UIGameObject extends Component implements Drawable, MouseListener {

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
        panel.removeMouseListener(this);
        if (BrickingBadFrame.getInstance().getCurrentPanel() instanceof BuildingModePanel) {
            panel.addMouseListener(this);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        position.setLocation((int)gameObject.getPosition().getX(), (int)gameObject.getPosition().getY());
        if (gameObject.getSize() == null) {
            System.out.println(gameObject.getClass().getSimpleName());
        }
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

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(panel.getClass().getSimpleName());
        if (panel.getClass().getSimpleName().equals("RunningModePanel")) {
            if (e.getButton() == MouseEvent.BUTTON3){
                if (gameObject instanceof Brick){
                    double mouseX = e.getX();
                    double mouseY = e.getY();

                    Vector v = gameObject.getPosition();

                    double brickX = v.getX();
                    double brickY = v.getY();

                    if (Math.abs(mouseX-brickX) <= GameConstants.rectangularBrickLength &&
                            Math.abs(mouseY-brickY) <= GameConstants.rectangularBrickThickness){
                        gameObject.destroy();
                    }


                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
