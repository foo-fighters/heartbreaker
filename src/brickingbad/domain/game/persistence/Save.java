package brickingbad.domain.game.persistence;

import java.util.ArrayList;
import java.util.List;

// TODO: Convert arrays to lists
public class Save {

  public String name;
  public int score;
  public int lives;
  public int paddleX;
  public int paddleY;
  public List<List<Integer>> brickCoordinates;
  public List<List<Integer>> ballCoordinates;
  public List<List<Integer>> ballVelocities;

}
