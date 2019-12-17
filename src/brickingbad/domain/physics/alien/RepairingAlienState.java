package brickingbad.domain.physics.alien;

import brickingbad.domain.game.gameobjects.GameObjectFactory;
import brickingbad.domain.game.Level;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.gameobjects.alien.Alien;
import brickingbad.domain.game.gameobjects.alien.DrunkAlien;
import brickingbad.domain.game.gameobjects.brick.Brick;
import brickingbad.domain.game.gameobjects.brick.BrickFactory;

import java.util.ArrayList;

public class RepairingAlienState extends AlienState {

    public boolean drunk;
    private double cooldown = GameConstants.repairingAlienCooldown;
    private long startTime;

    public RepairingAlienState(Alien alien) {
        this.alien = alien;
        this.drunk = alien instanceof DrunkAlien;
        this.startTime = Level.getInstance().getTime();
    }

    @Override
    public void performAction() {
        if(drunk) {
            Level.getInstance().addBrickHorizontal();
        }else {
            long currentTime = Level.getInstance().getTime();
            if(currentTime - startTime > 1000 * cooldown) {
                ArrayList<Brick> simpleBricks = BrickFactory.getInstance().createSimpleBricks(1);
                Brick newBrick = simpleBricks.get(0);
                GameObjectFactory.getInstance().addBrick(newBrick);
                startTime = currentTime;
            }
        }
    }

}
