package se.miun.stab2300.dt187g.jpaint;

import java.awt.JobAttributes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

import se.miun.stab2300.dt187g.jpaint.geometry.Circle;
import se.miun.stab2300.dt187g.jpaint.geometry.Point;
import se.miun.stab2300.dt187g.jpaint.geometry.Rectangle;
import se.miun.stab2300.dt187g.jpaint.geometry.Shape;

public class FileHandler {
    // TODO Create static and public methods for:
    // save(Drawing drawing, String fileName)
    // load(String fileName)
    
    // TODO save(Drawing drawing, String fileName) should check the fileName has .shape as fileextension.
    // If it does not have that extenstion it must add .shape as filetype.
    // TODO check if the file exists and ask user about action.
    public static void save(Drawing drawing, String fileName) {
        if(!fileName.endsWith(".shape")) {
            fileName += ".shape";
        } 
        try {
            File file = new File(fileName);
            if (file.exists()) {
                // TODO attach parentComponent to drawing.
                var result = JOptionPane.showConfirmDialog(null, "The file '" + fileName + 
                            "' already exists. Would you like to overwrite?", "warning", JOptionPane.ERROR_MESSAGE);
                switch (result) {
                    case JOptionPane.OK_OPTION:
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
                        
                    
                    
                    
                        // // TODO Breakout this to a method.
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
                        break;
                    case JOptionPane.NO_OPTION:
                        return;
                    default:
                        break;
                }
                return;
            }
                
            } catch (IOException e) {
                System.err.println("IOException, error while saving file: " + e.getMessage());
                
            }
        }   
    

    public static Drawing load(String fileName) throws FileNotFoundException {
        Drawing loadedDrawing = new Drawing();
        try {
            
            // TODO add || searchedFilename != fileName
            if (fileName == null) {
                throw new FileNotFoundException("Could not find file.");
            }

            BufferedReader output = new BufferedReader(new FileReader(fileName));
            String row = output.readLine();
            
            ArrayList<String> listOfStrings = new ArrayList<>();

            String drawingName = "";
            String authorName = "";
            
            while (row != null) {
                if(row == "[not specified]") {
                    listOfStrings.add(row);
                }
                else {
                    listOfStrings.add(row);
                }
                System.out.println(row);

                row = output.readLine();
            }
            drawingName = listOfStrings.get(0);
            authorName = listOfStrings.get(1);
            if(drawingName.equals("[not specified]")) {
            } else {
                loadedDrawing.setName(drawingName);
            }
            if(authorName.equals("[not specified]")) {
            } else {
                loadedDrawing.setAuthor(authorName);
            }
            

            // TODO Fix the creation of shapes and give better names.
            for (String string : listOfStrings) {
                String[] shapeArray = string.split(",");
                String shapeType = shapeArray[0];
                if (shapeType.equals("Circle")) {
                    Circle c = new Circle(Integer.parseInt(shapeArray[1]), Integer.parseInt(shapeArray[2]), 
                            Integer.parseInt(shapeArray[3]), Integer.parseInt(shapeArray[4]), shapeArray[shapeArray.length-1]);
                    loadedDrawing.addShape(c);
                }
                if (shapeType.equals("Rectangle")) {
                    Rectangle r = new Rectangle(Integer.parseInt(shapeArray[1]), Integer.parseInt(shapeArray[2]), 
                            Integer.parseInt(shapeArray[3]), Integer.parseInt(shapeArray[4]), shapeArray[shapeArray.length-1]);

                    loadedDrawing.addShape(r);
                }
            }

            output.close();
            
        } catch (FileNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            // TODO: handle exception
        }

        return loadedDrawing;
    }

    // TODO load(String fileName) returns a Drawing. It must use IO-streams (either java.io or java.nio.file) while loading a Drawing object.
    // Catch errors and write a appropriate errormessage with System.err.
    // TIPS while loading it could throw a FileNotFoundException in it. If that is the case from calling class it should pass a message to the user what the error is.

    // TODO The shape file should format according to Check assignment.
    // TODO coordinates should be casted to an int in this program.


}
