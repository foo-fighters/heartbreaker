package brickingbad.domain.physics.alien;

import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.alien.Alien;

public abstract class AlienState extends GameObject {

    protected Alien alien;

    public abstract void performAction();

    public void finishAction() {
    }

}