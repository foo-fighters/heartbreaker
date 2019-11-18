package brickingbad.domain.game;

import brickingbad.domain.game.powerup.*;
import brickingbad.domain.game.border.*;
import brickingbad.domain.game.brick.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {

    private static Game instance;

    public Paddle paddle;
    public ArrayList<Ball> balls;
    public Ground ground;
    public Wall wall;
    public ArrayList<Brick> bricks;

    public int score;
    public int lives;
    public Date time;

    public ArrayList<PowerUp> activePowerUps;
    public ArrayList<PowerUp> storedPowerUps;

    public ArrayList<GameObjectListener> objectListeners;

    private Game() {
        objectListeners = new ArrayList<>();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void addObjectListener(GameObjectListener listener) {
        objectListeners.add(listener);
    }

    public List<GameObject> getObjects() {
        List<GameObject> objects = new ArrayList<GameObject>();
        return objects;
    }

    public void play() {
    }

    // GETTERS & SETTERS

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public ArrayList<PowerUp> getActivePowerUps() {
        return activePowerUps;
    }

    public ArrayList<PowerUp> getStoredPowerUps() {
        return storedPowerUps;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

}