package brickingbad.domain.game.gameobjects;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameLogic;
import brickingbad.domain.game.Level;
import brickingbad.domain.game.WrapperContent;
import brickingbad.domain.game.gameobjects.alien.*;
import brickingbad.domain.game.gameobjects.brick.Brick;
import brickingbad.domain.game.gameobjects.brick.SimpleBrick;
import brickingbad.domain.game.powerup.*;
import brickingbad.domain.physics.PhysicsEngine;
import brickingbad.domain.physics.Vector;

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

    public void addBrick(Brick brick) {
        boolean overlaps = true;
        int count = 0;
        while (overlaps && count < 1000) {
            int x = ThreadLocalRandom.current().nextInt(Level.getInstance().getGridX());
            int y = ThreadLocalRandom.current().nextInt(Level.getInstance().getGridY());
            if (Level.getInstance().brickCount() == 0) {
                overlaps = false;
            } else {
                overlaps = Level.getInstance().getBrickGrid()[x][y];
            }
            if (!overlaps) {
                Level.getInstance().getBrickGrid()[x][y] = true;
                brick.setPosition(new Vector((x + 0.5) * GameConstants.rectangularBrickLength,
                        GameConstants.menuAreaHeight + (y + 0.5) * GameConstants.rectangularBrickThickness));
                brick.setCellX(x);
                brick.setCellY(y);
            }
            count++;
        }
        Level.getInstance().addObject(brick);
    }

    public void addBrickHorizontal() {
        boolean overlaps = false;
        int y = ThreadLocalRandom.current().nextInt(Level.getInstance().getGridY());
        int x = GameConstants.rectangularBrickLength / 2;
        while(x <= GameConstants.screenWidth) {
            SimpleBrick brick = new SimpleBrick();
            brick.setPosition(new Vector(x, y));
            ArrayList<GameObject> objectsCopy = new ArrayList<>(Level.getInstance().getObjects());
            for(GameObject object: objectsCopy) {
                if(PhysicsEngine.areColliding(object, brick)) {
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
                if(PhysicsEngine.areColliding(object, alien)) {
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
                GameLogic.spawnGangOfBalls(revealPosition);
                return;
            default:
        }
        assert power != null;
        Level.getInstance().addObject(power);
    }
}
