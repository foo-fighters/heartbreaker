package brickingbad.domain.physics.alien;

import brickingbad.domain.game.GameLogic;
import brickingbad.domain.game.Level;
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
        this.startTime = Level.getInstance().getTime();
        this.rowHeight = GameLogic.getBrickRowHeight();
        if(drunk) {
            Level.getInstance().startAnimation("DrunkAlienRowAnimation", rowHeight);
        }else {
            Level.getInstance().startAnimation("CooperativeAlienRowAnimation", rowHeight);
        }
    }

    @Override
    public void performAction() {
        long currentTime = Level.getInstance().getTime();
        if(currentTime - startTime > 1000 * cooldown) {
            Brick nextBrick = GameLogic.nextBrickInRow(rowHeight);
            if (nextBrick == null) {
                finishAction();
                if(drunk) {
                    rowHeight = GameLogic.getBrickRowHeight();
                    Level.getInstance().startAnimation("DrunkAlienRowAnimation", rowHeight);
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
            Level.getInstance().finishAnimation("DrunkAlienRowAnimation");
        }else {
            Level.getInstance().finishAnimation("CooperativeAlienRowAnimation");
        }
    }

}
