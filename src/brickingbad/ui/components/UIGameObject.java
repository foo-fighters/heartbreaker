package brickingbad.ui.components;

import brickingbad.controller.GameController;
import brickingbad.domain.game.GameLogic;
import brickingbad.domain.game.Level;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.gameobjects.GameObject;
import brickingbad.domain.game.listeners.GameObjectListener;
import brickingbad.domain.game.gameobjects.brick.Brick;
import brickingbad.domain.game.gameobjects.powerup.PowerUp;
import brickingbad.domain.physics.Vector;
import brickingbad.ui.BrickingBadFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UIGameObject extends JLabel implements MouseListener, MouseMotionListener, GameObjectListener {

    private Point position;
    private BufferedImage sprite;
    private GameObject gameObject;
    private AffineTransform defaultFrameTransform;
    boolean dragging = false;

    public UIGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
        gameObject.setGameObjectListener(this);
        this.position = new Point();
        this.setSprite();
        this.defaultFrameTransform = BrickingBadFrame.getInstance().getGraphicsConfiguration().getDefaultTransform();
    }

    @Override
    public void paintComponent(Graphics g) {
        int newX = (int) (gameObject.getPosition().getX() - gameObject.getSize().getX() / 2.0);
        int newY = (int) (gameObject.getPosition().getY() - gameObject.getSize().getY() / 2.0);
        position.x = newX;
        position.y = newY;
        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform at = new AffineTransform();
        at.scale(defaultFrameTransform.getScaleX(), defaultFrameTransform.getScaleY());
        at.rotate(-Math.toRadians(gameObject.getAngle()), gameObject.getPosition().getX(), gameObject.getPosition().getY());
        at.translate(position.x, position.y);
        at.scale(gameObject.getSize().getX() / sprite.getWidth(), gameObject.getSize().getY() / sprite.getHeight());
        g2d.setTransform(at);
        g2d.drawImage(sprite, 0, 0, null);
        g2d.dispose();
    }

    private void setSprite()  {
        try {
            String spritePath = String.format("resources/sprites/%s.png",
                    gameObject.getClass().getSimpleName().toLowerCase());
            sprite = ImageIO.read(new File(spritePath));
            setIcon(new ImageIcon(sprite));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSprite(String spriteName)  {
        try {
            String spritePath = String.format("resources/sprites/%s%s.png",
                    gameObject.getClass().getSimpleName().toLowerCase(), spriteName);
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
    public void changeObjectState(String stateModifier) {
        setSprite(stateModifier);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (gameObject instanceof Brick) {
                double mouseX = e.getX();
                double mouseY = e.getY();
                double brickX = gameObject.getPosition().getX();
                double brickY = gameObject.getPosition().getY();
                if (Math.abs(mouseX-brickX) <= GameConstants.rectangularBrickLength / 2.0 &&
                        Math.abs(mouseY-brickY) <= GameConstants.rectangularBrickThickness / 2.0) {
                    Level.getInstance().removeObject(gameObject);
                }
            }
        }
        if(e.getButton() == MouseEvent.BUTTON1) {
            if(gameObject instanceof PowerUp && Level.getInstance().getStoredPowerUps().contains(gameObject)) {
                double mouseX = e.getX();
                double mouseY = e.getY();
                double brickX = gameObject.getPosition().getX();
                double brickY = gameObject.getPosition().getY();
                if (Math.abs(mouseX-brickX) <= GameConstants.powerupSize / 2.0 &&
                        Math.abs(mouseY-brickY) <= GameConstants.powerupSize / 2.0) {
                    GameController.getInstance().usePowerUp(((PowerUp) gameObject).getName());
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (gameObject instanceof Brick) {
                double mouseX = e.getX();
                double mouseY = e.getY();
                double brickX = gameObject.getPosition().getX();
                double brickY = gameObject.getPosition().getY();
                if (Math.abs(mouseX-brickX) <= GameConstants.rectangularBrickLength / 2.0 &&
                        Math.abs(mouseY-brickY) <= GameConstants.rectangularBrickThickness / 2.0) {
                    dragging = true;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(dragging) {
            Brick brick = (Brick)gameObject;
            Vector location = GameLogic.getClosestGridLocation(gameObject.getPosition());
            int indX = (int)location.getX();
            int indY = (int)location.getY();
            if(brick.getPosition().getX() <= 0 ||
                    brick.getPosition().getX() >= GameConstants.rectangularBrickLength *
                            Level.getInstance().getBrickGrid().length ||
                    brick.getPosition().getY() <= GameConstants.menuAreaHeight ||
                    brick.getPosition().getY() >= GameConstants.menuAreaHeight + GameConstants.brickAreaHeight ||
                    Level.getInstance().getBrickGrid()[indX][indY]) {
                brick.setPosition(new Vector((brick.getCellX() + 0.5) * GameConstants.rectangularBrickLength,
                        GameConstants.menuAreaHeight + (brick.getCellY() + 0.5) * GameConstants.rectangularBrickThickness));
            }else {
                Level.getInstance().getBrickGrid()[brick.getCellX()][brick.getCellY()] = false;
                Level.getInstance().getBrickGrid()[indX][indY] = true;
                brick.setCellX(indX);
                brick.setCellY(indY);
                brick.setPosition(new Vector((indX + 0.5) * GameConstants.rectangularBrickLength,
                        GameConstants.menuAreaHeight + (indY + 0.5) * GameConstants.rectangularBrickThickness));
            }
        }
        dragging = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragging) {
            gameObject.setPosition(new Vector(e.getX(), e.getY()));
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
