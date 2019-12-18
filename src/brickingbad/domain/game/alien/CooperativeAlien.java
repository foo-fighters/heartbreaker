package brickingbad.domain.game.alien;

import brickingbad.controller.EffectsController;
import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Shape;
import brickingbad.domain.game.WrapperContent;
import brickingbad.domain.physics.Vector;
import brickingbad.domain.physics.alien.CooperativeAlienState;

public class CooperativeAlien extends Alien {

    public CooperativeAlien() {
        this.position= new Vector();
        this.shape= Shape.RECTANGLE;
        this.velocity=new Vector();
        this.angle = 0.0;
        this.size = new Vector(GameConstants.alienSize, GameConstants.alienSize);
        this.alienState = new CooperativeAlienState(this);
        this.name = WrapperContent.COOPERATIVE_ALIEN;
        EffectsController.getInstance().playAudio("alienSpawn");
    }

    @Override
    public void destroy() {
        Game.getInstance().finishAnimation("CooperativeAlienRowAnimation");
        super.destroy();
    }

}
