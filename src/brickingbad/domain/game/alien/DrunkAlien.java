package brickingbad.domain.game.alien;

import brickingbad.domain.game.Game;


public class DrunkAlien extends Alien {
 private int brickCount = Game.getInstance().getBrickCount();
 private int startCount= Game.getInstance().startBrickCount;
 @Override
    void performAction() {
    if(startCount*0.7 >= brickCount){

    }

    }
}
