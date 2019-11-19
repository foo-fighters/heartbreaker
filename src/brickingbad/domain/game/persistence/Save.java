package brickingbad.domain.game.persistence;

import java.util.ArrayList;
import java.util.List;

public class Save {

  String name;
  int score;
  int lives;
  List<Integer> paddleCoordinates;
  List<List<Integer>> brickCoordinates;
  List<List<Integer>> ballCoordinates;
  List<List<Integer>> ballVelocities;

  Save() {
    paddleCoordinates = new ArrayList<>();
    brickCoordinates = new ArrayList<>();
    ballCoordinates = new ArrayList<>();
    ballVelocities = new ArrayList<>();
  }

}
