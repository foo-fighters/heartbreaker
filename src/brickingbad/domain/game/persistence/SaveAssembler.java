package brickingbad.domain.game.persistence;

import brickingbad.domain.game.Ball;
import brickingbad.domain.game.Game;
import brickingbad.domain.game.Paddle;
import brickingbad.domain.game.WrapperContent;
import brickingbad.domain.game.alien.Alien;
import brickingbad.domain.game.brick.Brick;
import brickingbad.domain.game.brick.BrickFactory;
import brickingbad.domain.game.brick.SimpleBrick;
import brickingbad.domain.game.powerup.*;
import brickingbad.domain.physics.Vector;

import java.sql.Array;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;


public class SaveAssembler {

  public static Save assemble(Game game, String name) {
    Save save = new Save();

    int score = game.getScore();
    int lives = game.getLives();
    
    double paddleX = game.getPaddle().getPosition().getX();
    double paddleY = game.getPaddle().getPosition().getY();
    save.paddleCoordinates.add(paddleX);
    save.paddleCoordinates.add(paddleY);

    ArrayList<Ball> balls = game.getBalls();
    List<Ball> paddleBalls = game.getPaddle().getCurrentBalls();
    balls.forEach((ball) -> {
      ArrayList<Double> tempCoordinates  = new ArrayList<>();
      tempCoordinates.add(ball.getPosition().getX());
      tempCoordinates.add(ball.getPosition().getY());
      save.ballCoordinates.add(tempCoordinates);

      ArrayList<Double> tempVelocities = new ArrayList<>();
      tempVelocities.add(ball.getVelocity().getX());
      tempVelocities.add(ball.getVelocity().getY());
      save.ballVelocities.add(tempVelocities);

      save.ballOnPaddle.add(paddleBalls.contains(ball));
    });

    ArrayList<Brick> bricks = game.getBricks();
    bricks.forEach((brick) -> {
      ArrayList<Double> tempCoordinates = new ArrayList<>();
      tempCoordinates.add(brick.getPosition().getX());
      tempCoordinates.add(brick.getPosition().getY());
      save.brickCoordinates.add(tempCoordinates);
      save.brickTypes.add(brick.getTypeName());
    });

    ArrayList<Alien> aliens = game.getAliens();
    aliens.forEach((alien) -> {
      ArrayList<Double> tempCoordinates = new ArrayList<>();
      tempCoordinates.add(alien.getPosition().getX());
      tempCoordinates.add(alien.getPosition().getY());
      save.alienCoordinates.add(tempCoordinates);
      save.alienTypes.add(alien.getTypeName());
    });

    ArrayList<PowerUp> storedPowerUps = game.getStoredPowerUps();
    ArrayList<PowerUp> activePowerUps = game.getActivePowerUps();
    storedPowerUps.forEach((powerUp) -> {
      save.storedPowerUps.add(powerUp.getName().name());
    });
    activePowerUps.forEach((powerUp) -> {
      save.activePowerUps.add(powerUp.getName().name());
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
    int brickIndex = 0;
    for (List<Double> coordinates : save.brickCoordinates) {
      String className = save.brickTypes.get(brickIndex);
      double x = coordinates.get(0);
      double y = coordinates.get(1);
      Brick brick = BrickFactory.getInstance().createBrick(className);
      brick.setPosition(new Vector(x, y));
      bricks.add(brick);
      brickIndex++;
    }

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

    List<String> activePowerUps = save.activePowerUps;
    List<String> storedPowerUps = save.storedPowerUps;
    for (String name : storedPowerUps) {
      PowerUp powerUp = PowerUp.getByName(name);
      Game.getInstance().storePowerUp(powerUp);
    }
    for (String name : activePowerUps) {
      PowerUp powerUp = PowerUp.getByName(name);
      Game.getInstance().storePowerUp(powerUp);
      Game.getInstance().usePowerUp(WrapperContent.valueOf(name));
    }

//    int alienIndex = 0;
//    for (List<Double> coordinates : save.alienCoordinates) {
//      double x = coordinates.get(0);
//      double y = coordinates.get(1);
//      String typeName = save.alienTypes.get(alienIndex);
//      Alien alien = Alien.getByType(typeName);
//      Game.getInstance().addAlien(alien);
//      alienIndex++;
//    }

    Game.getInstance().setScore(score);
    Game.getInstance().setLives(lives);
    bricks.forEach(brick -> Game.getInstance().addBrick(brick));
    Game.getInstance().setPaddle(paddle);
    for (int i = 0; i < balls.size(); i++) {
      Ball ball = balls.get(i);
      Game.getInstance().addBall(ball);
      if (save.ballOnPaddle.get(i)) {
        Game.getInstance().getPaddle().catchBall(ball);
      }
    }
  }

}
