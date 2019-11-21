package brickingbad.domain.physics;

public class Line {

    private double slope;
    private double offset;
    private boolean isVertical;
    private double verticalX;

    public Line(double slope, double offset) {
        this.slope = slope;
        this.offset = offset;
    }

    public Line(Vector v, double angle) {
        if(angle == Math.PI / 2.0) {
            this.isVertical = true;
            this.verticalX = v.getX();
        }else {
            this.slope = Math.tan(angle);
            this.offset = v.getY() - v.getX() * slope;
        }
    }

    public boolean isVectorHigher(Vector v) {
        if(isVertical) {
            return isVectorRight(v);
        }else {
            return isVectorAbove(v);
        }
    }

    public boolean isVectorLower(Vector v) {
        if(isVertical) {
            return isVectorLeft(v);
        }else {
            return isVectorBelow(v);
        }
    }

    public boolean isVectorAbove(Vector v) {
        if(isVertical) {
            return false;
        }else {
            return v.getY() > v.getX() * slope + offset;
        }
    }

    public boolean isVectorBelow(Vector v) {
        if(isVertical) {
            return false;
        }else {
            return v.getY() < v.getX() * slope + offset;
        }
    }

    public boolean isVectorLeft(Vector v) {
        if(isVertical) {
            return v.getX() < verticalX;
        }else if(slope > 0.0) {
            return v.getY() > v.getX() * slope + offset;
        }else {
            return v.getY() < v.getX() * slope + offset;
        }
    }

    public boolean isVectorRight(Vector v) {
        if(isVertical) {
            return v.getX() > verticalX;
        }else if(slope > 0.0) {
            return v.getY() < v.getX() * slope + offset;
        }else {
            return v.getY() > v.getX() * slope + offset;
        }
    }

    public double distanceToLine(Vector v) {
        if(isVertical) {
            return Math.abs(v.getX() - verticalX);
        }else {
            return Math.abs(slope * v.getX() - v.getY() + offset) / Math.sqrt(Math.pow(slope, 2.0) + 1);
        }
    }

    public Vector intersection(Line l2){
        if(isVertical) {
            if(l2.isVertical) return null;
            return new Vector(verticalX, l2.slope * verticalX + l2.offset);
        }
        if(l2.isVertical) {
            return new Vector(l2.verticalX, this.slope * l2.verticalX + this.offset);
        }
        double x = (l2.offset - this.offset) / (l2.slope - this.slope);
        double y = slope * x + offset;
        return new Vector();
    }
}
