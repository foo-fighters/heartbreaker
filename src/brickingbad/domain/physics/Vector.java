package brickingbad.domain.physics;

/**
 * Vector is responsible for creating a vector.
 */
public class Vector {
    // OVERVIEW: A positional vector on a 2-dimensional plane. Has x-axis and y-axis values.

    /**
     * the private value for horizontal line of the vector
     */
    private double xval;
    /**
     * the private value for vertical line of the vector
     */
    private double yval;

    public Vector() {setVector(0.0, 0.0);}
    public Vector(double xval, double yval){
        setVector(xval, yval);
    }

    public double getX(){
        return xval;
    }

    public double getY(){
        return yval;
    }

    public void setVector(double xval, double yval) {
        this.xval = xval;
        this.yval = yval;
    }

    /**
     * sums the vectors with respect to their x and y values
     * @param c is a vector that will be added to the current vector
     */
    public void addVector(Vector c) {
        // MODIFIES: this.
        // EFFECTS: adds c to the x and y values of this vector.
        xval += c.getX();
        yval += c.getY();
    }

    /**
     * sums the vectors with respect to their x and y values
     * @param c is the vector that will be added to the current vector
     * @return the summation as a Vector
     */
    public Vector sum(Vector c) {
        // EFFECTS: returns a new vector with x and y values that are sums of this and the given vector's.
        return new Vector(xval + c.getX(), yval + c.getY());
    }

    /**
     *  multiplies the vector by a given multiplicity
     * @param mul is the multiplicity that will be multiplied by the vector
     * @return the product as a Vector
     */
    public Vector product(double mul) {
        // EFFECTS: returns a new vector with x and y values that are multiples of this vector's with mul.
        return new Vector(xval * mul, yval * mul);
    }

    /**
     * it converts the format to the string
     * @return the vector as a String 
     */
    @Override
    public String toString() {
        // EFFECTS: returns a string which is formatted to show the x and y values of this vector.
        return String.format("Vector: (%f, %f)", xval, yval);
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof Vector)) return false;
        Vector vector = (Vector) object;
        return vector.getX() == xval && vector.getY() == yval;
    }

    public boolean repOK() {
        return (Double.class.isInstance(xval) && Double.class.isInstance(yval));
    }

}
