package brickingbad.domain.physics.collisions;

import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.Shape;
import brickingbad.domain.physics.Direction;
import brickingbad.domain.physics.Line;
import brickingbad.domain.physics.Vector;

public class MixedCollisionStrategy implements ICollisionStrategy {
    @Override
    public boolean areColliding(GameObject o1, GameObject o2) {
        if(o1.getShape() == Shape.CIRCLE) return mixedColliding(o1, o2);
        return mixedColliding(o2, o1);
    }

    private boolean mixedColliding(GameObject circle, GameObject rect) {
        if(rect.getAngle() == 0.0) return basicMixedColliding(circle, rect);
        double circle_x = circle.getPosition().getX();
        double circle_y = circle.getPosition().getY();
        double radius = circle.getSize().getX() / 2.0;
        double rect_x = rect.getPosition().getX();
        double rect_y = rect.getPosition().getY();
        double rect_angle = Math.toRadians(rect.getAngle());
        double rect_half_x = rect.getSize().getX() / 2.0;
        double rect_half_y = rect.getSize().getY() / 2.0;
        Vector rect_x1 = new Vector(rect_x - Math.cos(rect_angle) * rect_half_x, rect_y + Math.sin(rect_angle) * rect_half_x);
        Vector rect_x2 = new Vector(rect_x + Math.cos(rect_angle) * rect_half_x, rect_y - Math.sin(rect_angle) * rect_half_x);
        Vector rect_y1 = new Vector(rect_x + Math.sin(rect_angle) * rect_half_y, rect_y + Math.cos(rect_angle) * rect_half_y);
        Vector rect_y2 = new Vector(rect_x - Math.sin(rect_angle) * rect_half_y, rect_y - Math.cos(rect_angle) * rect_half_y);
        Line line_x1 = new Line(rect_x1, Math.PI / 2.0 + rect_angle);
        Line line_x2 = new Line(rect_x2, Math.PI / 2.0 + rect_angle);
        Line line_y1 = new Line(rect_y1, rect_angle);
        Line line_y2 = new Line(rect_y2, rect_angle);
        Vector point_x1y1 = line_x1.intersection(line_y1);
        Vector point_x1y2 = line_x1.intersection(line_y2);
        Vector point_x2y1 = line_x2.intersection(line_y1);
        Vector point_x2y2 = line_x2.intersection(line_y2);

        if (line_x1.isVectorLeft(circle.getPosition())) {
            if (line_y1.isVectorBelow(circle.getPosition())) {
                if (Math.hypot(circle_x - point_x1y1.getX(), circle_y - point_x1y1.getY()) < radius) {
                    circle.setReflectionDirection(Direction.DOWN_LEFT);
                    return true;
                } else return false;
            } else if (line_y2.isVectorAbove(circle.getPosition())) {
                if (Math.hypot(circle_x - point_x1y2.getX(), circle_y - point_x1y2.getY()) < radius) {
                    circle.setReflectionDirection(Direction.UP_LEFT);
                    return true;
                } else return false;
            } else {
                if (line_x1.distanceToLine(circle.getPosition()) < radius) {
                    circle.setReflectionDirection(Direction.LEFT);
                    return true;
                } else return false;
            }
        } else if (line_x2.isVectorRight(circle.getPosition())) {
            if (line_y1.isVectorBelow(circle.getPosition())) {
                if (Math.hypot(circle_x - point_x2y1.getX(), circle_y - point_x2y1.getY()) < radius) {
                    circle.setReflectionDirection(Direction.DOWN_RIGHT);
                    return true;
                } else return false;
            } else if (line_y2.isVectorAbove(circle.getPosition())) {
                if (Math.hypot(circle_x - point_x2y2.getX(), circle_y - point_x2y2.getY()) < radius) {
                    circle.setReflectionDirection(Direction.UP_RIGHT);
                    return true;
                } else return false;
            } else {
                if (line_x2.distanceToLine(circle.getPosition()) < radius) {
                    circle.setReflectionDirection(Direction.RIGHT);
                    return true;
                } else return false;
            }
        } else {
            if (line_y1.isVectorBelow(circle.getPosition())) {
                if (line_y1.distanceToLine(circle.getPosition()) < radius) {
                    circle.setReflectionDirection(Direction.DOWN);
                    return true;
                } else return false;
            } else if (line_y2.isVectorAbove(circle.getPosition())) {
                if (line_y2.distanceToLine(circle.getPosition()) < radius) {
                    circle.setReflectionDirection(Direction.UP);
                    return true;
                } else return false;
            } else {
                circle.setReflectionDirection(Direction.DOWN);
                return true;
            }
        }
    }

    private boolean basicMixedColliding(GameObject circle, GameObject rect) {
        double circle_x = circle.getPosition().getX();
        double circle_y = circle.getPosition().getY();
        double radius = circle.getSize().getX() / 2.0;
        double rect_x1 = rect.getPosition().getX() - rect.getSize().getX() / 2.0;
        double rect_x2 = rect.getPosition().getX() + rect.getSize().getX() / 2.0;
        double rect_y1 = rect.getPosition().getY() + rect.getSize().getY() / 2.0;
        double rect_y2 = rect.getPosition().getY() - rect.getSize().getY() / 2.0;

        if (circle_x < rect_x1) {
            if (circle_y > rect_y1) {
                if (Math.hypot(circle_x - rect_x1, circle_y - rect_y1) < radius) {
                    circle.setReflectionDirection(Direction.DOWN_LEFT);
                    return true;
                } else return false;
            } else if (circle_y < rect_y2) {
                if (Math.hypot(circle_x - rect_x1, circle_y - rect_y2) < radius) {
                    circle.setReflectionDirection(Direction.UP_LEFT);
                    return true;
                } else return false;
            } else {
                if (rect_x1 - circle_x < radius) {
                    circle.setReflectionDirection(Direction.LEFT);
                    return true;
                } else return false;
            }
        } else if (circle_x > rect_x2) {
            if (circle_y > rect_y1) {
                if (Math.hypot(circle_x - rect_x2, circle_y - rect_y1) < radius) {
                    circle.setReflectionDirection(Direction.DOWN_RIGHT);
                    return true;
                } else return false;
            } else if (circle_y < rect_y2) {
                if (Math.hypot(circle_x - rect_x2, circle_y - rect_y2) < radius) {
                    circle.setReflectionDirection(Direction.UP_RIGHT);
                    return true;
                } else return false;
            } else {
                if (circle_x - rect_x2 < radius) {
                    circle.setReflectionDirection(Direction.RIGHT);
                    return true;
                } else return false;
            }
        } else {
            if (circle_y > rect_y1) {
                if (circle_y - rect_y1 < radius) {
                    circle.setReflectionDirection(Direction.DOWN);
                    return true;
                } else return false;
            } else if (circle_y < rect_y2) {
                if (rect_y2 - circle_y < radius) {
                    circle.setReflectionDirection(Direction.UP);
                    return true;
                } else return false;
            } else {
                circle.setReflectionDirection(Direction.DOWN);
                return true;
            }
        }
    }
}
