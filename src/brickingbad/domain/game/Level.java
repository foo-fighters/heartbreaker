package brickingbad.domain.game;

import brickingbad.controller.GameController;
import brickingbad.domain.game.alien.*;
import brickingbad.domain.game.listeners.AnimationListener;
import brickingbad.domain.game.listeners.GameListener;
import brickingbad.domain.game.powerup.*;
import brickingbad.domain.game.border.*;
import brickingbad.domain.game.brick.*;
import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.PhysicsEngine;
import brickingbad.domain.physics.Vector;
import brickingbad.domain.physics.ball.ChemicalBallState;
import brickingbad.domain.physics.ball.FireBallState;

import java.lang.reflect.InvocationTargetException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
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

    private ArrayList<WrapperContent> wrapperContentList;
    private ArrayList<PowerUp> storedPowerUps;
    private ArrayList<PowerUp> activePowerUps;
    private ArrayList<Alien> activeAliens;
    private boolean alreadyWon;
    private static final Random random = new Random();

    private ArrayList<GameListener> objectListeners;
    private ArrayList<AnimationListener> animationListeners;

    // CONSTRUCTION AND INITIALIZATION
    private Level() {
        objectListeners = new ArrayList<>();
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
        alreadyWon = false;

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
        GameController.getInstance().setUIScore(score);
    }

    public void play() {
        startBrickCount = 0;
        for(GameObject object: gameObjects) {
            if(object instanceof Brick) {
                ((Brick) object).startMovement();
                startBrickCount++;
            }
        }
    }

    // LISTENER FUNCTIONS
    public void addObjectListener(GameListener listener) {
        objectListeners.add(listener);
    }

    public void addAnimationListener(AnimationListener anim) {
        animationListeners.add(anim);
    }

    private void removeObjectFromListeners(GameObject object) {
        objectListeners.forEach(listener -> listener.removeObject(object));
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
    private void trackObject(GameObject object) {
        gameObjects.add(object);
        for (GameListener listener : objectListeners) {
            listener.addObject(object);
        }
    }

    public void removeObject(GameObject object) {
        removeObjectFromListeners(object);
        gameObjects.remove(object);
        bricks.remove(object);
        balls.remove(object);
    }

    // BALLS
    public void addBall(Ball ball) {
        balls.add(ball);
        trackObject(ball);
    }

    public void resetBall() {
        Ball firstBall = new Ball(paddle.getBallStartPosition());
        balls.add(firstBall);
        paddle.getCurrentBalls().add(firstBall);
        trackObject(firstBall);
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

    public void addBrick(Brick brick) {
        boolean overlaps = true;
        int count = 0;
        while (overlaps && count < 1000) {
            int x = ThreadLocalRandom.current().nextInt(gridX);
            int y = ThreadLocalRandom.current().nextInt(gridY);
            if (bricks.size() == 0) {
                overlaps = false;
            } else {
                overlaps = brickGrid[x][y];
            }
            if (!overlaps) {
                brickGrid[x][y] = true;
                brick.setPosition(new Vector((x + 0.5) * GameConstants.rectangularBrickLength,
                        GameConstants.menuAreaHeight + (y + 0.5) * GameConstants.rectangularBrickThickness));
                brick.setCellX(x);
                brick.setCellY(y);
            }
            count++;
        }
        bricks.add(brick);
        trackObject(brick);
    }

    public void increaseScore() {
        score += 300/(PhysicsEngine.getInstance().getTimePassed()/1000);
        GameController.getInstance().setUIScore(score);
    }

    public void addBrickHorizontal() {
        boolean overlaps = false;
        int y = ThreadLocalRandom.current().nextInt(gridY);
        int x = GameConstants.rectangularBrickLength / 2;
        while(x <= GameConstants.screenWidth) {
            SimpleBrick brick = new SimpleBrick();
            brick.setPosition(new Vector(x, y));
            ArrayList<GameObject> objectsCopy = new ArrayList<>(gameObjects);
            for(GameObject object: objectsCopy) {
                if(PhysicsEngine.areColliding(object, brick)) {
                    overlaps = true;
                    break;
                }
            }
            if(!overlaps) {
                bricks.add(brick);
                trackObject(brick);
            }
            x += GameConstants.rectangularBrickLength;
        }
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
                spawnPowerup(content, revealPosition);
            }else {
                if(activeAliens.stream().map(Alien::getName).collect(Collectors.toList()).contains(content)) {
                    return;
                }
                spawnAlien(content);
            }
        }
    }

    // POWER-UPS
    private void spawnPowerup(WrapperContent content, Vector revealPosition) {
        switch (content) {
            case FIREBALL:
                trackObject(new Fireball(revealPosition));
                break;
            case CHEMICAL_BALL:
                trackObject(new ChemicalBall(revealPosition));
                break;
            case DESTRUCTIVE_LASER_GUN:
                trackObject(new DestructiveLaserGun(revealPosition));
                break;
            case MAGNET:
                trackObject(new Magnet(revealPosition));
                break;
            case TALLER_PADDLE:
                trackObject(new TallerPaddle(revealPosition));
                break;
            case GANG_OF_BALLS:
                spawnGangOfBalls(revealPosition);
                break;
            default:
        }
    }

    public void storePowerUp(PowerUp powerup) {
        if(storedPowerUps.stream().map(PowerUp::getName).collect(Collectors.toList()).contains(powerup.getName())
                || activePowerUps.stream().map(PowerUp::getName).collect(Collectors.toList()).contains(powerup.getName())) {
            powerup.destroy();
            return;
        }
        storedPowerUps.add(powerup);
        powerup.velocity.setVector(0.0, 0.0);
        int posX = 10 + GameConstants.powerupSize / 2 + (10 + GameConstants.powerupSize) * powerup.getName().ordinal();
        int posY = GameConstants.screenHeight - GameConstants.powerupSize / 2 - 10;
        powerup.position.setVector(posX, posY);
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

    private void spawnGangOfBalls(Vector revealPosition) {
        double minimumDistance = GameConstants.screenWidth;
        GameObject closestBall = null;
        for (GameObject object: gameObjects) {
            if(object instanceof Ball) {
                double ballDistance = Math.hypot(object.getPosition().getX() - revealPosition.getX(),
                        object.getPosition().getY() - revealPosition.getY());
                if(ballDistance < minimumDistance) {
                    minimumDistance = ballDistance;
                    closestBall = object;
                }
            }
        }
        if(minimumDistance < GameConstants.rectangularBrickLength + GameConstants.ballSize) {
            System.out.println(((Ball) closestBall).getBallState().getClass().getSimpleName());
            for(int i = 0; i < GameConstants.gangOfBallsMultiplier; i++) {
                Ball ball = new Ball(revealPosition);
                ball.startMovement((360.0 / GameConstants.gangOfBallsMultiplier) * i, ((Ball) closestBall).getSpeed());
                trackObject(ball);
                balls.add(ball);
                if(((Ball) closestBall).getBallState() instanceof FireBallState) ball.setFireball();
                if(((Ball) closestBall).getBallState() instanceof ChemicalBallState) ball.setChemical();
            }
            removeObject(closestBall);
        }
    }

    public void shootLaserColumn(double x) {
        ArrayList<GameObject> objectColumn = new ArrayList<>();
        double endY = 0;
        for(GameObject object: gameObjects) {
            if(object instanceof Brick || object instanceof Alien) {
                if(Math.abs(object.getPosition().getX() - x) < object.getSize().getX() / 2.0) {
                    objectColumn.add(object);
                }
            }
        }
        objectColumn.sort(Comparator.comparingDouble(o -> o.getPosition().getY()));
        Collections.reverse(objectColumn);
        for(GameObject object: objectColumn) {
            if(object instanceof HalfMetalBrick) {
                endY = object.position.getY() + object.getSize().getY() / 2;
                break;
            }else {
                object.destroy();
            }
        }
        startAnimation("LaserAnimation", x, endY);
    }

    // ALIENS
    private void spawnAlien(WrapperContent content) {
        Alien alien = null;
        switch (content) {
            case COOPERATIVE_ALIEN:
                alien = new CooperativeAlien();
                break;
            case PROTECTING_ALIEN:
                alien = new ProtectingAlien();
                break;
            case REPAIRING_ALIEN:
                alien = new RepairingAlien();
                break;
            case DRUNK_ALIEN:
                alien = new DrunkAlien();
                break;
            default:
        }
        ArrayList<Alien> aliensCopy = new ArrayList<>(activeAliens);
        boolean overlaps = true;
        while(overlaps) {
            overlaps = false;
            double spawnX = GameConstants.alienSize / 2.0 +
                    random.nextDouble() * (GameConstants.screenWidth - GameConstants.alienSize);
            double spawnY = (GameConstants.screenHeight - GameConstants.paddleAreaHeight - GameConstants.alienSize / 2.0) -
                    random.nextDouble() * (GameConstants.alienAreaHeight - GameConstants.alienSize);
            assert alien != null;
            alien.setPosition(new Vector(spawnX, spawnY));
            for(GameObject object: aliensCopy) {
                if(PhysicsEngine.areColliding(object, alien)) {
                    overlaps = true;
                    break;
                }
            }
        }
        activeAliens.add(alien);
        aliens.add(alien);
        trackObject(alien);
    }

    // WIN AND LOSE CONDITIONS
    public void anyBallLeft() {
        if (balls.isEmpty()){
            lostLife();
        }
    }

    public void anyBricksLeft(){
        if (bricks.isEmpty()){
            winGame();
        }
    }

    public void lostLife() {
        if (lives != 1){
            lives = lives - 1;
            resetBall();
        }else{
            GameController.getInstance().stopAnimator();
            GameController.getInstance().showDeadDialog();
        }
    }

    private void winGame() {
        if (!alreadyWon){
            GameController.getInstance().stopAnimator();
            GameController.getInstance().showWinDialog();
            alreadyWon = true;
        }
    }

    // EXTRA
    public void invokeGodMode() {
        paddle.god();
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