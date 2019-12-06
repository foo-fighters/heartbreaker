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
    private boolean isDestroyed;
    private double y;
    private double x;


    public CooperativeAlien() {
        this.position= new Vector();
        this.shape= Shape.CIRCLE;
        this.velocity=new Vector();
        this.angle = 0.0;
        this.size = new Vector(GameConstants.alienSize, GameConstants.alienSize);
        rand = new Random();
        x =  (rand.nextInt(GameConstants.screenWidth - GameConstants.alienSize) + GameConstants.alienSize/2)/10;
        y = (rand.nextInt((int) GameConstants.alienAreaHeight) + GameConstants.brickAreaHeight + GameConstants.menuAreaHeight )/10;//should be in the brick area
        position.setVector(x,y);
    }

    @Override
    void performAction() {
        //if h is aroun d the center of bricks
       isDestroyed = false;


       while(!isDestroyed){//at least one brick should be disappeared
           ArrayList<GameObject> objects = new ArrayList<>(Game.getInstance().getObjects()) ;
           h = (rand.nextInt((int) (GameConstants.brickAreaHeight - GameConstants.rectangularBrickThickness/2)) + GameConstants.menuAreaHeight +  GameConstants.rectangularBrickThickness/2)/10;//should be in the brick area
           h_down = h - GameConstants.rectangularBrickThickness/2;
           h_up = h + GameConstants.rectangularBrickThickness/2;
           for(GameObject object: objects) {
               if (object instanceof Brick) {
                   if (object.getPosition().getY() > h_down && object.getPosition().getY() <= h_up) {//if rand is in the middle, (the center of bricks are in the bounds of the range random so destroy upper one <=) destroy
                       object.destroy();
                       isDestroyed = true;
                   }
               }
            }

       }
    }
}

