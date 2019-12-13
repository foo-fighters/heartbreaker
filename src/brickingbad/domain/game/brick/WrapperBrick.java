package brickingbad.domain.game.brick;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Shape;
import brickingbad.domain.physics.Vector;

public class WrapperBrick extends Brick {

    public WrapperBrick() {
        this.position = new Vector();
        this.shape = Shape.RECTANGLE;
        this.size = new Vector(GameConstants.rectangularBrickLength, GameConstants.rectangularBrickThickness);
        this.velocity = new Vector();
        this.angle = 0.0;
        this.name = "WrapperBrick";

        Game.getInstance().addWrapperContent();
    }

    public void destroy() {
        super.destroy();
        Game.getInstance().revealWrapperContent(position);
    }

    @Override
    public boolean equals(Object obj) {
        WrapperBrick brick = (WrapperBrick) obj;
        if(this.name.equals(brick.name)){
            return true;
        }else{
            return false;
        }
    }


}
