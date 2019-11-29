package brickingbad.domain.game.powerup;

import brickingbad.domain.game.*;
import brickingbad.domain.physics.Vector;

public class TallerPaddle extends PowerUp {

    private int multiplier = GameConstants.tallerPaddleMultiplier;

    public TallerPaddle(Vector revealPosition) {
        this.position = new Vector(revealPosition.getX(), revealPosition.getY());
        this.shape = Shape.RECTANGLE;
        this.size = new Vector(GameConstants.powerupSize, GameConstants.powerupSize);
        this.velocity = new Vector(0, GameConstants.powerupFallSpeed);
        this.angle = 0.0;
        this.name = WrapperContent.TALLER_PADDLE;
        this.duration = GameConstants.tallerPaddleDuration;
    }

    @Override
    public void activate() {
        super.activate();
        Paddle paddle = Game.getInstance().getPaddle();
        paddle.getSize().setVector(GameConstants.paddleLength * multiplier, GameConstants.paddleThickness);
        for(Ball ball: paddle.getCurrentBalls()) {
            ball.setPaddleOffset(ball.getPaddleOffset() * multiplier);
        }
    }

    @Override
    public void deactivate() {
        Paddle paddle = Game.getInstance().getPaddle();
        paddle.getSize().setVector(GameConstants.paddleLength, GameConstants.paddleThickness);
        for(Ball ball: paddle.getCurrentBalls()) {
            ball.setPaddleOffset(ball.getPaddleOffset() / multiplier);
        }
        destroy();
    }
}
