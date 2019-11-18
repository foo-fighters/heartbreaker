package brickingbad.domain.game.persistence;

public class Save {

  private String name;
  private int score;
  private int lives;
  private int paddleX;
  private int paddleY;
//  private int[] brickX;
//  private int[] brickY;
//  private int[] ballX;
//  private int[] ballY;
//  private int[] ballVelX;
//  private int[] ballVelY;

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

//  public int[] getBallX() {
//    return ballX;
//  }
//
//  public void setBallX(int[] ballX) {
//    this.ballX = ballX;
//  }
//
//  public int[] getBallY() {
//    return ballY;
//  }
//
//  public void setBallY(int[] ballY) {
//    this.ballY = ballY;
//  }
//
//  public int[] getBallVelX() {
//    return ballVelX;
//  }
//
//  public void setBallVelX(int[] ballVelX) {
//    this.ballVelX = ballVelX;
//  }
//
//  public int[] getBallVelY() {
//    return ballVelY;
//  }
//
//  public void setBallVelY(int[] ballVelY) {
//    this.ballVelY = ballVelY;
//  }

  public int getPaddleX() {
    return paddleX;
  }

  public void setPaddleX(int paddleX) {
    this.paddleX = paddleX;
  }

  public int getPaddleY() {
    return paddleY;
  }

  public void setPaddleY(int paddleY) {
    this.paddleY = paddleY;
  }

//  public int[] getBrickX() {
//    return brickX;
//  }
//
//  public void setBrickX(int[] brickX) {
//    this.brickX = brickX;
//  }
//
//  public int[] getBrickY() {
//    return brickY;
//  }
//
//  public void setBrickY(int[] brickY) {
//    this.brickY = brickY;
//  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
