package brickingbad.domain.physics.paddle;

import brickingbad.domain.game.Paddle;

import javax.swing.text.Position;

public class EndPaddleMoveState extends PaddleMoveState{

  private final Position startPosition;

  public EndPaddleMoveState(Paddle paddle, Direction direction, Position startPosition) {
    super(paddle, direction);
    this.startPosition = startPosition;
  }

  @Override
  void updatePosition() {

  }

}
