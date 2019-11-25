package brickingbad.domain.game.border;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.Shape;
import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.Vector;

public class Wall extends GameObject {

    public Wall(Direction direction) {
        shape = Shape.RECTANGLE;
        velocity = new Vector();
        angle = 0.0;
        if(direction == Direction.LEFT){
            position = new Vector(-5.0, GameConstants.screenHeight / 2.0);
            size = new Vector(10.0, GameConstants.screenHeight);
        }else if(direction == Direction.RIGHT){
            position = new Vector(GameConstants.screenWidth + 5.0, GameConstants.screenHeight / 2.0);
            size = new Vector(10.0, GameConstants.screenHeight);
        }else if (direction == Direction.UP){
            position = new Vector(GameConstants.screenWidth / 2.0, GameConstants.menuAreaHeight - 5.0);
            size = new Vector(GameConstants.screenWidth, 10.0);
        }
    }

}

