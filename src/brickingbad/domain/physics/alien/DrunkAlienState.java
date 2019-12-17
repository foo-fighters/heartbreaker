package brickingbad.domain.physics.alien;

import brickingbad.domain.game.Level;
import brickingbad.domain.game.gameobjects.alien.Alien;

public class DrunkAlienState extends AlienState {

    private double cooldown = 5;
    private long startTime;

    public DrunkAlienState(Alien alien) {
        this.alien = alien;
        this.startTime = Level.getInstance().getTime();
    }

    @Override
    public void performAction() {
        long currentTime = Level.getInstance().getTime();
        if(currentTime - startTime > 1000 * cooldown) {
            alien.destroy();
        }
    }
}
