package brickingbad.domain.game;

import brickingbad.domain.game.brick.HalfMetalBrick;
import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.Vector;
import brickingbad.domain.physics.ball.BallState;
import brickingbad.domain.physics.ball.ChemicalBallState;
import brickingbad.domain.physics.ball.FireBallState;
import brickingbad.domain.physics.ball.SimpleBallState;

public class Ball extends GameObject {

    private BallState ballState;
    private double paddleOffset;

    public Ball(Vector position){
        this.shape = Shape.CIRCLE;
        this.size = new Vector(GameConstants.ballSize, GameConstants.ballSize);
        this.position = position;
        this.velocity = new Vector();
        this.paddleOffset = 0.0;
        this.angle = 0.0;
        setSimple();
    }

    public double getPaddleOffset() {
        return paddleOffset;
    }

    public void startMovement(double angle, double speed){
        velocity.setVector(-speed * Math.sin(Math.toRadians(angle)), -speed * Math.cos(Math.toRadians(angle)));
    }

    public double getSpeed() {
        return Math.hypot(velocity.getX(), velocity.getY());
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

    public void setChemical(){ ballState = new ChemicalBallState(this); }

    public void reflect(GameObject object) {
        double incidenceAngle = Math.atan2(velocity.getY(), -velocity.getX());
        double normalAngle;
        if (object.getShape() == Shape.CIRCLE) {
            normalAngle = Math.atan2(object.getPosition().getY() - position.getY(),
                    position.getX() - object.getPosition().getX());
        }else if(object.getShape() == Shape.RECTANGLE) {
            normalAngle = Math.toRadians(object.getAngle() + reflectionDirection.ordinal() * 45.0);
        }else {
            normalAngle = incidenceAngle;
        }
        double difference = normalAngle - incidenceAngle;
        while(difference > Math.PI) difference -= Math.PI * 2.0;
        while(difference < -Math.PI) difference += Math.PI * 2.0;
        double reflectionAngle = normalAngle + difference;
        if(difference >= Math.PI / 2.0) reflectionAngle -= Math.PI / 2.0;
        if(-difference >= Math.PI / 2.0) reflectionAngle += Math.PI / 2.0;
        double len = Math.hypot(velocity.getX(), velocity.getY());
        velocity.setVector(Math.cos(reflectionAngle) * len, -Math.sin(reflectionAngle) * len);
    }

    @Override
    public void collide(GameObject object) {
        ballState.collide(object);
    }
}
