package brickingbad.domain.physics;

public class Coordinate {

    private int xcoor;
    private int ycoor;

    public Coordinate(int xcoor, int ycoor){
        setPosition(xcoor, ycoor);
    }

    public void setPosition(int xcoor, int ycoor){
        this.xcoor = xcoor;
        this.ycoor = ycoor;
    }

    public int getX(){
        return xcoor;
    }

    public int getY(){
        return ycoor;
    }

    public void add(Coordinate c){
        xcoor += c.getX();
        ycoor += c.getY();
    }

}
