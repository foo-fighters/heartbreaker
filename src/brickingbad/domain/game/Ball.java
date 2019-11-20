package brickingbad.domain.game;

import brickingbad.domain.physics.Vector;
import brickingbad.domain.physics.ball.BallState;
import brickingbad.domain.physics.ball.ChemicalBallState;
import brickingbad.domain.physics.ball.FireBallState;
import brickingbad.domain.physics.ball.SimpleBallState;

public class Ball extends GameObject {

    private BallState ballState;
    private double launchSpeed = GameConstants.ballLaunchSpeed;

    public Ball(Vector position){
        this.shape = Shape.CIRCLE;
        this.size = new Vector(GameConstants.ballSize, GameConstants.ballSize);
        this.position = position;
        this.velocity = new Vector();
        setSimple();
    }

    public void startMovement(double angle){
        this.velocity.setVector(launchSpeed * Math.sin(angle), -launchSpeed * Math.cos(angle));
    }

    public void stopMovement(){
        this.velocity.setVector(0.0, 0.0);
    }

    public void setSimple(){
        ballState = new SimpleBallState(this);
    }

    public void setFireball(){
        ballState = new FireBallState(this);
    }

    public void setChemical(){
        ballState = new ChemicalBallState(this);
    }

    @Override
    public void reflect(GameObject object){
        ballState.reflect(object);
    }
}
