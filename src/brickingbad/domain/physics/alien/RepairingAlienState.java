package brickingbad.domain.physics.alien;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.alien.Alien;
import brickingbad.domain.game.alien.DrunkAlien;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.game.brick.BrickFactory;

import java.util.ArrayList;

public class RepairingAlienState extends AlienState {

    public boolean drunk;
    private double cooldown = GameConstants.repairingAlienCooldown;
    private long startTime;

    public RepairingAlienState(Alien alien) {
        this.alien = alien;
        this.drunk = alien instanceof DrunkAlien;
        this.startTime = Game.getInstance().getTime();
    }

    @Override
    public void performAction() {
        if(drunk) {
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
