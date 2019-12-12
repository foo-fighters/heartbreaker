package brickingbad.domain.game.alien;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.Shape;
import brickingbad.domain.game.alien.Alien;
import brickingbad.domain.physics.Vector;

import java.util.Random;

public class ProtectingAlien extends Alien {

    public ProtectingAlien() {
        this.position= new Vector();
        this.velocity = new Vector();
        this.shape= Shape.RECTANGLE;
        this.angle = 0.0;
        this.size = new Vector(GameConstants.alienSize, GameConstants.alienSize);
        this.dynamic = true;
        this.currentStrategy = new ProtectingAlienStrategy();
        this.velocity.setVector(GameConstants.alienSpeed, 0.0);
    }

    @Override
    public void performAction() {

    }

    @Override
    public void collide(GameObject object) {
        if(object instanceof Ball) return;
        reflect();
    }

    public void reflect() {
        setVelocity(new Vector(-getVelocity().getX(), getVelocity().getY()));
    }

}
