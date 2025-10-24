package se.miun.stab2300.dt187g.jpaint;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import se.miun.stab2300.dt187g.jpaint.geometry.Circle;
import se.miun.stab2300.dt187g.jpaint.geometry.Point;
import se.miun.stab2300.dt187g.jpaint.geometry.Rectangle;
import se.miun.stab2300.dt187g.jpaint.geometry.Shape;

public class FileHandler {
    public static void save(Drawing drawing, String fileName) {
        if(!fileName.endsWith(".shape")) {
            fileName += ".shape";
        } 
        try {
            File file = new File(fileName);

            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            String trimmedDrawingName = drawing.getName().trim();
            String trimmedAuthorName = drawing.getAuthor().trim();
            if(!trimmedDrawingName.isEmpty()) {
                bw.write(drawing.getName());
                bw.newLine();
            }else {
                bw.write("[not specified]");
                bw.newLine();
            }
        
            if(!trimmedAuthorName.isEmpty()) {
                bw.write(drawing.getAuthor());
            } else {
                bw.write("[not specified]");
            }

            List<Shape> shapes = drawing.getShapes();
            for (Shape shape : shapes) {
                bw.newLine();
                String stringBuilding = "";
                String pointsString = new String();
                List<Point> points = shape.getPoints();
            
                for (Point point : points) {
                    int x = (int)point.getX();
                    int y = (int)point.getY();
                    pointsString += x + "," + y + ",";
                }
            
                if(shape instanceof Circle) {
                    stringBuilding = "Circle";
                }
                if(shape instanceof Rectangle) {
                    stringBuilding = "Rectangle";
                }
                stringBuilding += "," + pointsString +  shape.getColor();
                bw.write(stringBuilding);
            }
            file.createNewFile();
            bw.close();
        } catch (IOException e) {
            System.err.println("IOException, error while saving file: " + e.getMessage());
        }
    }   
    
    public static Drawing load(String fileName) throws FileNotFoundException {
        Drawing loadedDrawing = new Drawing();
        try {
            if (fileName == null) {
                throw new FileNotFoundException("Could not find file.");
            }

            BufferedReader output = new BufferedReader(new FileReader(fileName));
            String row = output.readLine();
            
            ArrayList<String> loadedShapesInfo = new ArrayList<>();

            String drawingName = "";
            String authorName = "";
            
            while (row != null) {
                if(row == "[not specified]") {
                    loadedShapesInfo.add(row);
                }
                else {
                    loadedShapesInfo.add(row);
                }
                row = output.readLine();
            }
            drawingName = loadedShapesInfo.get(0);
            authorName = loadedShapesInfo.get(1);
            
            if(drawingName.equals("[not specified]")) {
            } else {
                loadedDrawing.setName(drawingName);
            }
            if(authorName.equals("[not specified]")) {
            } else {
                loadedDrawing.setAuthor(authorName);
            }

            for (String shapeInfoLine : loadedShapesInfo) {
                String[] shapeInfoArray = shapeInfoLine.split(",");
                String shapeType = shapeInfoArray[0];
                if(shapeType.equals("Circle") || shapeType.equals("Rectangle")) {
                    int x1 = Integer.parseInt(shapeInfoArray[1]);
                    int y1 = Integer.parseInt(shapeInfoArray[2]);
                    int x2 = Integer.parseInt(shapeInfoArray[3]);
                    int y2 = Integer.parseInt(shapeInfoArray[4]);
                    String color = shapeInfoArray[shapeInfoArray.length-1];
                    if (shapeType.equals("Circle")) {
                        Circle c = new Circle(x1, y1, x2, y2, color);
                        loadedDrawing.addShape(c);
                    }
                    if (shapeType.equals("Rectangle")) {
                        Rectangle r = new Rectangle(x1, y1, x2, y2, color);
                        loadedDrawing.addShape(r);
                    }
                }
            }
            output.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error while loading, file not found: " + e.getMessage());
            e.printStackTrace();
            throw new FileNotFoundException("Error while loading: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException thrown: " + e.getMessage());
            e.printStackTrace();
        } catch (DrawingException e) {
            System.err.println("DrawingException thrown: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("General exception: " + e.getMessage());
            e.printStackTrace();
        }
        return loadedDrawing;
    }
}
