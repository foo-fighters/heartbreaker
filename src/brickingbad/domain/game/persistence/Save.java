package brickingbad.domain.game.persistence;

import java.util.ArrayList;
import java.util.List;

public class Save {

  public String name;
  public int score;
  public int lives;
  public List<Double> paddleCoordinates;
  public List<List<Double>> brickCoordinates;
  public List<List<Double>> ballCoordinates;
  public List<List<Double>> ballVelocities;

  public Save() {
    paddleCoordinates = new ArrayList<>();
    brickCoordinates = new ArrayList<>();
    ballCoordinates = new ArrayList<>();
    ballVelocities = new ArrayList<>();
  }

}
