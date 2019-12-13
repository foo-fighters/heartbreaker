package brickingbad.domain.game;

import brickingbad.controller.GameController;
import brickingbad.domain.game.alien.*;
import brickingbad.domain.game.powerup.*;
import brickingbad.domain.game.border.*;
import brickingbad.domain.game.brick.*;
import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.PhysicsEngine;
import brickingbad.domain.physics.Vector;
import brickingbad.ui.game.animation.Animation;

import java.lang.reflect.InvocationTargetException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Game {

    private static Game instance;

    private Paddle paddle;
    private ArrayList<Ball> balls;
    private Ground ground;
    private ArrayList<Wall> walls;
    private ArrayList<Brick> bricks;
    private ArrayList<Alien> aliens;
    private ArrayList<GameObject> gameObjects;

    private int gridX = GameConstants.screenWidth / GameConstants.rectangularBrickLength;
    private int gridY = (int)GameConstants.brickAreaHeight / GameConstants.rectangularBrickThickness;
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

    private ArrayList<GameObjectListener> objectListeners;
    private ArrayList<ErrorListener> errorListeners;
    private ArrayList<AnimationListener> animationListeners;

    // CONSTRUCTION AND INITIALIZATION
    private Game() {
        objectListeners = new ArrayList<>();
        errorListeners = new ArrayList<>();
        animationListeners = new ArrayList<>();
        balls = new ArrayList<>();
        walls = new ArrayList<>();
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

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void initialize(boolean fromSave) {
        gameObjects = new ArrayList<>();

        alreadyWon = false;

        for (Brick brick : bricks) {
            removeObjectFromListeners(brick);
        }
        for (Ball ball : balls) {
            removeObjectFromListeners(ball);
        }
        for (Ball ball : balls) {
            removeObjectFromListeners(ball);
        }
        for (PowerUp powerup : storedPowerUps) {
            removeObjectFromListeners(powerup);
        }
        for (PowerUp powerup : activePowerUps) {
            removeObjectFromListeners(powerup);
        }

        bricks = new ArrayList<>();
        balls = new ArrayList<>();
        aliens = new ArrayList<>();
        activePowerUps = new ArrayList<>();
        storedPowerUps = new ArrayList<>();

        removeObjectFromListeners(paddle);

        Wall wall1 = new Wall(Direction.UP);
        Wall wall2 = new Wall(Direction.RIGHT);
        Wall wall3 = new Wall(Direction.LEFT);
        this.ground = new Ground();

        walls.add(wall1);
        walls.add(wall2);
        walls.add(wall3);

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
        trackObject(wall1);
        trackObject(wall2);
        trackObject(wall3);
        trackObject(this.ground);
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

    // LISTENER FUNCTIONS
    public void addObjectListener(GameObjectListener listener) {
        objectListeners.add(listener);
    }

    public void addErrorListener(ErrorListener err) {
        errorListeners.add(err);
    }

    public void addAnimationListener(AnimationListener anim) {
        animationListeners.add(anim);
    }

    private void removeObjectFromListeners(GameObject object) {
        objectListeners.forEach(listener -> listener.removeObject(object));
    }

    private void sendError(String err){
        errorListeners.forEach(errorListener -> {
            errorListener.showError(err);
        });
    }

    public void updateBalls(String stateModifier) {
        for(AnimationListener anim: animationListeners) {
            anim.updateBalls(stateModifier);
        }
    }

    public void crackHalfMetalBrick(HalfMetalBrick brick) {
        for(AnimationListener anim: animationListeners) {
            anim.crackHalfMetalBrick(brick);
        }
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
        for (GameObjectListener listener : objectListeners) {
            listener.addObject(object);
        }
    }

    public void removeObject(GameObject object) {
        removeObjectFromListeners(object);
        gameObjects.removeIf(obj -> obj.equals(object));
        if (object instanceof Brick) {
            bricks.removeIf(brick -> brick.equals(object));
        }
        if (object instanceof Ball) {
            balls.removeIf(ball -> ball.equals(object));
        }
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

    public void destroyBricksInRadius(Vector center, double radius) {
        ArrayList<GameObject> objectList = new ArrayList<>(gameObjects);
        double xdist;
        double ydist;
        for (GameObject object: objectList) {
            if(object instanceof Brick) {
                xdist = center.getX() - object.getPosition().getX();
                ydist = center.getY() - object.getPosition().getY();
                if(Math.hypot(xdist, ydist) < radius) {
                    object.destroy();
                }
            }
        }
        startAnimation("ExplosionAnimation", center, radius);
    }

    public void addBrick(Brick brick) {
        boolean overlaps = true;

        while (overlaps) {
            int x = ThreadLocalRandom.current().nextInt(brickGrid.length);
            int y = ThreadLocalRandom.current().nextInt(brickGrid[0].length);
            if (bricks.size() == 0) {
                overlaps = false;
            } else {
                overlaps = brickGrid[x][y];
            }
            if (!overlaps) {
                brickGrid[x][y] = true;
                brick.setPosition(new Vector((x + 0.5) * GameConstants.rectangularBrickLength,
                        GameConstants.menuAreaHeight + (y + 0.5) * GameConstants.rectangularBrickThickness));
            }
        }
        bricks.add(brick);
        trackObject(brick);
    }

    public double getBrickRowHeight() {
        boolean empty = true;
        double rowHeight = 0.0;
        while(empty) {
            int row = random.nextInt(gridX);
            rowHeight = GameConstants.menuAreaHeight + GameConstants.rectangularBrickThickness / 2.0 +
                    row * GameConstants.rectangularBrickThickness;
            for(GameObject object: gameObjects) {
                if(object.getPosition().getY() == rowHeight) {
                    empty = false;
                }
            }
        }
        return rowHeight;
    }

    public Brick nextBrickInRow(double rowHeight) {
        ArrayList<Brick> rowBricks = new ArrayList<>();
        ArrayList<GameObject> objectsCopy = new ArrayList<>(gameObjects);
        for(GameObject object: objectsCopy) {
            if(object instanceof Brick && object.getPosition().getY() == rowHeight) {
                rowBricks.add((Brick) object);
            }
        }
        Collections.sort(rowBricks, Comparator.comparingDouble(o -> o.getPosition().getX()));
        if(rowBricks.isEmpty()) return null;
        return rowBricks.get(0);
    }

    public void brickDestroyed() {
        score += 300/(PhysicsEngine.getInstance().getTimePassed()/1000);
        GameController.getInstance().setUIScore(score);
    }

    public void addBrickHorizontal() {
        boolean overlaps = true;
        int y = ThreadLocalRandom.current().nextInt(brickGrid[0].length);
        int x = GameConstants.rectangularBrickLength / 2;
        while(!((x + GameConstants.rectangularBrickLength) >= GameConstants.screenWidth)) {
            SimpleBrick brick = new SimpleBrick();
            while (overlaps) {
                if (bricks.size() == 0) {
                    overlaps = false;
                } else {
                    overlaps = brickGrid[x][y];
                }
                if (!overlaps) {
                    brickGrid[x][y] = true;
                    brick.setPosition(new Vector(x, y));
                }
                x += GameConstants.rectangularBrickLength;
            }
            bricks.add(brick);
            trackObject(brick);
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
            for(int i = 0; i < GameConstants.gangOfBallsMultiplier; i++) {
                Ball ball = new Ball(revealPosition);
                ball.startMovement((360.0 / GameConstants.gangOfBallsMultiplier) * i, ((Ball)closestBall).getSpeed());
                trackObject(ball);
                balls.add(ball);
            }
            removeObject(closestBall);
        }
    }

    public void shootLaserColumn(double x) {
        ArrayList<GameObject> objectColumn = new ArrayList<>();
        double endY = 0;

        for(GameObject object: gameObjects) {
            if(object instanceof Brick || object instanceof Alien) {
                if(Math.abs(object.getPosition().getX() - x) < GameConstants.rectangularBrickLength / 2.0) {
                    objectColumn.add(object);
                }
            }
        }
        Collections.sort(objectColumn, Comparator.comparingDouble(o -> o.getPosition().getY()));
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
        double spawnX = GameConstants.alienSize / 2.0 +
                random.nextDouble() * (GameConstants.screenWidth - GameConstants.alienSize);
        double spawnY = (GameConstants.screenHeight - GameConstants.paddleAreaHeight - GameConstants.alienSize / 2.0) -
                random.nextDouble() * (GameConstants.alienAreaHeight - GameConstants.alienSize);
        alien.setPosition(new Vector(spawnX, spawnY));
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

    public int getGridSize() {
        return gridX * gridY;
    }

    public int getStartBrickCount() {
        return startBrickCount;
    }
}