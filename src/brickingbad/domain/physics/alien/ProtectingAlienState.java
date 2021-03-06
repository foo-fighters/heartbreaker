package brickingbad.domain.physics.alien;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.gameobjects.alien.Alien;
import brickingbad.domain.physics.Vector;

public class ProtectingAlienState extends AlienState {

    public ProtectingAlienState(Alien alien) {
        this.alien = alien;
        this.alien.setVelocity(new Vector(GameConstants.alienSpeed, 0.0));
    }

    @Override
    public void performAction() {

    }

    @Override
    public void finishAction() {
        alien.setVelocity(new Vector());
    }
}
