package brickingbad.domain.game.persistence;

public class Save {

  private String name;
  private int score;
  private int lives;
  private double paddleX;
  private double paddleY;
//  private double[] brickX;
//  private double[] brickY;
//  private double[] ballX;
//  private double[] ballY;
//  private double[] ballVelX;
//  private double[] ballVelY;

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public int getLives() {
    return lives;
  }

  public void setLives(int lives) {
    this.lives = lives;
  }

//  public double[] getBallX() {
//    return ballX;
//  }
//
//  public void setBallX(double[] ballX) {
//    this.ballX = ballX;
//  }
//
//  public double[] getBallY() {
//    return ballY;
//  }
//
//  public void setBallY(double[] ballY) {
//    this.ballY = ballY;
//  }
//
//  public double[] getBallVelX() {
//    return ballVelX;
//  }
//
//  public void setBallVelX(double[] ballVelX) {
//    this.ballVelX = ballVelX;
//  }
//
//  public double[] getBallVelY() {
//    return ballVelY;
//  }
//
//  public void setBallVelY(v[] ballVelY) {
//    this.ballVelY = ballVelY;
//  }

  public double getPaddleX() {
    return paddleX;
  }

  public void setPaddleX(double paddleX) {
    this.paddleX = paddleX;
  }

  public double getPaddleY() {
    return paddleY;
  }

  public void setPaddleY(double paddleY) {
    this.paddleY = paddleY;
  }

//  public double[] getBrickX() {
//    return brickX;
//  }
//
//  public void setBrickX(double[] brickX) {
//    this.brickX = brickX;
//  }
//
//  public double[] getBrickY() {
//    return brickY;
//  }
//
//  public void setBrickY(double[] brickY) {
//    this.brickY = brickY;
//  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
