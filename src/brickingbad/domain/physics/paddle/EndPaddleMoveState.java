package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Paddle;

public class EndPaddleMoveState extends PaddleMoveState{

  private final int startX;
  private final int startY;
  private final int moveSpeed = GameConstants.slowPaddleMovementSpeed;

  public EndPaddleMoveState(Paddle paddle, Direction direction, int startX, int startY) {
    super(paddle, direction);
    this.startX = startX;
    this.startY = startY;
  }

  @Override
  public void updatePosition() {
  }

}
