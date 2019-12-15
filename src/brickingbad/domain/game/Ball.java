package brickingbad.domain.game;

import brickingbad.domain.physics.Vector;
import brickingbad.domain.physics.ball.BallState;
import brickingbad.domain.physics.ball.ChemicalBallState;
import brickingbad.domain.physics.ball.FireBallState;
import brickingbad.domain.physics.ball.SimpleBallState;

public class Ball extends GameObject {

    private final double PI = Math.PI;
    private BallState ballState = new SimpleBallState(this);
    private double paddleOffset;

    public Ball(Vector position) {
        this.shape = Shape.CIRCLE;
        this.size = new Vector(GameConstants.ballSize, GameConstants.ballSize);
        this.position = position;
        this.velocity = new Vector();
        this.paddleOffset = 0.0;
        this.angle = 0.0;
        this.dynamic = true;
    }

    public BallState getBallState() {
        return ballState;
    }

    public double getPaddleOffset() {
        return paddleOffset;
    }

    public void setPaddleOffset(double paddleOffset) {
        this.paddleOffset = paddleOffset;
    }

    public void startMovement(double angle, double speed) {
        velocity.setVector(-speed * Math.sin(Math.toRadians(angle)), -speed * Math.cos(Math.toRadians(angle)));
        this.angle = angle;
    }

    public double getSpeed() {
        return Math.hypot(velocity.getX(), velocity.getY());
    }

    public void stopMovement() {
        this.velocity.setVector(0.0, 0.0);
    }

    public void setSimple() {
        ballState = new SimpleBallState(this);
        publishStateToListener("");
    }

    public void setFireball() {
        ballState = new FireBallState(this);
        publishStateToListener("_fire");
    }

    public void setChemical() {
        ballState = new ChemicalBallState(this);
        publishStateToListener("_chemical");
    }

    public void reflect(GameObject object) {
        double incidenceAngle = Math.atan2(velocity.getY(), -velocity.getX());
        double ballAngle = incidenceAngle + PI;
        double objectAngle = Math.atan2(object.getVelocity().getY(), object.getVelocity().getX());
        double velocityDiff = Math.abs(ballAngle - objectAngle);
        while(velocityDiff > PI) velocityDiff -= PI * 2.0;
        double reflectionAngle;
        if(object.isDynamic()) {
            if(velocityDiff == PI / 2) {
                double newDiff = objectAngle - incidenceAngle;
                while(newDiff > Math.PI) newDiff -= Math.PI * 2.0;
                while(newDiff < -Math.PI) newDiff += Math.PI * 2.0;
                reflectionAngle = incidenceAngle + newDiff / 2.0;
                this.angle = reflectionAngle;
                double len = Math.hypot(velocity.getX(), velocity.getY());
                velocity.setVector(Math.cos(reflectionAngle) * len, -Math.sin(reflectionAngle) * len);
                return;
            }else if(velocityDiff > PI / 2) {
                reflectionAngle = incidenceAngle;
                this.angle = reflectionAngle;
                double len = Math.hypot(velocity.getX(), velocity.getY());
                velocity.setVector(Math.cos(reflectionAngle) * len, -Math.sin(reflectionAngle) * len);
                return;
            }
        }
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
        reflectionAngle = normalAngle + difference;
        if(difference >= Math.PI / 2.0) reflectionAngle -= Math.PI / 2.0;
        if(-difference >= Math.PI / 2.0) reflectionAngle += Math.PI / 2.0;
        this.angle = reflectionAngle;
        double len = Math.hypot(velocity.getX(), velocity.getY());
        velocity.setVector(Math.cos(reflectionAngle) * len, -Math.sin(reflectionAngle) * len);
        if(object.isDynamic() && velocityDiff < PI / 2) {
            velocity.sum(new Vector(5 * Math.cos(objectAngle), 5 * Math.sin(objectAngle)));
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        Game.getInstance().removeObject(this);
        Game.getInstance().anyBallLeft();
        System.out.println(Game.getInstance().getBalls().size());
    }

    @Override
    public void collide(GameObject object) {
        ballState.collide(object);
    }
}
