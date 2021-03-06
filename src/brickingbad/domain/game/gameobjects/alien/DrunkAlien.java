package brickingbad.domain.game.gameobjects.alien;

import brickingbad.domain.game.*;
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
        this.dynamic = true;
        this.startBrickCount = Level.getInstance().getStartBrickCount();
        this.alienState = new ProtectingAlienState(this);
    }

    @Override
    public void performAction() {
        currentBrickCount = Level.getInstance().brickCount();
        if (currentBrickCount > startBrickCount * 0.7 && !(alienState instanceof CooperativeAlienState)) {
            alienState.finishAction();
            alienState = new CooperativeAlienState(this);
        } else if (currentBrickCount < startBrickCount * 0.3 && !(alienState instanceof ProtectingAlienState)) {
            alienState.finishAction();
            alienState = new RepairingAlienState(this);
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
            alienState = new RepairingAlienState(this);
        } else if((currentBrickCount > startBrickCount * 0.3 && currentBrickCount < startBrickCount * 0.4)
                || (currentBrickCount > startBrickCount * 0.6 && currentBrickCount < startBrickCount * 0.7)
                && !(alienState instanceof DrunkAlienState)) {
            alienState.finishAction();
            alienState = new DrunkAlienState(this);
        }
        alienState.performAction();
    }

}
