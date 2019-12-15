import brickingbad.domain.game.Ball;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Paddle;
import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.Vector;
import brickingbad.domain.physics.paddle.ActivePaddleMoveState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActivePaddleMoveStateTest {

    private Paddle paddle;
    private Ball ball;
    private Vector paddleStartPosition;
    private Vector ballStartPosition;
    private double deltaPos;

    @Before
    public void setUp() {
        paddle = new Paddle();
        ball = new Ball(paddle.getBallStartPosition());
        paddle.getCurrentBalls().add(ball);
        paddleStartPosition = new Vector(paddle.getPosition().getX(), paddle.getPosition().getY());
        ballStartPosition = new Vector(ball.getPosition().getX(), ball.getPosition().getY());
        deltaPos = GameConstants.regularPaddleMovementSpeed / GameConstants.calculationsPerSecond;
    }

    @Test
    public void testActiveMoveState() {
        paddle.startMove(Direction.LEFT);
        assertEquals(ActivePaddleMoveState.class, paddle.getMoveState().getClass());
    }

    @Test
    public void testMoveLeft() {
        paddle.startMove(Direction.LEFT);
        paddle.getMoveState().updatePosition();
        assertEquals(paddleStartPosition.sum(new Vector(-deltaPos, 0)), paddle.getPosition());
        assertEquals(ballStartPosition.sum(new Vector(-deltaPos, 0)), ball.getPosition());
    }

    @Test
    public void testMoveLeftAtBorder() {
        Vector paddleTestPosition = new Vector(paddle.getSize().getX() / 2.0, paddle.getPosition().getY());
        paddle.setPosition(paddleTestPosition);
        paddle.startMove(Direction.LEFT);
        paddle.getMoveState().updatePosition();
        assertEquals(paddleTestPosition, paddle.getPosition());
    }

    @Test
    public void testMoveRight() {
        paddle.startMove(Direction.RIGHT);
        paddle.getMoveState().updatePosition();
        assertEquals(paddleStartPosition.sum(new Vector(deltaPos, 0)), paddle.getPosition());
        assertEquals(ballStartPosition.sum(new Vector(deltaPos, 0)), ball.getPosition());
    }

    @Test
    public void testMoveRightAtBorder() {
        Vector paddleTestPosition = new Vector(GameConstants.screenWidth - paddle.getSize().getX() / 2.0, paddle.getPosition().getY());
        paddle.setPosition(paddleTestPosition);
        paddle.startMove(Direction.RIGHT);
        paddle.getMoveState().updatePosition();
        assertEquals(paddleTestPosition, paddle.getPosition());
    }

}
