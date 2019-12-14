package brickingbad.domain.physics.alien;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.alien.Alien;

public class DrunkAlienState extends AlienState {

    private double cooldown = 5;
    private long startTime;

    public DrunkAlienState(Alien alien) {
        this.alien = alien;
        this.startTime = Game.getInstance().getTime();
    }

    @Override
    public void performAction() {
        long currentTime = Game.getInstance().getTime();
        if(currentTime - startTime > 1000 * cooldown) {
            alien.destroy();
        }
    }
}
