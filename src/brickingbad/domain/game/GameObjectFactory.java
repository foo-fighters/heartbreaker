package brickingbad.domain.game;

import brickingbad.domain.game.alien.*;
import brickingbad.domain.game.brick.Brick;
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
        ArrayList<Alien> aliensCopy = new ArrayList<>(Level.getInstance().getActiveAliens());
        boolean overlaps = true;
        while(overlaps) {
            overlaps = false;
            double spawnX = GameConstants.alienSize / 2.0 +
                    ThreadLocalRandom.current().nextDouble() * (GameConstants.screenWidth - GameConstants.alienSize);
            double spawnY = (GameConstants.screenHeight - GameConstants.paddleAreaHeight - GameConstants.alienSize / 2.0) -
                    ThreadLocalRandom.current().nextDouble() * (GameConstants.alienAreaHeight - GameConstants.alienSize);
            assert alien != null;
            alien.setPosition(new Vector(spawnX, spawnY));
            for(GameObject object: aliensCopy) {
                if(PhysicsEngine.areColliding(object, alien)) {
                    overlaps = true;
                    break;
                }
            }
        }
        Level.getInstance().getActiveAliens().add(alien);
        Level.getInstance().addObject(alien);
    }
}
