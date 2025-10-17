import se.miun.stab2300.dt187g.jpaint.*;
import se.miun.stab2300.dt187g.jpaint.geometry.Circle;
import se.miun.stab2300.dt187g.jpaint.geometry.Rectangle;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        Drawing d = new Drawing("hej", "hej");

        for(int i = 0; i < 10; i++) {
            Circle c = new Circle(new se.miun.stab2300.dt187g.jpaint.geometry.Point(i, i), "#0000ff");
            c.addPoint(i + 1, i + 1);

            Rectangle r = new Rectangle(new se.miun.stab2300.dt187g.jpaint.geometry.Point(i, i), "#0000ff");
            r.addPoint(i+1, i+1);
            
            d.addShape(c);
            d.addShape(r);
        }

        FileHandler.save(d, "test.shape");
        FileHandler.load("test.shape");
    }
}
