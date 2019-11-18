package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Paddle;

public class ActivePaddleMoveState extends PaddleMoveState {

  private final int moveSpeed = GameConstants.regularPaddleMovementSpeed;

  public ActivePaddleMoveState(Paddle paddle, Direction direction) {
    super(paddle, direction);
  }

  @Override
  public void updatePosition() {
  }

}
