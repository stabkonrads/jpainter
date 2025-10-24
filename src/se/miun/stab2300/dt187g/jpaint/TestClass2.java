package se.miun.stab2300.dt187g.jpaint;

import se.miun.stab2300.dt187g.jpaint.geometry.Circle;
import se.miun.stab2300.dt187g.jpaint.geometry.Point;
import se.miun.stab2300.dt187g.jpaint.geometry.Rectangle;
import se.miun.stab2300.dt187g.jpaint.geometry.Shape;

/**
 * <h1>TestClass1</h1> This application creates different shapes and calls
 * various methods to print circumference, print area and draw the shapes to the
 * standard output.
 * <p>
 * Giving proper comments in your program makes it more user friendly and it is
 * assumed as a high quality code.
 * 
 *
 * @author Your Name (studentid)
 * @version 1.0
 */
public class TestClass2 {

	public static void main(String[] args) {
		
		testDrawing();
	}

	private static void testDrawing() {
		// Create an empty drawing and print info about it.
		System.out.println("Creating a new empty drawing...");
		
		Drawing d1 = new Drawing();
		
		System.out.println(d1);
		
		// Set name and author.
		System.out.println("\nSetting name and author...");
		
		try {
			d1.setName("Mona Lisa");
			d1.setAuthor("L. da Vinci");
			
		} catch (DrawingException e) {
		}
		
		System.out.println(d1);
		
		// Add five shapes with no end points
		System.out.println("\nAdding 5 shapes with no end points...");
		
		Shape face = new Circle(100,100, "#ffe0bd"); // RGB(255,224,189)
		Shape leftEye = new Circle(75, 75, "#0000ff"); // RGB(0, 0, 255)
		Shape rightEye = new Circle(125, 75, "#0000ff"); // RGB(0, 0, 255)
		Shape nose = new Rectangle(95, 100, "#000000"); // RGB(0, 0, 0)
		Shape mouth = new Rectangle(55, 130, "#ff0000"); // RGB(255, 0, 0)
		
		d1.addShape(face);
		d1.addShape(leftEye);
		d1.addShape(rightEye);
		d1.addShape(nose);
		d1.addShape(mouth);
		
		System.out.println(d1);
		
		// Add a null shape (size should not increase!).
		System.out.println("\nSize is: " + d1.getSize());
		System.out.println("Adding a null shape...");
		
		d1.addShape(null);
		
		System.out.println("Size is: " + d1.getSize());
		
		// Add end point to all shapes
		System.out.println("\nAdding end point to all shapes...");
		face.addPoint(175, 100);
		leftEye.addPoint(85, 75);
		rightEye.addPoint(135, 75);
		nose.addPoint(105, 115);
		mouth.addPoint(145, 140);
		System.out.println("Total circumference is: " + d1.getTotalCircumference());
		System.out.println("Total area is: " + d1.getTotalArea());
		
		// Draw the entire drawing
		System.out.println("\nDraw the drawing...");
		d1.draw();

		// Print area of nose
		System.out.println("\nArea of nose=" + nose.getArea());
		
		// Change end point of nose and print area again
		Point p = new Point(100, 110);
		System.out.println("Changing end point of nose to " + p);
		nose.addPoint(p);
		System.out.println("Area of nose=" + nose.getArea());
	}
}

