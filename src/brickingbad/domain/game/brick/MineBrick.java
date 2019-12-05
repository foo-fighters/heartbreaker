package brickingbad.domain.game.brick;

import brickingbad.controller.EffectsController;
import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.Shape;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.physics.Vector;

import java.util.ArrayList;

public class MineBrick extends Brick {

    public MineBrick() {
        this.position = new Vector();
        this.shape = Shape.CIRCLE;
        this.size = new Vector(GameConstants.circularBrickSize, GameConstants.circularBrickSize);
        this.velocity = new Vector();
        this.angle = 0.0;
    }

    @Override
    public void destroy(boolean destroyedByBall) {
        if (destroyedByBall){
            EffectsController.getInstance().playAudio("mineBrick");
        }
        super.destroy();
        Game.getInstance().destroyBricksInRadius(position, GameConstants.mineBrickExplosionRadius);
        EffectsController.getInstance().showMineBrickExplodeEffect(position.getX(), position.getY());
    }

}
