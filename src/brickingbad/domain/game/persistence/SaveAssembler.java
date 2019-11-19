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
    double paddleX = game.getPaddle().getPosition().getX();
    double paddleY = game.getPaddle().getPosition().getY();

    ArrayList<Ball> balls = game.getBalls();
    int ballCount = balls.size();
    double[] ballX = new double[ballCount];
    double[] ballY = new double[ballCount];
    double[] ballVelX = new double[ballCount];
    double[] ballVelY = new double[ballCount];
    for (int i = 0; i < ballCount; i++) {
      ballX[i] = balls.get(i).getPosition().getX();
      ballY[i] = balls.get(i).getPosition().getY();
      ballVelX[i] = balls.get(i).getVelocity().getX();
      ballVelY[i] = balls.get(i).getVelocity().getY();
    }

    ArrayList<Brick> bricks = game.getBricks();
    int brickCount = bricks.size();
    double[] brickX = new double[brickCount];
    double[] brickY = new double[brickCount];
    for (int i = 0; i < brickCount; i++) {
      Brick brick = bricks.get(i);
      brickX[i] = brick.getPosition().getX();
      brickY[i] = brick.getPosition().getY();
    }

    save.setName(name);
    save.setScore(score);
    save.setLives(lives);
//    dto.setBallVelX(ballVelX);
//    dto.setBallVelY(ballVelY);
//    dto.setBallX(ballX);
//    dto.setBallY(ballY);
//    dto.setBrickX(brickX);
//    dto.setBrickY(brickY);
    save.setPaddleX(paddleX);
    save.setPaddleY(paddleY);

    return save;
  }

}
