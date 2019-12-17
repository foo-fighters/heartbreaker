package brickingbad.domain.physics.alien;

import brickingbad.domain.game.gameobjects.GameObject;
import brickingbad.domain.game.gameobjects.alien.Alien;

public abstract class AlienState extends GameObject {

    protected Alien alien;

    public abstract void performAction();

    public void finishAction() {
    }

}