package se.miun.stab2300.dt187g.jpaint.geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
* This class is used for creating and printing a Circle.
* The Circle is implementing from both the parent class Shape and Drawable to make it possible to draw a shape based on coordinates.
* The startpoint of the circle is in the center and the endpoint is the outerline of the circle.
* The methods are defined to gather information about the Circle: color, radius, area and circumference.
*
* @author Stefan Abramsson (stab2300)
* @version 1.0
*/

public class Circle extends Shape {
    final double pi = 3.14159265;

    public Circle(Point p, String color) {
        super(p, color);
    }
    
    public Circle(double x, double y, String color) {
        this(new Point(x, y), color);
    }

    public Circle(double x1, double y1, double x2, double y2, String color) {
        this(new Point(x1, y1), color);
        addPoint(x2, y2);
    }

    public double getRadius() {
        if (!hasEndpoint()){
            return 0.0;
        }
        
        double radius = 0.0;
        
        double y1  = this.points.getFirst().getY();
        double y2  = this.points.getLast().getY();
        double x1  = this.points.getFirst().getX();
        double x2  = this.points.getLast().getX();

        return radius = Math.hypot(x2 - x1, y2 - y1);
    }
    
    @Override
    public void draw() {
        System.out.println(toString());
    }

    @Override
    public void draw(Graphics g) {
        double radius = getRadius(); 
        int startX = (int) this.points.getFirst().getX() - (int) radius;
        int startY = (int) this.points.getFirst().getY() - (int) radius;
        int correctedDiameter = (int) radius * 2;

        Graphics2D g2 = (Graphics2D) g;
	    
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(Color.decode(this.getColor()));
        g2.fillOval(startX, startY, correctedDiameter, correctedDiameter);
    }
    
    @Override
    public double getCircumference() {
        if(!hasEndpoint()) {
            return 0.0;
        }

        double radius = getRadius();
        double diameter = radius * 2; 

        return pi * diameter;
    }
    
    @Override
    public double getArea() {
        if(!hasEndpoint()) {
            return 0.0;
        }

        double radius = getRadius();

        return radius * radius * pi;
    }

    @Override
    public void addPoint(Point p) {
        this.points.addLast(p);
    }

    @Override
    public void addPoint(double x, double y) {
        this.addPoint(new Point(x, y));
    }

    @Override
    public boolean hasEndpoint() {
        if (this.points.size() > 1) {
            return true;
        }
        else
        return false;
    }

    @Override
    public String toString() {
        return !hasEndpoint() ? "Drawing a Circle[start=" + this.points.getFirst().toString() + 
        "; end=N/A; radius=N/A; color=" + this.getColor() + "]" 
        : 
        "Drawing a Circle[start=" + this.points.getFirst().toString() + 
        "; end=" + this.points.getLast().toString() + 
        "; radius=" + this.getRadius() + 
        "; color=" + this.getColor() + "]";
    }
}
