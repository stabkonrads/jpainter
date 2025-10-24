package se.miun.stab2300.dt187g.jpaint;

import javax.swing.SwingUtilities;

import se.miun.stab2300.dt187g.jpaint.gui.JPaintFrame;

/**
* AppStart is the class that starts JPaint and makes it visible.
* 
*
* @author Stefan Abramsson (stab2300)
* @version 1.0
*/
public class AppStart {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new JPaintFrame().setVisible(true);
			}
		});
	}
}
