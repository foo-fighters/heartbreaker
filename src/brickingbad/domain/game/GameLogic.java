package brickingbad.domain.game;

import brickingbad.domain.game.gameobjects.Ball;
import brickingbad.domain.game.gameobjects.alien.Alien;
import brickingbad.domain.game.gameobjects.brick.*;
import brickingbad.domain.game.gameobjects.GameObject;
import brickingbad.domain.physics.Vector;
import brickingbad.domain.physics.ball.ChemicalBallState;
import brickingbad.domain.physics.ball.FireBallState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class GameLogic {

    private static ArrayList<GameObject> objectsCopy() {
        return new ArrayList<>(Level.getInstance().getObjects());
    }

    public static String checkBrickCount() {
        AtomicInteger simpleBrickCount = new AtomicInteger();
        AtomicInteger halfMetalBrickCount = new AtomicInteger();
        AtomicInteger mineBrickCount = new AtomicInteger();
        AtomicInteger wrapperBrickCount = new AtomicInteger();

        ArrayList<Brick> bricks = new ArrayList<>(Level.getInstance().getBricks());
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

        if (simpleBrickCount.get() < GameConstants.simpleBrickLimit) {
            warning = warning + (GameConstants.simpleBrickLimit - simpleBrickCount.get()) + " Simple Bricks, ";
        }

        if (halfMetalBrickCount.get() < GameConstants.halfMetalBrickLimit) {
            warning = warning + (GameConstants.halfMetalBrickLimit - halfMetalBrickCount.get()) + " Half Metal Bricks, ";
        }

        if (mineBrickCount.get() < GameConstants.mineBrickLimit) {
            warning = warning + (GameConstants.mineBrickLimit - mineBrickCount.get()) + " Mine Bricks, ";
        }

        if (wrapperBrickCount.get() < GameConstants.wrapperBrickLimit) {
            warning = warning + (GameConstants.wrapperBrickLimit - wrapperBrickCount.get()) + " Wrapper Bricks, ";
        }

        if (!warning.equals("")) {
            warning = warning.substring(0, warning.length() - 2);
            warning = warning + " more are needed to start the game!";
        }
        System.out.println(warning);
        return warning;
    }

    public static Vector getClosestGridLocation(Vector pos) {
        int indX = Math.floorDiv((int) pos.getX(), GameConstants.rectangularBrickLength);
        int indY = Math.floorDiv((int) (pos.getY() - GameConstants.menuAreaHeight), GameConstants.rectangularBrickThickness);
        return new Vector(indX, indY);
    }

    public static double getBrickRowHeight() {
        boolean empty = true;
        double rowHeight = 0.0;
        int count = 0;
        while(empty && count < 1000) {
            int row = ThreadLocalRandom.current().nextInt(Level.getInstance().getGridX());
            rowHeight = GameConstants.menuAreaHeight + GameConstants.rectangularBrickThickness / 2.0 +
                    row * GameConstants.rectangularBrickThickness;
            for(GameObject object: objectsCopy()) {
                if (object.getPosition().getY() == rowHeight) {
                    empty = false;
                    break;
                }
            }
            count++;
        }
        return rowHeight;
    }

    public static Brick nextBrickInRow(double rowHeight) {
        ArrayList<Brick> rowBricks = new ArrayList<>();
        for(GameObject object: objectsCopy()) {
            if(object instanceof Brick && object.getPosition().getY() == rowHeight) {
                rowBricks.add((Brick) object);
            }
        }
        rowBricks.sort(Comparator.comparingDouble(o -> o.getPosition().getX()));
        if(rowBricks.isEmpty()) return null;
        return rowBricks.get(0);
    }

    public static void destroyBricksInRadius(Vector center, double radius) {
        double xdist;
        double ydist;
        ArrayList<Vector> mineBrickCenters = new ArrayList<>();
        for (GameObject object: objectsCopy()) {
            if(object instanceof Brick) {
                xdist = center.getX() - object.getPosition().getX();
                ydist = center.getY() - object.getPosition().getY();
                if(Math.hypot(xdist, ydist) < radius) {
                    if(object instanceof MineBrick) {
                        mineBrickCenters.add(object.getPosition());
                        Level.getInstance().removeObject(object);
                    }else {
                        object.destroy();
                    }
                }
            }
        }
        mineBrickCenters.forEach(c -> destroyBricksInRadius(c, GameConstants.mineBrickExplosionRadius));
        Level.getInstance().startAnimation("ExplosionAnimation", center, radius);
    }

    public static void shootLaserColumn(double x) {
        ArrayList<GameObject> objectColumn = new ArrayList<>();
        double endY = 0;
        for(GameObject object: objectsCopy()) {
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
                endY = object.getPosition().getY() + object.getSize().getY() / 2;
                break;
            }else {
                object.destroy();
            }
        }
        Level.getInstance().startAnimation("LaserAnimation", x, endY);
    }

    public static void invokeGodMode() {
        Level.getInstance().getPaddle().god();
    }

    public static void destroyAllBalls() {
        for(GameObject object: objectsCopy()) {
            if(object instanceof Ball) {
                object.destroy();
            }
        }
    }
}
