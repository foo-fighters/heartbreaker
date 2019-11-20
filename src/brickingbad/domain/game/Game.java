package brickingbad.domain.game;

import brickingbad.domain.game.powerup.*;
import brickingbad.domain.game.border.*;
import brickingbad.domain.game.brick.*;
import brickingbad.domain.physics.Vector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private static Game instance;

    private Paddle paddle;
    private ArrayList<Ball> balls;
    private Ground ground;
    private ArrayList<Wall> walls;
    private ArrayList<Brick> bricks;
    //private ArrayList<GameObject> gameObjects;

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
        //gameObjects = new ArrayList<>();
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

    private void trackObject(GameObject object) {
        //gameObjects.add(object);
        for (GameObjectListener listener : objectListeners) {
            listener.addObject(object);
        }
    }

    public void initialize() {
        paddle = new Paddle();
        trackObject(paddle);
        Ball firstBall = new Ball(paddle.getBallStartPosition());
        trackObject(firstBall);
        balls.add(firstBall);
        paddle.getCurrentBalls().add(firstBall);
    }

    public void play() {
    }

    // GETTERS & SETTERS

    public List<GameObject> getObjects() {
        List<GameObject> gameObjects = new ArrayList<>();

        gameObjects.add(paddle);
        gameObjects.addAll(bricks);
        gameObjects.addAll(balls);
        gameObjects.addAll(activePowerUps);
        gameObjects.addAll(storedPowerUps);

        return gameObjects;
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

    public void addBrick(Brick brick) {

        double x = ThreadLocalRandom.current().nextDouble(0, GameConstants.screenWidth); //will be fixed
        double y = ThreadLocalRandom.current().nextDouble(0, GameConstants.screenHeight);

        brick.setPosition(new Vector(x, y));

        bricks.add(brick);
        trackObject(brick);
    }
}