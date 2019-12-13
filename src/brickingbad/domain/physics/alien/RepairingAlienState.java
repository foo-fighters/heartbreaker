package brickingbad.domain.physics.alien;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.game.brick.BrickFactory;

import java.util.ArrayList;

public class RepairingAlienState extends AlienState {

    public boolean horizontal;
    private double cooldown = 5;
    private long startTime;

    public RepairingAlienState(boolean horizontal) {
        this.startTime = Game.getInstance().getTime();
        this.horizontal = horizontal;
    }

    @Override
    public void performAction() {
        if(horizontal) {
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
