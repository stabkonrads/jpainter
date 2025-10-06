package se.miun.stab2300.dt187g.jpaint.geometry;

/**
* The Point class is used to define the start and end coordinates for Shape.
*
* @author Stefan Abramsson (stab2300)
* @version 1.0
*/

public class Point {
    
    private double x;
    private double y;

    public Point() {
        x = 0.0;
        y = 0.0;
    }

    public Point(double x, double y) {
        setX(x);
        setY(y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    private void setX(double setX) {
        this.x = setX;
    }

    private void setY(double setY) {
        this.y = setY;
    }

    @Override
    public String toString() {
        return "[" + getX() + ", " + getY() + "]";
    }
}
