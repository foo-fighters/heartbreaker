package brickingbad.domain.game.alien;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.Shape;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.physics.Vector;

import java.util.ArrayList;
import java.util.Random;

public class CooperativeAlien extends Alien {
private Random rand;
private double h;
private double h_up;
private double h_down;
private boolean isDissappeared;

    public CooperativeAlien() {
        this.position= new Vector();
        this.shape= Shape.CIRCLE;
        this.velocity=new Vector();
        this.angle = 0.0;
        this.size = new Vector(GameConstants.alienSize, GameConstants.alienSize);
    }

    @Override
    void performAction() {
        //if h is aroun d the center of bricks
        ArrayList<GameObject> objects = new ArrayList<>(Game.getInstance().getObjects()) ;
       h = rand.nextDouble();
       h_down = h - GameConstants.rectangularBrickThickness/2;
       h_up = h + GameConstants.rectangularBrickThickness/2;
       for(GameObject object: objects) {
           if(object instanceof Brick){
               if(object.getPosition().getY() > h_down &&  object.getPosition().getY() < h_up){
                   object.destroy();
                   isDissappeared = true;
               }
           }

       }

    }

    boolean isDisappeared(boolean isDisappeared) {
        if (isDisappeared) {
            return true;
        }
    return false;
    }

    @Override
    public void doSelection() {
        this.performAction();
    }
}

