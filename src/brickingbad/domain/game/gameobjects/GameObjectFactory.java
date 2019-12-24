package brickingbad.domain.game.gameobjects;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Level;
import brickingbad.domain.game.WrapperContent;
import brickingbad.domain.game.gameobjects.alien.*;
import brickingbad.domain.game.gameobjects.brick.Brick;
import brickingbad.domain.game.gameobjects.brick.BrickFactory;
import brickingbad.domain.game.gameobjects.brick.HalfMetalBrick;
import brickingbad.domain.game.gameobjects.brick.SimpleBrick;
import brickingbad.domain.game.gameobjects.powerup.*;
import brickingbad.domain.physics.PhysicsEngine;
import brickingbad.domain.physics.Vector;
import brickingbad.domain.physics.ball.ChemicalBallState;
import brickingbad.domain.physics.ball.FireBallState;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GameObjectFactory {

    private static GameObjectFactory instance;

    public static GameObjectFactory getInstance() {
        if (instance == null) {
            instance = new GameObjectFactory();
        }
        return instance;
    }

    private GameObjectFactory() {

    }

    private static ArrayList<GameObject> objectsCopy() {
        return new ArrayList<>(Level.getInstance().getObjects());
    }

    public void createBricks(int simple, int halfMetal, int mine, int wrapper) {
        if (Level.getInstance().getBricks().size() + simple + halfMetal + mine + wrapper
                <= Level.getInstance().getGridX() * Level.getInstance().getGridY()) {

            ArrayList<Brick> simpleBricks = BrickFactory.getInstance().createSimpleBricks(simple);
            simpleBricks.forEach((this::addBrick));

            ArrayList<Brick> halfMetalBricks = BrickFactory.getInstance().createHalfMetalBricks(halfMetal);
            halfMetalBricks.forEach((this::addBrick));

            ArrayList<Brick> mineBricks = BrickFactory.getInstance().createMineBricks(mine);
            mineBricks.forEach((this::addBrick));

            ArrayList<Brick> wrapperBricks = BrickFactory.getInstance().createWrapperBricks(wrapper);
            wrapperBricks.forEach((this::addBrick));
        }
    }

    public void addBrick(Brick brick) {
        boolean bottomFull = true;
        boolean overlaps = true;
        int count = 0;
        int x = 0;
        int y = 0;
        if(brick instanceof HalfMetalBrick) {
            y = Level.getInstance().getGridY() - 1;
            for (int i = 2; i < Level.getInstance().getGridX() - 2; i++) {
                if(!Level.getInstance().getBrickGrid()[i][y]) bottomFull = false;
            }
        }
        while (overlaps && count < 1000) {
            if(brick instanceof HalfMetalBrick && !bottomFull) {
                x = ThreadLocalRandom.current().nextInt(2, Level.getInstance().getGridX() - 2);
            }else {
                x = ThreadLocalRandom.current().nextInt(Level.getInstance().getGridX());
                y = ThreadLocalRandom.current().nextInt(Level.getInstance().getGridY() - 1);
            }
            overlaps = Level.getInstance().getBrickGrid()[x][y];
            if (!overlaps) {
                Level.getInstance().getBrickGrid()[x][y] = true;
                brick.setPosition(new Vector((x + 0.5) * GameConstants.rectangularBrickLength,
                        GameConstants.menuAreaHeight + (y + 0.5) * GameConstants.rectangularBrickThickness));
                brick.setCellX(x);
                brick.setCellY(y);
            }
            count++;
        }
        if(!overlaps) Level.getInstance().addObject(brick);
    }

    public void addBrickManual(Brick brick) {
        Level.getInstance().addObject(brick);
    }

    public void addBrickHorizontal() {
        boolean overlaps;
        int y = ThreadLocalRandom.current().nextInt(Level.getInstance().getGridY());
        int x = GameConstants.rectangularBrickLength / 2;
        while(x <= GameConstants.screenWidth) {
            SimpleBrick brick = new SimpleBrick();
            brick.setPosition(new Vector(x, GameConstants.menuAreaHeight + (y + 0.5) * GameConstants.rectangularBrickThickness));
            overlaps = false;
            for(GameObject object: objectsCopy()) {
                if(PhysicsEngine.getInstance().areColliding(object, brick)) {
                    overlaps = true;
                    break;
                }
            }
            if(!overlaps) {
                Level.getInstance().addObject(brick);
            }
            x += GameConstants.rectangularBrickLength;
        }
    }

    public void spawnAlien(WrapperContent content) {
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
        assert alien != null;
        ArrayList<Alien> aliensCopy = new ArrayList<>(Level.getInstance().getActiveAliens());
        boolean overlaps = true;
        while(overlaps) {
            overlaps = false;
            double spawnX = GameConstants.alienSize / 2.0 +
                    ThreadLocalRandom.current().nextDouble() * (GameConstants.screenWidth - GameConstants.alienSize);
            double spawnY = (GameConstants.screenHeight - GameConstants.paddleAreaHeight - GameConstants.alienSize / 2.0) -
                    ThreadLocalRandom.current().nextDouble() * (GameConstants.alienAreaHeight - GameConstants.alienSize);
            alien.setPosition(new Vector(spawnX, spawnY));
            for(GameObject object: aliensCopy) {
                if(PhysicsEngine.getInstance().areColliding(object, alien)) {
                    overlaps = true;
                    break;
                }
            }
        }
        Level.getInstance().addObject(alien);
    }

    public void spawnPowerup(WrapperContent content, Vector revealPosition) {
        PowerUp power = null;
        switch (content) {
            case FIREBALL:
                power = new Fireball(revealPosition);
                break;
            case CHEMICAL_BALL:
                power = new ChemicalBall(revealPosition);
                break;
            case DESTRUCTIVE_LASER_GUN:
                power = new DestructiveLaserGun(revealPosition);
                break;
            case MAGNET:
                power = new Magnet(revealPosition);
                break;
            case TALLER_PADDLE:
                power = new TallerPaddle(revealPosition);
                break;
            case GANG_OF_BALLS:
                spawnGangOfBalls(revealPosition);
                return;
            default:
        }
        assert power != null;
        Level.getInstance().addObject(power);
    }

    private static void spawnGangOfBalls(Vector revealPosition) {
        double minimumDistance = GameConstants.screenWidth;
        GameObject closestBall = null;
        for (GameObject object: objectsCopy()) {
            if(object instanceof Ball) {
                double ballDistance = Math.hypot(object.getPosition().getX() - revealPosition.getX(),
                        object.getPosition().getY() - revealPosition.getY());
                if(ballDistance < minimumDistance) {
                    minimumDistance = ballDistance;
                    closestBall = object;
                }
            }
        }
        if(minimumDistance < (GameConstants.rectangularBrickLength + GameConstants.ballSize) * 0.6) {
            for(int i = 0; i < GameConstants.gangOfBallsMultiplier; i++) {
                Ball ball = new Ball(revealPosition);
                ball.startMovement((360.0 / GameConstants.gangOfBallsMultiplier) * i, ((Ball) closestBall).getSpeed());
                Level.getInstance().addObject(ball);
                if(((Ball) closestBall).getBallState() instanceof FireBallState) ball.setFireball();
                if(((Ball) closestBall).getBallState() instanceof ChemicalBallState) ball.setChemical();
            }
            closestBall.destroy();
        }
    }
}
