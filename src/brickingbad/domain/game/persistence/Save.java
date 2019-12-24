package brickingbad.domain.game.persistence;

import java.util.ArrayList;
import java.util.List;

public class Save {
  // TODO: keep game time

  public String name;

  List<List<Boolean>> grid;

  public boolean inRunningMode;

  public String username;

  public int score;
  public int lives;

  public List<Double> paddleCoordinates;

  public List<List<Double>> brickCoordinates;
  public List<String> brickTypes;

  public List<Boolean> ballOnPaddle;
  public List<List<Double>> ballCoordinates;
  public List<List<Double>> ballVelocities;

  public List<String> activePowerUps;
  public List<String> storedPowerUps;

  public List<List<Double>> alienCoordinates;
  public List<String> alienTypes;

  public boolean cooperativeAlienIsKilled;

  public long offset;

  public Save() {
    paddleCoordinates = new ArrayList<>();
    brickCoordinates = new ArrayList<>();
    ballCoordinates = new ArrayList<>();
    ballVelocities = new ArrayList<>();
    storedPowerUps = new ArrayList<>();
    activePowerUps = new ArrayList<>();
    brickTypes = new ArrayList<>();
    alienCoordinates = new ArrayList<>();
    alienTypes = new ArrayList<>();
    ballOnPaddle = new ArrayList<>();
    grid = new ArrayList<>();
  }

}
