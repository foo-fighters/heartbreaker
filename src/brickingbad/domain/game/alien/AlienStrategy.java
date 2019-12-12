package brickingbad.domain.game.alien;

import brickingbad.domain.game.GameObject;

public abstract class AlienStrategy extends GameObject {
    abstract void performAction();
}