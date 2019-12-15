package brickingbad.domain.game.listeners;

import brickingbad.domain.game.GameObject;

public interface GameListener {
    void addObject(GameObject object);
    void removeObject(GameObject object);
}
