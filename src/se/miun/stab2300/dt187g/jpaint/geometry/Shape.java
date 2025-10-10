package se.miun.stab2300.dt187g.jpaint.geometry;

import se.miun.stab2300.dt187g.jpaint.Drawable;

import java.util.List;
import java.util.ArrayList;

/**
* This abstract class is used for defining the start point/end point and is used by the subclasses.
* It implements the methods draw from Drawable that makes it possible to print the shapes.
*
* @author Stefan Abramsson (stab2300)
* @version 1.0
*/

public abstract class Shape implements Drawable {
    private String color;
    protected List<Point> points;

    public Shape(Point p, String color) {
        this.points  = new ArrayList<Point>();
        this.points.addFirst(p);
        this.color = color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public abstract double getCircumference();
    public abstract double getArea();

    public void addPoint(Point p) {
        points.addLast(p);
    } 

    public void addPoint(double x, double y) {
        this.addPoint(new Point(x, y));
    }

    // TODO Create a getmethod for returning list of points
    public List<Point> getPoints() {
        return points;
    }

    public abstract boolean hasEndpoint();
}
