package brickingbad.domain.game.listeners;

import brickingbad.domain.game.gameobjects.GameObject;

public interface GameListener {
    void addObject(GameObject object);
    void removeObject(GameObject object);
}
