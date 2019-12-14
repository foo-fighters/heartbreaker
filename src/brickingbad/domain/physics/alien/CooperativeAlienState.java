package brickingbad.domain.physics.alien;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.alien.Alien;
import brickingbad.domain.game.brick.Brick;

public class CooperativeAlienState extends AlienState {

    private double cooldown = 1;
    private long startTime;
    private double rowHeight;
    private Alien alien;

    public CooperativeAlienState(Alien alien) {
        this.alien = alien;
        this.startTime = Game.getInstance().getTime();
        this.rowHeight = Game.getInstance().getBrickRowHeight();
        Game.getInstance().startAnimation("CooperativeAlienRowAnimation", rowHeight);
    }

    @Override
    public void performAction() {
        long currentTime = Game.getInstance().getTime();
        if(currentTime - startTime > 1000 * cooldown) {
            Brick nextBrick = Game.getInstance().nextBrickInRow(rowHeight);
            if (nextBrick == null) {
                finishAction();
                alien.destroy();
            }else {
                nextBrick.destroy();
                startTime = currentTime;
            }
        }
    }

    @Override
    public void finishAction() {
        Game.getInstance().finishAnimation("CooperativeAlienRowAnimation");
    }

}
