package brickingbad.ui.components;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.physics.Vector;
import brickingbad.ui.BrickingBadFrame;
import brickingbad.ui.game.BuildingModePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UIGameObject extends JLabel implements MouseListener {

    private Point position;
    private BufferedImage sprite;
    private GameObject gameObject;
    private JPanel panel;
    private AffineTransform defaultScaleTransform;

    public UIGameObject(GameObject gameObject, JPanel panel) {
        this.gameObject = gameObject;
        this.panel = panel;
        this.position = new Point();
        this.setSprite();
        panel.removeMouseListener(this);
        if (BrickingBadFrame.getInstance().getCurrentPanel() instanceof BuildingModePanel) {
            panel.addMouseListener(this);
        }
        this.defaultScaleTransform = BrickingBadFrame.getInstance().getGraphicsConfiguration().getDefaultTransform();
    }

    @Override
    public void paintComponent(Graphics g) {
        position.x = (int)(gameObject.getPosition().getX() - gameObject.getSize().getX() / 2.0);
        position.y = (int)(gameObject.getPosition().getY() - gameObject.getSize().getY() / 2.0);
        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform at = new AffineTransform();
        at.scale(defaultScaleTransform.getScaleX(), defaultScaleTransform.getScaleY());
        at.rotate(-Math.toRadians(gameObject.getAngle()), gameObject.getPosition().getX(), gameObject.getPosition().getY());
        at.translate(position.x, position.y);
        at.scale(gameObject.getSize().getX() / sprite.getWidth(), gameObject.getSize().getY() / sprite.getHeight());
        g2d.setTransform(at);
        g2d.drawImage(sprite, 0, 0, null);
        g2d.dispose();
    }

    private void setSprite() {
        try {
            String spritePath = String.format("resources/sprites/%s.png", gameObject.getClass().getSimpleName().toLowerCase());
            sprite = ImageIO.read(new File(spritePath));
            setIcon(new ImageIcon(sprite));
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
