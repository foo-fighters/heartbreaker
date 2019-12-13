package brickingbad.domain.physics.alien;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.alien.Alien;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.game.brick.BrickFactory;

import java.util.ArrayList;

public class DrunkAlienState extends AlienState {

    private Alien alien;
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
