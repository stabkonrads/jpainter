package se.miun.stab2300.dt187g.jpaint;


/**
* This exception is a custom exception to define if it's a DrawingException that has happened.
* It's used in a try catch element to handle if the case of input is null.
*
* @author Stefan Abramsson (stab2300)
* @version 1.0
*/
public class DrawingException extends Exception {
    public DrawingException() {
        super();
    }

    public DrawingException(String message) {
        super(message);
    }
}
