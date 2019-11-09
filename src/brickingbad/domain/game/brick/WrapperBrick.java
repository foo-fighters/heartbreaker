package brickingbad.domain.game.brick;

import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.brick.Brick;

public class WrapperBrick extends Brick {

    private GameObject content;

    public GameObject getContent() {
        return content;
    }

    public void setContent(GameObject content) {
        this.content = content;
    }

    public WrapperBrick(GameObject content) {
        this.content = content;
    }
}
