package brickingbad.domain.physics.alien;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.alien.Alien;
import brickingbad.domain.game.alien.DrunkAlien;
import brickingbad.domain.game.brick.Brick;

public class CooperativeAlienState extends AlienState {

    private boolean drunk;
    private double cooldown = GameConstants.cooperativeAlienCooldown;
    private long startTime;
    private double rowHeight;

    public CooperativeAlienState(Alien alien) {
        this.alien = alien;
        this.drunk = alien instanceof DrunkAlien;
        this.startTime = Game.getInstance().getTime();
        this.rowHeight = Game.getInstance().getBrickRowHeight();
        if(drunk) {
            Game.getInstance().startAnimation("DrunkAlienRowAnimation", rowHeight);
        }else {
            Game.getInstance().startAnimation("CooperativeAlienRowAnimation", rowHeight);
        }
    }

    @Override
    public void performAction() {
        long currentTime = Game.getInstance().getTime();
        if(currentTime - startTime > 1000 * cooldown) {
            Brick nextBrick = Game.getInstance().nextBrickInRow(rowHeight);
            if (nextBrick == null) {
                finishAction();
                if(drunk) {
                    rowHeight = Game.getInstance().getBrickRowHeight();
                    Game.getInstance().startAnimation("DrunkAlienRowAnimation", rowHeight);
                }else {
                    alien.destroy();
                }
            }else {
                nextBrick.destroy();
                startTime = currentTime;
            }
        }
    }

    @Override
    public void finishAction() {
        if(drunk) {
            Game.getInstance().finishAnimation("DrunkAlienRowAnimation");
        }else {
            Game.getInstance().finishAnimation("CooperativeAlienRowAnimation");
        }
    }

}
