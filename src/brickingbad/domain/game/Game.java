package brickingbad.domain.game;

import brickingbad.controller.GameController;
import brickingbad.domain.game.powerup.*;
import brickingbad.domain.game.border.*;
import brickingbad.domain.game.brick.*;
import brickingbad.domain.physics.Vector;
import brickingbad.ui.game.BuildingModePanel;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

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
    private ArrayList<ErrorListener> errorListeners;

    private Game() {
        objectListeners = new ArrayList<>();
        errorListeners = new ArrayList<>();
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

    public void addErrorListener(ErrorListener err) {
        errorListeners.add(err);
    }

    private void trackObject(GameObject object) {
        //gameObjects.add(object);
        for (GameObjectListener listener : objectListeners) {
            listener.addObject(object);
        }
    }

    public void initialize() {
        for (Brick brick : bricks) {
            removeObjectFromListeners(brick);
        }
        for (Ball ball : balls) {
            removeObjectFromListeners(ball);
        }
        bricks = new ArrayList<>();

        removeObjectFromListeners(paddle);
        paddle = new Paddle();
        trackObject(paddle);

        Ball firstBall = new Ball(paddle.getBallStartPosition());
        trackObject(firstBall);
        balls.add(firstBall);
        paddle.getCurrentBalls().add(firstBall);
    }

    public void play() {
    }

    private void removeObjectFromListeners(GameObject object) {
        objectListeners.forEach(listener -> listener.removeObject(object));
    }

    public void removeObject(GameObject object) {
        removeObjectFromListeners(object);
        if (object instanceof Brick) {
            bricks.removeIf(brick -> brick.equals(object));
        }
        if (object instanceof Ball) {
            bricks.removeIf(ball -> ball.equals(object));
        }
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
        trackObject(paddle);
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
        boolean overlaps = true;

        while (overlaps) {
            double x = ThreadLocalRandom.current().nextDouble(0, GameConstants.screenWidth);
            double y = ThreadLocalRandom.current().nextDouble(30, GameConstants.screenHeight*3/4);
            if (bricks.size() == 0){
                overlaps = false;
            } else {
                for (Brick other : bricks) {
                    double otherX = other.getPosition().getX();
                    double otherY = other.getPosition().getY();
                    overlaps = Math.abs(otherX - x) <= GameConstants.rectangularBrickLength &&
                            Math.abs(otherY - y) <= GameConstants.rectangularBrickThickness;
                    if (overlaps) {
                        x = ThreadLocalRandom.current().nextDouble(0, GameConstants.screenWidth);
                        y = ThreadLocalRandom.current().nextDouble(30, GameConstants.screenHeight*3/4);
                    }
                }
            }
            if (!overlaps) {
                brick.setPosition(new Vector(x, y));
            }
        }

        bricks.add(brick);
        trackObject(brick);
    }

    public void addBall(Ball ball) {
        paddle.getCurrentBalls().add(ball);
        trackObject(ball);
    }

    public boolean checkBrickCount() {

        AtomicInteger simpleBrickCount = new AtomicInteger();
        AtomicInteger halfMetalBrickCount = new AtomicInteger();
        AtomicInteger mineBrickCount = new AtomicInteger();
        AtomicInteger wrapperBrickCount = new AtomicInteger();

        bricks.forEach(brick -> {
            if (brick instanceof SimpleBrick) {
                simpleBrickCount.getAndIncrement();
            } else if (brick instanceof HalfMetalBrick) {
                halfMetalBrickCount.getAndIncrement();
            } else if (brick instanceof MineBrick) {
                mineBrickCount.getAndIncrement();
            } else if (brick instanceof WrapperBrick) {
                wrapperBrickCount.getAndIncrement();
            }
        });

        String warning = "";
        boolean enoughSimpleBricks = false;
        boolean enoughHalfMetalBricks = false;
        boolean enoughMineBricks = false;
        boolean enoughWrapperBricks = false;

        if (simpleBrickCount.get() >= GameConstants.simpleBrickLimit) {
            enoughSimpleBricks = true;
        } else {
            warning = warning + (GameConstants.simpleBrickLimit - simpleBrickCount.get()) + " Simple Bricks, ";
        }

        if (halfMetalBrickCount.get() >= GameConstants.halfMetalBrickLimit) {
            enoughHalfMetalBricks = true;
        } else {
            warning = warning + (GameConstants.halfMetalBrickLimit - halfMetalBrickCount.get()) + " Half Metal Bricks, ";
        }

        if (mineBrickCount.get() >= GameConstants.mineBrickLimit) {
            enoughMineBricks = true;
        } else {
            warning = warning + (GameConstants.mineBrickLimit - mineBrickCount.get()) + " Mine Bricks, ";
        }

        if (wrapperBrickCount.get() >= GameConstants.wrapperBrickLimit) {
            enoughWrapperBricks = true;
        } else {
            warning = warning + (GameConstants.wrapperBrickLimit - wrapperBrickCount.get()) + " Wrapper Bricks, ";
        }

        if (enoughSimpleBricks && enoughHalfMetalBricks && enoughMineBricks && enoughWrapperBricks){
            return true;
        }else {
            warning = warning.substring(0, warning.length() - 2);
            warning = warning + " more are needed to start the Game.";
            sendError(warning);
            return false;
        }
    }

    private void sendError(String err){
        errorListeners.forEach(errorListener -> {
            errorListener.showError(err);
        });
    }
}