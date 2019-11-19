package brickingbad.domain.physics;

public class Vector {

    private int xval;
    private int yval;

    public Vector() {setVector(0, 0);}
    public Vector(int xval, int yval){
        setVector(xval, yval);
    }

    public void setVector(int xval, int yval){
        this.xval = xval;
        this.yval = yval;
    }

    public int getX(){
        return xval;
    }

    public int getY(){
        return yval;
    }

    public void addVector(Vector c){
        xval += c.getX();
        yval += c.getY();
    }

}
