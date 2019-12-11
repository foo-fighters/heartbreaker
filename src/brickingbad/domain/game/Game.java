package brickingbad.domain.game;

import brickingbad.controller.EffectsController;
import brickingbad.controller.GameController;
import brickingbad.domain.game.alien.Alien;
import brickingbad.domain.game.alien.CooperativeAlien;
import brickingbad.domain.game.alien.ProtectingAlien;
import brickingbad.domain.game.alien.RepairingAlien;
import brickingbad.domain.game.powerup.*;
import brickingbad.domain.game.border.*;
import brickingbad.domain.game.brick.*;
import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.PhysicsEngine;
import brickingbad.domain.physics.Vector;

import java.time.Clock;
import java.util.ArrayList;
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

    private boolean[][] brickGrid;

    private int score;
    private int lives;
    private Clock gameClock;

    private ArrayList<WrapperContent> wrapperContentList;
    private ArrayList<PowerUp> storedPowerUps;
    private ArrayList<PowerUp> activePowerUps;
    private ArrayList<WrapperContent> activeAliens;
    private boolean alreadyWon;
    private static final Random random = new Random();

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
        gameObjects = new ArrayList<>();
        wrapperContentList = new ArrayList<>();
        activeAliens = new ArrayList<>();
        aliens = new ArrayList<>();
        gameClock = Clock.systemDefaultZone();

        int gridX = GameConstants.screenWidth / GameConstants.rectangularBrickLength;
        int gridY = (int)GameConstants.brickAreaHeight / GameConstants.rectangularBrickThickness;
        brickGrid = new boolean[gridX][gridY];
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
        gameObjects.add(object);
        gameObjects.sort(GameObject::compareTo);
        for (GameObjectListener listener : objectListeners) {
            listener.addObject(object);
        }
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

    public void resetBall() {
        Ball firstBall = new Ball(paddle.getBallStartPosition());
        balls.add(firstBall);
        paddle.getCurrentBalls().add(firstBall);
        trackObject(firstBall);
    }

    public void play() {
        for(GameObject object: gameObjects) {
            if(object instanceof Brick) {
                ((Brick) object).startMovement();
            }
        }
    }

    private void removeObjectFromListeners(GameObject object) {
        objectListeners.forEach(listener -> listener.removeObject(object));
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

    public void destroyBricksInRadius(Vector center, double radius) {
        ArrayList<GameObject> objectList = new ArrayList<>(gameObjects);
        double xdist;
        double ydist;
        for (GameObject object: objectList) {
            if(object instanceof Brick) {
                xdist = center.getX() - object.getPosition().getX();
                ydist = center.getY() - object.getPosition().getY();
                if(Math.hypot(xdist, ydist) < radius) {
                    object.destroy(false);
                }
            }
        }
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

    public void addBall(Ball ball) {
        balls.add(ball);
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

    public void invokeGodMode() {
        paddle.god();
    } 

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
            if(storedPowerUps.stream().map(PowerUp::getName).collect(Collectors.toList()).contains(content)
                    || activePowerUps.stream().map(PowerUp::getName).collect(Collectors.toList()).contains(content)
                    || activeAliens.contains(content)) {
                return;
            }
            if(content.ordinal() < 6) {
                spawnPowerup(content, revealPosition);
            }else {
                spawnAlien(content);
            }
        }
    }

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
            default:
        }
        aliens.add(alien);
        trackObject(alien);
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

    public void storePowerUp(PowerUp powerup) {
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
                storedPowerUps.remove(powerup);
                activePowerUps.add(powerup);
                powerup.activate();
            }
        }
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

    public void addAlien(Alien alien) {
        aliens.add(alien);
        trackObject(alien);
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

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public Ground getGround() {
        return ground;
    }

    public void lostLife() {
        if (lives != 1){
            EffectsController.getInstance().playAudio("lifeLost");
            lives = lives - 1;
            resetBall();

            if (lives == 1){
                EffectsController.getInstance().startHeartBeat();
            }

        }else{
            EffectsController.getInstance().stopHeartBeat();
            GameController.getInstance().stopAnimator();
            GameController.getInstance().showDeadDialog();
        }
    }

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

    private void winGame() {
        if (!alreadyWon){
            GameController.getInstance().stopAnimator();
            GameController.getInstance().showWinDialog();
            alreadyWon = true;
        }

    }

    public void brickDestroyed() {
        score += 300/(PhysicsEngine.getInstance().getTimePassed()/1000);
        GameController.getInstance().setUIScore(score);
    }
}