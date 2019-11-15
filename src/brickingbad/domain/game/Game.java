package brickingbad.domain.game;

import brickingbad.domain.game.powerup.*;
import brickingbad.domain.game.border.*;
import brickingbad.domain.game.alien.*;
import brickingbad.domain.game.brick.*;
import brickingbad.domain.game.*;

import java.util.ArrayList;
import java.util.Date;

public class Game {

  private static Game instance;

  private Paddle paddle;
  private ArrayList<Ball> balls;
  private Ground ground;
  private Wall wall;
  private ArrayList<Brick> bricks;

  private int score;
  private int lives;
  private Date time;

  private ArrayList<PowerUp> activePowerUps;
  private ArrayList<PowerUp> storedPowerUps;

  private ArrayList<GameObjectListener> objectListeners;

  private Game() {
    objectListeners = new ArrayList<>();
  }

  public static Game getInstance() {
    if (instance == null) {
      instance = new Game();
    }
    return instance;
  }

  public void addObjectListener(GameObjectListener listener) {
    objectListeners.add(listener);
  }

}
