package brickingbad.ui.game;

import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.GameObjectListener;
import brickingbad.ui.BrickingBadFrame;

public class UIGameObjectHandler implements GameObjectListener {

    @Override
    public void addUIObject(GameObject object) {
        BrickingBadFrame.getInstance().getCurrentPanel();
    }

    @Override
    public void removeUIObject(GameObject object) {

    }

}
