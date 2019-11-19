package brickingbad.domain.game;

import brickingbad.domain.game.powerup.*;
import brickingbad.domain.game.border.*;
import brickingbad.domain.game.brick.*;
import brickingbad.domain.physics.Vector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {

    private static Game instance;

    private Paddle paddle;
    private ArrayList<Ball> balls;
    private Ground ground;
    private ArrayList<Wall> walls;
    private ArrayList<Brick> bricks;

    private int score;
    private int lives;
    private Date time;

    private ArrayList<PowerUp> activePowerUps;
    private ArrayList<PowerUp> storedPowerUps;

    private ArrayList<GameObjectListener> objectListeners;

    private Game() {
        objectListeners = new ArrayList<>();
        balls = new ArrayList<>();
        walls = new ArrayList<>();
        bricks = new ArrayList<>();
        activePowerUps = new ArrayList<>();
        storedPowerUps = new ArrayList<>();
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

    public void observeObject(GameObject object){
        for (GameObjectListener listener: objectListeners) {
            listener.addObject(object);
        }
    }

    public void initialize() {
        paddle = new Paddle();
        observeObject(paddle);
        Ball firstBall = new Ball(paddle.getBallStartPosition());
        observeObject(firstBall);
        balls.add(firstBall);
    }

    public void play() {
    }

    // GETTERS & SETTERS

    public List<GameObject> getObjects() {
        List<GameObject> objects = new ArrayList<>();
        return objects;
    }

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