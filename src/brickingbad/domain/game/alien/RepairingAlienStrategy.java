package brickingbad.domain.game.alien;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.brick.BrickFactory;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RepairingAlienStrategy extends AlienStrategy {

    public boolean horizontalStrategy;
    public Timer repairTimer = new Timer();

    @Override
    public void performAction() {
        if(horizontalStrategy) {
            Game.getInstance().addBrickHorizontal();
        }else {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    BrickFactory.getInstance().createSimpleBricks(1);
                }
            };
            repairTimer.schedule(task, 0, GameConstants.repairingAlienCooldown);
        }
    }

}
