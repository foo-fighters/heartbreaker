package brickingbad.domain.game.persistence;

import java.util.ArrayList;
import java.util.List;

public class Save {

  public String name;

  int score;
  int lives;

  List<Double> paddleCoordinates;

  List<List<Double>> brickCoordinates;
  List<String> brickTypes;

  List<List<Double>> ballCoordinates;
  List<List<Double>> ballVelocities;

  List<Boolean> activePowerUps;
  List<Boolean> storedPowerUps;

  List<List<Double>> alienCoordinates;
  List<String> alienTypes;

  Save() {
    paddleCoordinates = new ArrayList<>();
    brickCoordinates = new ArrayList<>();
    ballCoordinates = new ArrayList<>();
    ballVelocities = new ArrayList<>();
    storedPowerUps = new ArrayList<>();
    activePowerUps = new ArrayList<>();
    brickTypes = new ArrayList<>();
    alienCoordinates = new ArrayList<>();
    alienTypes = new ArrayList<>();
  }

}
