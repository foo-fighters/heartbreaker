package brickingbad.domain.game.powerup;

import brickingbad.domain.game.*;
import brickingbad.domain.game.gameobjects.Ball;
import brickingbad.domain.game.gameobjects.Paddle;
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
        this.dynamic = true;
    }

    @Override
    public void activate() {
        super.activate();
        Paddle paddle = Level.getInstance().getPaddle();
        paddle.getSize().setVector(GameConstants.paddleLength * multiplier, GameConstants.paddleThickness);
        if(paddle.getPosition().getX() <= paddle.getSize().getX() / 2.0) {
            paddle.setPosition(paddle.getSize().getX() / 2.0, paddle.getPosition().getY());
        }
        if(paddle.getPosition().getX() >= GameConstants. screenWidth - paddle.getSize().getX() / 2.0) {
            paddle.setPosition(GameConstants. screenWidth - paddle.getSize().getX() / 2.0, paddle.getPosition().getY());
        }
        Level.getInstance().getPaddle().paddleMountDistance *= multiplier;
        for(Ball ball: paddle.getCurrentBalls()) {
            ball.setPaddleOffset(ball.getPaddleOffset() * multiplier);
        }
    }

    @Override
    public void deactivate() {
        Paddle paddle = Level.getInstance().getPaddle();
        paddle.getSize().setVector(GameConstants.paddleLength, GameConstants.paddleThickness);
        Level.getInstance().getPaddle().paddleMountDistance /= multiplier;
        for(Ball ball: paddle.getCurrentBalls()) {
            ball.setPaddleOffset(ball.getPaddleOffset() / multiplier);
        }
        super.deactivate();
    }
}
