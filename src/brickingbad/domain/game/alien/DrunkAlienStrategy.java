package brickingbad.domain.game.alien;


import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.Shape;
import brickingbad.domain.game.brick.BrickFactory;
import brickingbad.domain.physics.Vector;
import java.util.*;

public class DrunkAlienStrategy extends AlienStrategy {

   private int startBrickCount;
   private int currentBrick;
   private AlienStrategy cooperativeAlienStrategy;
   private AlienStrategy repairingAlienStrategy;
   private AlienStrategy protectingAlienStrategy;
   private ArrayList<GameObject> GameObjects;
   private double y;
   private double x;
   private Random rand;
   public boolean horizontalStrategy;

   public DrunkAlienStrategy() {
      this.position = new Vector();
      this.shape = Shape.RECTANGLE;
      this.velocity = new Vector();
      this.angle = 0.0;
      this.size = new Vector(GameConstants.alienSize, GameConstants.alienSize);
      startBrickCount = Game.getInstance().startBrickCount;
      currentBrick = Game.getInstance().getBrickCount();
      horizontalStrategy = false;
      cooperativeAlienStrategy = new CooperativeAlienStrategy();
      protectingAlienStrategy = new ProtectingAlienStrategy();
      repairingAlienStrategy = new RepairingAlienStrategy(horizontalStrategy );
      rand = new Random();
      x =  ((rand.nextInt(GameConstants.screenWidth - GameConstants.alienSize) + GameConstants.alienSize/2)/10)*10;
      y = ((rand.nextInt((int) GameConstants.alienAreaHeight - GameConstants.alienSize) + GameConstants.brickAreaHeight + GameConstants.menuAreaHeight
              + GameConstants.alienSize/2)/10)*10;//should be in the brick area
      position.setVector(x,y);
   }

   @Override
   void performStrategy() {
      GameObjects = new ArrayList<>(Game.getInstance().getObjects());
      for (GameObject object : GameObjects) {
         while (object instanceof DrunkAlienStrategy) {
            if (startBrickCount * 0.7 < currentBrick) {
               cooperativeAlienStrategy.performStrategy();
            } else if (startBrickCount * 0.3 > currentBrick) {
               horizontalStrategy= true;
               repairingAlienStrategy.performStrategy();
               protectingAlienStrategy.performStrategy();
               horizontalStrategy = false;
            } else if (currentBrick > startBrickCount * 0.4 && currentBrick < startBrickCount * 0.5) {
               protectingAlienStrategy.performStrategy();
            } else if (currentBrick > startBrickCount * 0.5 && currentBrick < startBrickCount * 0.6) {
               repairingAlienStrategy.performStrategy();
            } else if ((currentBrick > startBrickCount * 0.3 && currentBrick < startBrickCount * 0.4)
                    || (currentBrick > startBrickCount * 0.6 && currentBrick < startBrickCount * 0.7)) {
               Date currentTime = new Date();//gettime ilke degisecek
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

