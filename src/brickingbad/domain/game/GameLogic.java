package brickingbad.domain.game;

import brickingbad.domain.game.brick.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GameLogic {

//    private static GameLogic instance;
//    private ArrayList<ErrorListener> errorListeners;
//
//    public static GameLogic getInstance() {
//        if (instance == null) {
//            instance = new GameLogic();
//        }
//        return instance;
//    }
//
//    private GameLogic() {
//        errorListeners = new ArrayList<>();
//    }
//
//    public void addErrorListener(ErrorListener err) {
//        errorListeners.add(err);
//    }
//
//    private void sendError(String err){
//        errorListeners.forEach(errorListener -> errorListener.showError(err));
//    }

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
}
