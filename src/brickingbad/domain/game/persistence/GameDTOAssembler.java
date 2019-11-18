package brickingbad.domain.game.persistence;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.Game;
import brickingbad.domain.game.brick.Brick;

import java.util.ArrayList;


public class GameDTOAssembler {

  public static GameDTO assemble(Game game) {
    int score = game.getScore();
    int lives = game.getLives();
    int paddleX = game.getPaddle().getPosition().getX();
    int paddleY = game.getPaddle().getPosition().getY();

    ArrayList<Ball> balls = game.getBalls();
    int ballCount = balls.size();
    int[] ballX = new int[ballCount];
    int[] ballY = new int[ballCount];
    for (int i = 0; i < ballCount; i++) {
      ballX[i] = balls.get(i)
    }


    ArrayList<Brick> bricks = game.getBricks();
    int brickCount = bricks.size();
    int[] brickX = new int[brickCount];
    int[] brickY = new int[brickCount];

  }

}
