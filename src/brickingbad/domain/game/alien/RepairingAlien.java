package brickingbad.domain.game.alien;

import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.Shape;
import brickingbad.domain.physics.Vector;
import brickingbad.domain.game.brick.BrickFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import brickingbad.domain.game.brick.BrickFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RepairingAlien extends Alien {

    public RepairingAlien() {
        this.position = new Vector();
        this.shape = Shape.CIRCLE;
        this.velocity = new Vector();
        this.angle = 0.0;
        this.size = new Vector(GameConstants.alienSize, GameConstants.alienSize);

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

        Date currentTime = new Date();
        Timer myTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                BrickFactory.getInstance().createSimpleBricks(1);
            }
        };
        myTimer.schedule(task, currentTime, 5);
        ArrayList<GameObject> objects = new ArrayList<>(Game.getInstance().getObjects());
        for (GameObject object : objects) {
            if (object instanceof RepairingAlien) {

                if (!(object instanceof RepairingAlien)) {

                    myTimer.cancel();
                }
            }
        }
    }
}




