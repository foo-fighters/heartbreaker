package brickingbad.domain.physics.alien;

import brickingbad.domain.game.GameObject;

public abstract class AlienState extends GameObject {
    public abstract void performAction();
    public void finishAction() {

    }
}