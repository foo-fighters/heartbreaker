package brickingbad.domain.game.persistence;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.Game;
import brickingbad.domain.game.brick.Brick;

import java.util.ArrayList;

/**
 * SaveAssembler implements the 'assemble' method that takes
 * in a Game object and converts it into the Save POJO
 * so that it can be serialized easily.
 */
public class SaveAssembler {

  public static Save assemble(Game game, String name) {
    Save save = new Save();

    int score = game.getScore();
    int lives = game.getLives();

    int paddleX = game.getPaddle().getPosition().getX();
    int paddleY = game.getPaddle().getPosition().getY();
    save.paddleCoordinates.add(paddleX);
    save.paddleCoordinates.add(paddleY);

    ArrayList<Ball> balls = game.getBalls();
    balls.forEach((ball) -> {
      ArrayList<Integer> tempCoordinates  = new ArrayList<>();
      tempCoordinates.add(ball.getPosition().getX());
      tempCoordinates.add(ball.getPosition().getY());
      save.ballCoordinates.add(tempCoordinates);

      ArrayList<Integer> tempVelocities = new ArrayList<>();
      tempVelocities.add(ball.getVelocity().getX());
      tempVelocities.add(ball.getVelocity().getY());
      save.ballVelocities.add(tempVelocities);
    });

    ArrayList<Brick> bricks = game.getBricks();
    bricks.forEach((brick) -> {
      ArrayList<Integer> tempCoordinates = new ArrayList<>();
      tempCoordinates.add(brick.getPosition().getX());
      tempCoordinates.add(brick.getPosition().getY());
      save.brickCoordinates.add(tempCoordinates);
    });

    save.name = name;
    save.score = score;
    save.lives = lives;

    return save;
  }

}
