package brickingbad.domain.game.persistence;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.Game;
import brickingbad.domain.game.Paddle;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.game.brick.SimpleBrick;
import brickingbad.domain.physics.Vector;

import java.util.ArrayList;


public class SaveAssembler {

  /**
   * Takes a Game object and a name string.
   * Extracts the necessary information from the game object into the save object.
   */
  public static Save assemble(Game game, String name) {
    Save save = new Save();

    int score = game.getScore();
    int lives = game.getLives();
    
    double paddleX = game.getPaddle().getPosition().getX();
    double paddleY = game.getPaddle().getPosition().getY();
    save.paddleCoordinates.add(paddleX);
    save.paddleCoordinates.add(paddleY);

    ArrayList<Ball> balls = game.getBalls();
    balls.forEach((ball) -> {
      ArrayList<Double> tempCoordinates  = new ArrayList<>();
      tempCoordinates.add(ball.getPosition().getX());
      tempCoordinates.add(ball.getPosition().getY());
      save.ballCoordinates.add(tempCoordinates);

      ArrayList<Double> tempVelocities = new ArrayList<>();
      tempVelocities.add(ball.getVelocity().getX());
      tempVelocities.add(ball.getVelocity().getY());
      save.ballVelocities.add(tempVelocities);
    });

    ArrayList<Brick> bricks = game.getBricks();
    bricks.forEach((brick) -> {
      ArrayList<Double> tempCoordinates = new ArrayList<>();
      tempCoordinates.add(brick.getPosition().getX());
      tempCoordinates.add(brick.getPosition().getY());
      save.brickCoordinates.add(tempCoordinates);
    });

    save.name = name;
    save.score = score;
    save.lives = lives;

    return save;
  }

  public static void disassemble(Save save) {
    int score = save.score;
    int lives = save.lives;

    ArrayList<Brick> bricks = new ArrayList<>();
    save.brickCoordinates.forEach((coordinates) -> {
      double x = coordinates.get(0);
      double y = coordinates.get(1);
      Vector brickPosition = new Vector(x, y);
      bricks.add(new SimpleBrick(brickPosition));
    });

    ArrayList<Ball> balls = new ArrayList<>();
    save.ballCoordinates.forEach((coordinate) -> {
      double x = coordinate.get(0);
      double y = coordinate.get(1);
      Vector ballPosition = new Vector(x, y);
      balls.add(new Ball(ballPosition));
    });
    for (int i = 0; i < balls.size(); i++) {
      double velX = save.ballVelocities.get(i).get(0);
      double velY = save.ballVelocities.get(i).get(1);
      balls.get(i).setVelocity(new Vector(velX, velY));
    }

    Paddle paddle = new Paddle();
    if (save.paddleCoordinates != null) {
      double paddleX = save.paddleCoordinates.get(0);
      double paddleY = save.paddleCoordinates.get(1);
      paddle.setPosition(paddleX, paddleY);
    }

    Game.getInstance().setScore(score);
    Game.getInstance().setLives(lives);
    bricks.forEach(brick -> Game.getInstance().addBrick(brick));
//    Game.getInstance().setPaddle(paddle);

//    balls.forEach(ball ->  {
//      Game.getInstance().addBall(ball);
//    });
  }

}
