package brickingbad.domain.game.alien;


import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.Shape;
import brickingbad.domain.game.brick.BrickFactory;
import brickingbad.domain.physics.Vector;

import java.util.*;

public class DrunkAlien extends Alien {

   private int startBrickCount;
   private int currentBrick;
   private Alien cooperativeAlien;
   private Alien repairingAlien;
   private Alien protectingAlien;
   private ArrayList<GameObject> GameObjects;
   private BrickFactory brickFactory;
   private double y;
   private double x;
   private Random rand;


   public DrunkAlien() {
      this.position = new Vector();
      this.shape = Shape.RECTANGLE;
      this.velocity = new Vector();
      this.angle = 0.0;
      this.size = new Vector(GameConstants.alienSize, GameConstants.alienSize);
      startBrickCount = Game.getInstance().startBrickCount;
      currentBrick = Game.getInstance().getBrickCount();
      cooperativeAlien = new CooperativeAlien();
      protectingAlien = new ProtectingAlien();
      repairingAlien = new RepairingAlien();
      rand = new Random();
      x =  (rand.nextInt(GameConstants.screenWidth - GameConstants.alienSize) + GameConstants.alienSize/2)/10;
      y = (rand.nextInt((int) GameConstants.alienAreaHeight) + GameConstants.brickAreaHeight + GameConstants.menuAreaHeight )/10;//should be in the brick area
      position.setVector(x,y);
   }


   @Override
   void performAction() {
      GameObjects = new ArrayList<>(Game.getInstance().getObjects());
      for (GameObject object : GameObjects) {
         while (object instanceof DrunkAlien) {
            if (startBrickCount * 0.7 < currentBrick) {
               cooperativeAlien.performAction();
            } else if (startBrickCount * 0.3 > currentBrick) {
               Game.getInstance().addBrickHorizontal();
               protectingAlien.performAction();
            } else if (currentBrick > startBrickCount * 0.4 && currentBrick < startBrickCount * 0.5) {
               protectingAlien.performAction();
            } else if (currentBrick > startBrickCount * 0.5 && currentBrick < startBrickCount * 0.6) {
               repairingAlien.performAction();
            } else if ((currentBrick > startBrickCount * 0.3 && currentBrick < startBrickCount * 0.4)
                    || (currentBrick > startBrickCount * 0.6 && currentBrick < startBrickCount * 0.7)) {
               Date currentTime = new Date();
               Timer myTimer = new Timer();
               TimerTask task = new TimerTask() {
                  @Override
                  public void run() {
                     GameObjects.remove(object);
                     myTimer.cancel();
                  }
               };
               myTimer.schedule(task, 5);
            }
         }
      }
   }
}

