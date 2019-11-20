package brickingbad.ui.game;

import brickingbad.controller.GameController;
import brickingbad.domain.physics.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyboardListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_LEFT){
            GameController.getInstance().startPaddleMove(Direction.LEFT);
        }
        if(key == KeyEvent.VK_RIGHT){
            GameController.getInstance().startPaddleMove(Direction.RIGHT);
        }
        if(key == KeyEvent.VK_A){
            GameController.getInstance().startPaddleRotate(Direction.LEFT);
        }
        if(key == KeyEvent.VK_D){
            GameController.getInstance().startPaddleRotate(Direction.RIGHT);
        }
        if(key == KeyEvent.VK_W){
            GameController.getInstance().launchBalls();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_LEFT){
            GameController.getInstance().endPaddleMove(Direction.LEFT);
        }
        if(key == KeyEvent.VK_RIGHT){
            GameController.getInstance().endPaddleMove(Direction.RIGHT);
        }
        if(key == KeyEvent.VK_A){
            GameController.getInstance().endPaddleRotate(Direction.LEFT);
        }
        if(key == KeyEvent.VK_D){
            GameController.getInstance().endPaddleRotate(Direction.RIGHT);
        }
    }
}
