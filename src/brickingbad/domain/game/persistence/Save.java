package brickingbad.domain.game.persistence;

import java.util.ArrayList;
import java.util.List;

public class Save {

  public String name;
  public int score;
  public int lives;
  public List<Integer> paddleCoordinates;
  public List<List<Integer>> brickCoordinates;
  public List<List<Integer>> ballCoordinates;
  public List<List<Integer>> ballVelocities;

  public Save() {
    paddleCoordinates = new ArrayList<>();
    brickCoordinates = new ArrayList<>();
    ballCoordinates = new ArrayList<>();
    ballVelocities = new ArrayList<>();
  }

}
