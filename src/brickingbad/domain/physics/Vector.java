package brickingbad.domain.physics;

public class Vector {

    private double xval;
    private double yval;

    public Vector() {setVector(0.0, 0.0);}
    public Vector(double xval, double yval){
        setVector(xval, yval);
    }

    public void setVector(double xval, double yval){
        this.xval = xval;
        this.yval = yval;
    }

    public double getX(){
        return xval;
    }

    public double getY(){
        return yval;
    }

    public void addVector(Vector c){
        xval += c.getX();
        yval += c.getY();
    }

    public void multiplyVector(double mul) {
        xval *= mul;
        yval *= mul;
    }

    public Vector sum(Vector c) {
        return new Vector(xval + c.getX(), yval + c.getY());
    }

    public Vector product(double mul) {
        return new Vector(xval * mul, yval * mul);
    }

    @Override
    public String toString() {
        return String.format("Vector: (%f, %f)", xval, yval);
    }
}
