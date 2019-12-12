package brickingbad.domain.game.alien;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Shape;
import brickingbad.domain.physics.Vector;

import java.util.Random;

public class ProtectingAlienStrategy extends AlienStrategy {

    private Random rand;
    private double y;
    private double x;

    public ProtectingAlienStrategy() {
        this.position= new Vector();
        this.shape= Shape.CIRCLE;
        this.shape= Shape.RECTANGLE;
        this.velocity=new Vector();
        this.angle = 0.0;
        this.size = new Vector(GameConstants.alienSize, GameConstants.alienSize);
        velocity.setVector(3 * GameConstants.paddleLength/ 2, 0 );
        rand = new Random();
        x = (rand.nextInt(GameConstants.screenWidth - GameConstants.alienSize) + GameConstants.alienSize/2)/10;
        y = ((rand.nextInt((int) GameConstants.alienAreaHeight - GameConstants.alienSize) + GameConstants.brickAreaHeight + GameConstants.menuAreaHeight
                + GameConstants.alienSize/2)/10)*10;//should be in the brick area
        position.setVector(x,y);
    }

    @Override
    void performStrategy(){
    }


}
