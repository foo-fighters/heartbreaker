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

    ArrayList<Ball> balls = game.getBalls();
    int ballCount = balls.size();
    int[] ballX = new int[ballCount];
    int[] ballY = new int[ballCount];
    int[] ballVelX = new int[ballCount];
    int[] ballVelY = new int[ballCount];
    for (int i = 0; i < ballCount; i++) {
      ballX[i] = balls.get(i).getPosition().getX();
      ballY[i] = balls.get(i).getPosition().getY();
      ballVelX[i] = balls.get(i).getVelocity().getX();
      ballVelY[i] = balls.get(i).getVelocity().getY();
    }

    ArrayList<Brick> bricks = game.getBricks();
    int brickCount = bricks.size();
    int[] brickX = new int[brickCount];
    int[] brickY = new int[brickCount];
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
