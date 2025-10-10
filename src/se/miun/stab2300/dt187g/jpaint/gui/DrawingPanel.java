package se.miun.stab2300.dt187g.jpaint.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.function.Predicate;

import javax.swing.JPanel;

import se.miun.stab2300.dt187g.jpaint.Drawing;
import se.miun.stab2300.dt187g.jpaint.geometry.Circle;
import se.miun.stab2300.dt187g.jpaint.geometry.Point;
import se.miun.stab2300.dt187g.jpaint.geometry.Rectangle;
import se.miun.stab2300.dt187g.jpaint.geometry.Shape;

/**
* This class is used to set the background of the drawing area to a color.
* The base constructor is set to white and the one that takes an Color argument is used for another base color.
* The class extends JPanel to be able to use graphical elements.
*
* @author Stefan Abramsson (stab2300)
* @version 1.0
*/
public class DrawingPanel extends JPanel {
	private Drawing drawing;

	private Color drawColor;
	private boolean drawIsActive;
	private String activeShape;

	private Predicate<Shape> shapeFilter;

	private int x1;
	private int y1;
	private int x2;
	private int y2;

	/*
	 * Beroende på vilken konstruktor som anropas så kommer antingen 
	 * bakgrunden för denna komponent sättas till den färg som skickas som argument,
	 * alternativt sätts bakgrunden till vit (Color.WHITE).
	 */
	public DrawingPanel() {
		this.setBackground(Color.WHITE);
		drawIsActive = false;
		drawing = new Drawing();
	}
	
	public DrawingPanel(Color background) {
		this();
		this.setBackground(background);
	}

	public Drawing getDrawing() {
		return this.drawing;
	}
	
	public void setDrawing(Drawing drawing) {
		this.drawing = drawing;
	}

	public Color getDrawColor() {
		return this.drawColor;
	}

	public void setDrawColor(Color color) {
		this.drawColor = color;
	}

	public boolean getDrawIsActive() {
		return this.drawIsActive;
	}

	public void setDrawIsActive(boolean active) {
		this.drawIsActive = active;
	}

	public void setShapeFilter(Predicate<Shape> shapeFilter) {
		this.shapeFilter = shapeFilter;
		repaint();
	}

	public String getActiveShape() {
		return this.activeShape;
	}

	public void setActiveShape(String shape) {
		this.activeShape = shape;
	}

	public void setStartPoint(int x1, int y1) {
		this.x1 = x1;
		this.y1 = y1;
	}

	public void setEndPoint(int x2, int y2) {
		this.x2 = x2;
		this.y2 = y2;
	}

	public void removeShape(int index) {
		drawing.removeShape(index);
	}

	public void addShape() {
		switch (activeShape) {
		case "Rectangle":
			Point rectStart = new Point(x1 < x2 ? x1 : x2, y1 < y2 ? y1 : y2);
			Point rectEnd = new Point(x1 > x2 ? x1 : x2, y1 > y2 ? y1 : y2);
			Shape rect = new Rectangle(rectStart, getColorAsHexString(drawColor));
			rect.addPoint(rectEnd);
			drawing.addShape(rect);
			break;
		case "Circle":
			Point circleStart = new Point(x1, y1);
			Point circleEnd = new Point(x2, y1);
			Shape circle = new Circle(circleStart, getColorAsHexString(drawColor));
			circle.addPoint(circleEnd);
			drawing.addShape(circle);
			break;
		default:
			break;
		}
	}

	private void drawRect(Graphics2D g2) {
	java.awt.Shape rect = new java.awt.Rectangle(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2),
			Math.abs(y1 - y2));
	g2.fill(rect);
	}

	private void drawCircle(Graphics2D g2) {
		java.awt.Shape circle = new Ellipse2D.Double(x1 - Math.abs(x1 - x2), y1 - Math.abs(x1 - x2),
				Math.abs(x1 - x2) * 2, Math.abs(x1 - x2) * 2);
		g2.fill(circle);
	}

	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    drawing.draw(g);
	    Graphics2D g2 = (Graphics2D) g;
	    if (drawIsActive) {
	        g2.setColor(drawColor);
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        switch (activeShape) {
	        case "Rectangle":
	            drawRect(g2);
	            break;
	        case "Circle":
	            drawCircle(g2);
	            break;
	        default:
	            break;
	        }
	    }
	}

	public static String getColorAsHexString(Color color) {

	    String red = Integer.toHexString(color.getRed());
	    String green = Integer.toHexString(color.getGreen());
	    String blue = Integer.toHexString(color.getBlue());
	    red = red.length() == 1 ? "0"+red : red;
	    green = green.length() == 1 ? "0"+green : green;
	    blue = blue.length() == 1 ? "0"+blue : blue;

	    return "#" + red + green + blue;
	}
}
