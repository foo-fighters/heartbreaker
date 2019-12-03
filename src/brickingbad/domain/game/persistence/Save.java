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

  List<Boolean> ballOnPaddle;
  List<List<Double>> ballCoordinates;
  List<List<Double>> ballVelocities;

  List<String> activePowerUps;
  List<String> storedPowerUps;

  List<List<Double>> alienCoordinates;
  List<String> alienTypes;

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
  }

}
