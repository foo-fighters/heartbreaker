package brickingbad.domain.game.listeners;

import brickingbad.domain.game.gameobjects.GameObject;

public interface LevelListener {
    void addObject(GameObject object);
    void removeObject(GameObject object);
}
