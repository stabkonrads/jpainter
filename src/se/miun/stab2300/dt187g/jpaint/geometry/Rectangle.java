package se.miun.stab2300.dt187g.jpaint.geometry;

import java.awt.Color;
import java.awt.Graphics;

/**
* This class is used for creating and printing a Rectangle.
* The Rectangle is implementing from both the parent class Shape and Drawable to make it possible to draw a shape.
* The methods are defined to gather information about the Rectangle: color, height, width, area and circumference,
*
* @author Stefan Abramsson (stab2300)
* @version 1.0
*/

public class Rectangle extends Shape {
    public Rectangle(Point p, String color) {
        super(p, color);
    }

    public Rectangle(double x, double y, String color) {
        this(new Point(x, y), color);
    }

    public double getWidth() {
        if(!hasEndpoint()) {
            return 0.0;
        }

        return Math.abs(this.points.getFirst().getX() - this.points.getLast().getX());
    }

    public double getHeight() {
        if(!hasEndpoint()) {
            return 0.0;
        }

        return Math.abs(this.points.getFirst().getY() - this.points.getLast().getY());
    }

    @Override
    public void draw() {
        System.out.println(toString());
    }

    @Override
    public void draw(Graphics g) {
        int startX = (int) this.points.getFirst().getX();
        int startY = (int) this.points.getFirst().getY();
        int width = (int) this.getWidth();
        int height = (int) this.getHeight();
        
        g.setColor(Color.decode(this.getColor()));

        g.fillRect(startX, startY, width, height);
    }

    @Override
    public double getCircumference() {
        if(!hasEndpoint()) {
            return 0.0;
        }

        double circumference = this.getWidth() * 2 + this.getHeight() * 2;            
        return circumference;
    }

    @Override
    public double getArea() {
        if(!hasEndpoint()) {
            return 0.0;
        }

        return this.getWidth() * this.getHeight(); 
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
        return !hasEndpoint() ? "Drawing a Rectangle[start=" + this.points.getFirst().toString() + 
        "; end=N/A" + 
        "; width=N/A; " + 
        "height=N/A; " + 
        "color=" + this.getColor() + 
        "]" 
        : 
        "Drawing a Rectangle[start= " + this.points.getFirst().toString() + 
        "; end=" + this.points.getLast().toString() + 
        "; width=" + this.getWidth() + 
        "; height=" + this.getHeight() + 
        "; color=" + this.getColor() + "]";
    }

    
}
