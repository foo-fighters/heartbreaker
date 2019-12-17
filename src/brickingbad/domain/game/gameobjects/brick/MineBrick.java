package brickingbad.domain.game.gameobjects.brick;

import brickingbad.domain.game.GameLogic;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.Shape;
import brickingbad.domain.physics.Vector;

public class MineBrick extends Brick {

    private Vector center;
    private boolean clockwise;
    private double rotationAngle = -Math.PI / 2;
    private double rotationRadius = GameConstants.mineBrickMovementRadius;
    private double rotationSpeed = GameConstants.brickMovementSpeed;
    private double rotationDelta =  rotationSpeed / rotationRadius / GameConstants.calculationsPerSecond;

    public MineBrick() {
        this.position = new Vector();
        this.shape = Shape.CIRCLE;
        this.size = new Vector(GameConstants.circularBrickSize, GameConstants.circularBrickSize);
        this.velocity = new Vector();
        this.angle = 0.0;
    }

    @Override
    public void startMovement() {
        double rand = Math.random();
        if (rand < GameConstants.brickMovementProbability) {
            this.dynamic = true;
            this.center = new Vector(position.getX(), position.getY() - rotationRadius);
            if(rand < GameConstants.brickMovementProbability / 2) {
                this.clockwise = true;
            }else {
                this.clockwise = false;
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        GameLogic.destroyBricksInRadius(position, GameConstants.mineBrickExplosionRadius);
    }

    @Override
    public void updatePosition() {
        if(dynamic) {
            if(clockwise) {
                rotationAngle += rotationDelta % (2 * Math.PI);
            }else {
                rotationAngle -= rotationDelta % (2 * Math.PI);
            }
            position.setVector(center.getX() + rotationRadius * Math.cos(rotationAngle),
                    center.getY() - rotationRadius * Math.sin(rotationAngle));
            if(clockwise) {
                velocity.setVector(rotationSpeed * Math.cos(rotationAngle - Math.PI / 2),
                        rotationSpeed * Math.sin(rotationAngle - Math.PI / 2));
            }else {
                velocity.setVector(rotationSpeed * Math.cos(rotationAngle + Math.PI / 2),
                        rotationSpeed * Math.sin(rotationAngle + Math.PI / 2));
            }
        }
    }

    @Override
    public void reflect() {
        clockwise = !clockwise;
    }



}
