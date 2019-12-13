package brickingbad.domain.game.alien;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.game.brick.BrickFactory;

import java.util.ArrayList;

public class RepairingAlienStrategy extends AlienStrategy {

    public boolean horizontalStrategy;
    private double cooldown = 5;
    private long startTime;

    public RepairingAlienStrategy() {
        this.startTime = Game.getInstance().getTime();
    }

    @Override
    public void performAction() {
        if(horizontalStrategy) {
            Game.getInstance().addBrickHorizontal();
        }else {
            long currentTime = Game.getInstance().getTime();
            if(currentTime - startTime > 1000 * cooldown) {
                ArrayList<Brick> simpleBricks = BrickFactory.getInstance().createSimpleBricks(1);
                Brick newBrick = simpleBricks.get(0);
                Game.getInstance().addBrick(newBrick);
                startTime = currentTime;
            }
        }
    }

}
