package brickingbad.domain.game.alien;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Shape;
import brickingbad.domain.game.WrapperContent;
import brickingbad.domain.physics.Vector;
import brickingbad.domain.physics.alien.CooperativeAlienState;
import brickingbad.domain.physics.alien.DrunkAlienState;
import brickingbad.domain.physics.alien.ProtectingAlienState;
import brickingbad.domain.physics.alien.RepairingAlienState;

public class DrunkAlien extends Alien {

    private int startBrickCount;
    private int currentBrickCount;

    public DrunkAlien() {
        this.position= new Vector();
        this.shape= Shape.RECTANGLE;
        this.velocity=new Vector();
        this.angle = 0.0;
        this.size = new Vector(GameConstants.alienSize, GameConstants.alienSize);
        this.name = WrapperContent.DRUNK_ALIEN;
        this.startBrickCount = Game.getInstance().getStartBrickCount();
        this.alienState = new ProtectingAlienState(this);
    }

    @Override
    public void performAction() {
        System.out.println(alienState.getClass().getSimpleName());
        currentBrickCount = Game.getInstance().brickCount();
        if (currentBrickCount > startBrickCount * 0.7 && !(alienState instanceof CooperativeAlienState)) {
            alienState.finishAction();
            alienState = new CooperativeAlienState(this);
        } else if (currentBrickCount < startBrickCount * 0.3 && !(alienState instanceof ProtectingAlienState)) {
            alienState.finishAction();
            alienState = new RepairingAlienState(true);
            alienState.performAction();
            alienState.finishAction();
            alienState = new ProtectingAlienState(this);
        } else if (currentBrickCount > startBrickCount * 0.4 && currentBrickCount < startBrickCount * 0.5
                && !(alienState instanceof ProtectingAlienState)) {
            alienState.finishAction();
            alienState = new ProtectingAlienState(this);
        } else if (currentBrickCount > startBrickCount * 0.5 && currentBrickCount < startBrickCount * 0.6
                && !(alienState instanceof RepairingAlienState)) {
            alienState.finishAction();
            alienState = new RepairingAlienState(false);
        } else if((currentBrickCount > startBrickCount * 0.3 && currentBrickCount < startBrickCount * 0.4)
                || (currentBrickCount > startBrickCount * 0.6 && currentBrickCount < startBrickCount * 0.7)
                && !(alienState instanceof DrunkAlienState)){
            alienState.finishAction();
            alienState = new DrunkAlienState(this);
        }
        alienState.performAction();
    }

}
