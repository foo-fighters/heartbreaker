package brickingbad.domain.game;

import brickingbad.domain.game.border.Ground;
import brickingbad.domain.game.border.Wall;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.game.powerup.PowerUp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {

    private static Game instance;

    private Paddle paddle;
    private ArrayList<Ball> balls;
    private Ground ground;
    private Wall wall;
    private ArrayList<Brick> bricks;

    private int score;
    private int lives;
    private Date time;

    private ArrayList<PowerUp> activePowerUps;
    private ArrayList<PowerUp> storedPowerUps;

    private ArrayList<GameObjectListener> objectListeners;

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

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public void setBalls(ArrayList<Ball> balls) {
        this.balls = balls;
    }

    public void setBricks(ArrayList<Brick> bricks) {
        this.bricks = bricks;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setActivePowerUps(ArrayList<PowerUp> activePowerUps) {
        this.activePowerUps = activePowerUps;
    }

    public void setStoredPowerUps(ArrayList<PowerUp> storedPowerUps) {
        this.storedPowerUps = storedPowerUps;
    }
}