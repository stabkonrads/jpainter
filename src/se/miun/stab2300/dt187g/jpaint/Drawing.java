package se.miun.stab2300.dt187g.jpaint;

import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;

import se.miun.stab2300.dt187g.jpaint.geometry.Shape;

/**
* This class is used to setup a drawing featuring drawing name, who wrote it and a collection with created shapes.
* It presents the shapes with details.
*
* @author Stefan Abramsson (stab2300)
* @version 1.0
*/

public class Drawing implements Drawable {
    private String name;
    private String author;
    private List<Shape> shapes;

    public Drawing() {
        setAuthor();
        setName();
        shapes = new ArrayList<Shape>();
    }

    public Drawing(String name, String author) throws DrawingException{
        String n = name.trim();
        if(name == null || n.isEmpty()) {
            throw new DrawingException("Name and author can't be null or empty.");
        }
        String a = author.trim();
        if(author == null || a.isEmpty()) {
            throw new DrawingException("Name and author can't be null or empty.");
        }
        
            setName(name);
            setAuthor(author);
            shapes = new ArrayList<Shape>();
    }

    private void setAuthor() {
        this.author = "";
    }

    public void setAuthor(String author) throws DrawingException{
        if(author == null) {
            throw new DrawingException("Author cant't be null.");
        }
        String a = author.trim();
        if(author.isEmpty() || a.isEmpty()) {
            throw new DrawingException("Author can't be empty.");
        }

        this.author = author;
    }

    private void setName() {
        this.name = "";
    }
    
    public void setName(String name) throws DrawingException{
        if(name == null) {
            throw new DrawingException("Name can't be null.");
        }
        String n = name.trim();
        if(name.isEmpty() || n.isEmpty()) {
            throw new DrawingException("Name can't be empty.");
        }

        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public void addShape(Shape shape) {
        if (shape != null) {
            shapes.add(shape);
        }
    }

    public int getSize() {
        return shapes.size();
    }

    public double getTotalCircumference() {
        double totalCircumference = 0;

        for (Shape shape : shapes) {
            totalCircumference += shape.getCircumference();
        }

        return totalCircumference;
    }

    public double getTotalArea() {
        double totalArea = 0;

        for (Shape shape : shapes) {
            totalArea += shape.getArea();
        }

        return totalArea;
    }

    public void removeShape(int index) {
        shapes.remove(index);
    }
    
    public List<Shape> getShapes() {
        return shapes;
    }


    @Override
    public void draw() {
        shapes.stream()
            .forEach(System.out::println);
    }

    // Iterates through the shapes and draws them on the drawing.
    @Override
    public void draw(Graphics g) {
        shapes.stream()
            .forEach(s -> s.draw(g));
    }

    @Override
    public String toString() {
        return "Drawing[name= " + this.name + 
                        "; author= " + this.author + 
                        "; size=" + this.getSize() + 
                        "; circumference=" + this.getTotalCircumference() + 
                        "; area=" + this.getTotalArea() + "]";
    }   
}