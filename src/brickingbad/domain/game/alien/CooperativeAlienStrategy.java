package brickingbad.domain.game.alien;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.brick.Brick;

public class CooperativeAlienStrategy extends AlienStrategy {

    private double cooldown = 1;
    private long startTime;
    private double rowHeight;
    private Alien alien;

    public CooperativeAlienStrategy(Alien alien) {
        this.startTime = Game.getInstance().getTime();
        this.rowHeight = Game.getInstance().getBrickRowHeight();
        this.alien = alien;
    }

    @Override
    void performAction() {
        long currentTime = Game.getInstance().getTime();
        if(currentTime - startTime > 1000 * cooldown) {
            Brick nextBrick = Game.getInstance().nextBrickInRow(rowHeight);
            if (nextBrick == null) {
                alien.destroy();
            }else {
                nextBrick.destroy();
                startTime = currentTime;
            }
        }
    }

}
