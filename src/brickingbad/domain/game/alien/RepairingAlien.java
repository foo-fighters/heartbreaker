package brickingbad.domain.game.alien;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.Shape;
import brickingbad.domain.physics.Vector;
import brickingbad.domain.game.brick.BrickFactory;

import java.util.*;

public class RepairingAlien extends Alien {
    private Random rand;
    private double y;
    private double x;

    public RepairingAlien() {
        this.position = new Vector();
        this.shape = Shape.RECTANGLE;
        this.velocity = new Vector();
        this.angle = 0.0;
        this.size = new Vector(GameConstants.alienSize, GameConstants.alienSize);
        rand = new Random();
        x =  (rand.nextInt(GameConstants.screenWidth - GameConstants.alienSize) + GameConstants.alienSize/2)/10;
        y = (rand.nextInt((int) GameConstants.alienAreaHeight) + GameConstants.brickAreaHeight + GameConstants.menuAreaHeight )/10;//should be in the brick area
        position.setVector(x,y);
        repairCooldown = 5;
    }

    public RepairingAlien(int repairCooldown) {
        this.repairCooldown = repairCooldown;
    }

    private int repairCooldown;

    public int getRepairCooldown() {
        return repairCooldown;
    }

    public void setRepairCooldown(int repairCooldown) {
        this.repairCooldown = repairCooldown;
    }

    @Override
    void performAction() {
        ArrayList<GameObject> objects = new ArrayList<>(Game.getInstance().getObjects());
        Date currentTime = new Date();
        Timer myTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                for (GameObject object : objects) {
                    if (!(object instanceof RepairingAlien)) {
                        myTimer.cancel();
                    }
                    BrickFactory.getInstance().createSimpleBricks(1);
                }
            }
        };
        myTimer.schedule(task, currentTime, repairCooldown);
        }
    }







