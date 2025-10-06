package se.miun.stab2300.dt187g.jpaint;

/**
* This interface is used to define the methods draw() and draw(java.awt.Graphics g).
* It's used by the parent (Shape) and children (Rectangle and Circle) to print the objects.
*
* @author Stefan Abramsson (stab2300)
* @version 1.0
*/

public interface Drawable {

    void draw();
    void draw(java.awt.Graphics g);
}
