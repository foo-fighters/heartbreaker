package brickingbad.domain.game;

import brickingbad.domain.game.gameobjects.Ball;
import brickingbad.domain.game.gameobjects.GameObjectFactory;
import brickingbad.domain.game.gameobjects.Paddle;
import brickingbad.domain.game.gameobjects.alien.*;
import brickingbad.domain.game.gameobjects.GameObject;
import brickingbad.domain.game.listeners.AnimationListener;
import brickingbad.domain.game.listeners.GameListener;
import brickingbad.domain.game.listeners.GameStateListener;
import brickingbad.domain.game.powerup.*;
import brickingbad.domain.game.gameobjects.border.*;
import brickingbad.domain.game.gameobjects.brick.*;
import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.PhysicsEngine;
import brickingbad.domain.physics.Vector;

import java.lang.reflect.InvocationTargetException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Level {

    private static Level instance;

    private Paddle paddle;
    private ArrayList<Ball> balls;
    private ArrayList<Brick> bricks;
    private ArrayList<Alien> aliens;
    private ArrayList<GameObject> gameObjects;

    private final int gridX = GameConstants.screenWidth / GameConstants.rectangularBrickLength;
    private final int gridY = (int)GameConstants.brickAreaHeight / GameConstants.rectangularBrickThickness;
    private boolean[][] brickGrid;
    private int startBrickCount;

    private int score;
    private int lives;
    private Clock gameClock;
    private long startTime;

    private ArrayList<WrapperContent> wrapperContentList;
    private ArrayList<PowerUp> storedPowerUps;
    private ArrayList<PowerUp> activePowerUps;
    private ArrayList<Alien> activeAliens;
    private static final Random random = new Random();

    private ArrayList<GameListener> gameListeners;
    private ArrayList<AnimationListener> animationListeners;
    private GameStateListener gameStateListener;

    // CONSTRUCTION AND INITIALIZATION
    private Level() {
        gameListeners = new ArrayList<>();
        animationListeners = new ArrayList<>();
        balls = new ArrayList<>();
        bricks = new ArrayList<>();
        activePowerUps = new ArrayList<>();
        storedPowerUps = new ArrayList<>();
        gameObjects = new ArrayList<>();
        wrapperContentList = new ArrayList<>();
        activeAliens = new ArrayList<>();
        aliens = new ArrayList<>();
        gameClock = Clock.systemDefaultZone();
        brickGrid = new boolean[gridX][gridY];
    }

    public static Level getInstance() {
        if (instance == null) {
            instance = new Level();
        }
        return instance;
    }

    public void initialize(boolean fromSave) {

        for (GameObject object : gameObjects) {
            removeObjectFromListeners(object);
        }

        gameObjects = new ArrayList<>();
        bricks = new ArrayList<>();
        balls = new ArrayList<>();
        aliens = new ArrayList<>();
        activePowerUps = new ArrayList<>();
        storedPowerUps = new ArrayList<>();
        activeAliens = new ArrayList<>();
        wrapperContentList = new ArrayList<>();
        brickGrid = new boolean[gridX][gridY];

        trackObject(new Wall(Direction.UP));
        trackObject(new Wall(Direction.RIGHT));
        trackObject(new Wall(Direction.LEFT));
        trackObject(new Ground());

        if (!fromSave) {
            lives = 3;
            score = 0;
            paddle = new Paddle();
            Ball firstBall = new Ball(paddle.getBallStartPosition());
            balls.add(firstBall);
            paddle.getCurrentBalls().add(firstBall);
            trackObject(paddle);
            trackObject(firstBall);
        }

        publishLives();
        publishScore();
    }

    public void play() {
        startTime = gameClock.millis();
        startBrickCount = 0;
        for(GameObject object: gameObjects) {
            if(object instanceof Brick) {
                ((Brick) object).startMovement();
                startBrickCount++;
            }
        }
        PhysicsEngine.getInstance().resumeIfPaused();
    }

    // LISTENER FUNCTIONS
    public void setGameStateListener(GameStateListener gameStateListener) {
        this.gameStateListener = gameStateListener;
    }

    public void addGameListener(GameListener lis) {
        gameListeners.add(lis);
    }

    private void removeObjectFromListeners(GameObject object) {
        gameListeners.forEach(lis -> lis.removeObject(object));
    }

    private void publishLives() {
        for (GameListener lis : gameListeners) {
            lis.updateLives(lives);
        }
    }

    private void publishScore() {
        for (GameListener lis : gameListeners) {
            lis.updateScore(score);
        }
    }

    public void addAnimationListener(AnimationListener lis) {
        animationListeners.add(lis);
    }

    public void startAnimation(String animationName, Object... args) {
        for(AnimationListener lis: animationListeners) {
            try {
                lis.addAnimation(animationName, args);
            } catch (ClassNotFoundException |
                    IllegalAccessException |
                    InvocationTargetException |
                    InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public void finishAnimation(String animationName) {
        for(AnimationListener lis: animationListeners) {
            lis.removeAnimation(animationName);
        }
    }

    // GAME OBJECTS
    public void addObject(GameObject object) {
        trackObject(object);
        if(object instanceof Brick) bricks.add((Brick) object);
        if(object instanceof Ball) balls.add((Ball) object);
        if(object instanceof Alien) {
            aliens.add((Alien) object);
            activeAliens.add((Alien) object);
        }
    }

    private void trackObject(GameObject object) {
        gameObjects.add(object);
        for (GameListener lis : gameListeners) {
            lis.addObject(object);
        }
    }

    public void removeObject(GameObject object) {
        removeObjectFromListeners(object);
        gameObjects.remove(object);
        bricks.remove(object);
        balls.remove(object);
    }

    // BRICKS
    public int brickCount() {
        int count = 0;
        for(GameObject object: gameObjects) {
            if(object instanceof Brick) {
                count++;
            }
        }
        return count;
    }

    // WRAPPER CONTENTS
    public void addWrapperContent() {
        if(wrapperContentList.size() < WrapperContent.values().length) {
            wrapperContentList.add(WrapperContent.values()[wrapperContentList.size()]);
        }else {
            wrapperContentList.add(WrapperContent.values()[random.nextInt(WrapperContent.values().length)]);
        }
    }

    public void revealWrapperContent(Vector revealPosition) {
        if(wrapperContentList.size() > 0) {
            WrapperContent content = wrapperContentList.remove(random.nextInt(wrapperContentList.size()));
            if(content.ordinal() < 6) {
                GameObjectFactory.getInstance().spawnPowerup(content, revealPosition);
            }else {
                if(activeAliens.stream().map(Alien::getName).collect(Collectors.toList()).contains(content)) {
                    return;
                }
                GameObjectFactory.getInstance().spawnAlien(content);
            }
        }
    }

    // POWER-UPS
    public void storePowerUp(PowerUp powerup) {
        if(storedPowerUps.stream().map(PowerUp::getName).collect(Collectors.toList()).contains(powerup.getName())
                || activePowerUps.stream().map(PowerUp::getName).collect(Collectors.toList()).contains(powerup.getName())) {
            powerup.destroy();
            return;
        }
        storedPowerUps.add(powerup);
        powerup.setVelocity(new Vector());
        int posX = 10 + GameConstants.powerupSize / 2 + (10 + GameConstants.powerupSize) * powerup.getName().ordinal();
        int posY = GameConstants.screenHeight - GameConstants.powerupSize / 2 - 10;
        powerup.setPosition(new Vector(posX, posY));
    }

    public void usePowerUp(WrapperContent name) {
        ArrayList<PowerUp> storedPowerUpsCopy = new ArrayList<>(storedPowerUps);
        for(PowerUp powerup: storedPowerUpsCopy) {
            if(powerup.getName() == name) {
                if(powerup.getName() != WrapperContent.DESTRUCTIVE_LASER_GUN) {
                    storedPowerUps.remove(powerup);
                    activePowerUps.add(powerup);
                }
                powerup.activate();
                break;
            }
        }
    }

    // WIN AND LOSE CONDITIONS
    public void anyBallsLeft() {
        if (balls.isEmpty()) {
            lives--;
            publishLives();
            if(lives <= 0) {
                gameStateListener.loseGame();
            }else {
                resetBall();
            }
        }
    }

    public void anyBricksLeft() {
        if (bricks.isEmpty()) {
            gameStateListener.winGame();
        }
    }

    public void resetBall() {
        Ball firstBall = new Ball(paddle.getBallStartPosition());
        paddle.getCurrentBalls().add(firstBall);
        addObject(firstBall);
    }

    public void increaseScore() {
        score += 300000/(gameClock.millis() - startTime);
        publishScore();
    }

    // GETTERS & SETTERS

    public long getTime() {
        return gameClock.millis();
    }

    public ArrayList<GameObject> getObjects() {
        return gameObjects;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public ArrayList<Alien> getAliens() {
        return aliens;
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

    public ArrayList<Alien> getActiveAliens() {
        return activeAliens;
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

    public boolean[][] getBrickGrid() { return brickGrid; }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public int getStartBrickCount() {
        return startBrickCount;
    }
}