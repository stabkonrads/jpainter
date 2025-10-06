package se.miun.stab2300.dt187g.jpaint.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;


/**
* This class is used to create the statusbar for the tool coordinates and selectedColor. 
* The class extends from JPanel to use the graphical components.
* The coordinates are updated based on the mouse position and the selected color is based on current color.
*
* @author Stefan Abramsson (stab2300)
* @version 1.0
*/
public class StatusBarPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel coordinates;
	private JPanel selectedColor;
	private JPanel leftPanel;
	private JPanel rightPanel;

	public StatusBarPanel() {
		
		// 1. Sätt bakgrund på detta objekt
		this.setBackground(Color.LIGHT_GRAY);

		/*
		 * 2. Initialisera samtliga privata datafält.
		 *  För JLabel så är "0, 0" ett lämplig värde att skicka till konstruktorn.
		 */
		coordinates = new JLabel("0, 0");
		selectedColor = new JPanel();
		leftPanel = new JPanel();
		rightPanel = new JPanel();

		// 3. Sätt Layout till BorderLayout
		this.setLayout(new BorderLayout());
		
		/* 4. JPanel-objektet selectedColor ska visa den färg
		* som du anser ska vara den förvalda färgen i ritprogrammet
		* (rimligen en av de färger som finns i färgpaletten)
		*/
		selectedColor.setBackground(Color.BLUE);
		
		/* 5. leftPanel 
		 * - sätt bakgrunden för leftPanel
		 * - Skapa en JLabel där det står "Coordinates: ". Lägg till den till leftPanel via metoden add(Component comp, Object constraints),
		 * dvs den add-metod som tillåter oss ange ett "constraint", i detta fall BorderLayout.LÄMPLIG_CONSTRAINT.
		 * - Lägg sedan till instansvariabeln coordinates via samma add-metod som ovan.
		*/
		leftPanel.setBackground(Color.LIGHT_GRAY);
		JLabel leftLabel = new JLabel("Coordinates: ");
		leftPanel.add(leftLabel, BorderLayout.WEST);
		leftPanel.add(coordinates, BorderLayout.EAST);
		
		/* 6. rightPanel
		 * - sätt bakgrunden för rightPanel
		 * - Skapa en JLabel där det står "Selected color: ". Lägg till den till rightPanel på samma sätt som beskrivs i punkt 5.
		 * - Lägg sedan till instansvariabeln selectedColor via samma add-metod som ovan.
		*/
		rightPanel.setBackground(Color.LIGHT_GRAY);
		JLabel rightLabel = new JLabel("Selected color: ");
		rightPanel.add(rightLabel, BorderLayout.WEST);
		rightPanel.add(selectedColor, BorderLayout.EAST);

		/*
		 * 7. Lägg till leftPanel och rightPanel genom att anropa
		 * this.add(Component comp, Object constraints)
		 * för respektive panel.
		 */
		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);
	}

	/*
	 *  Uppdatera JLabel-objektet som visar koordinater med de nya värdena
	 */
	public void updateCoordinates(int x, int y) {
		coordinates.setText(x+", " + y);
	}
	
	// TODO Update the method according to the description
	/*
	 *  Uppdatera JPanel-objektet som visar vald färg med den nya färgen.
	 */
	public void updateSelectedColor(Color color) {
		selectedColor.setBackground(color);
		repaint();
	}

	// TODO Update method to get selected color, check if it is done.
	public Color getSelectedColor() {
		return selectedColor.getBackground();
	}
	
	// TODO Create the interface for OnChangeListener
	// public void setOnChangeListener(OnChangeListener<StatusBarPanel> listener) {

	// }
}
