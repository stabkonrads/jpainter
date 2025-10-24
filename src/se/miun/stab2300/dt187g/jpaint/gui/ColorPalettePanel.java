package se.miun.stab2300.dt187g.jpaint.gui;

import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
* This class is used to instance the list for ColorPanels, add ColorPanels to list and display the added colors.
* The class extends from JPanel to be able to use the graphical elements.
* The class adds MouseListener to the ColorPalets to ensure that the ColorPanels are clickable.
*
* @author Stefan Abramsson (stab2300)
* @version 1.0
*/

public class ColorPalettePanel extends JPanel {
	private ArrayList<ColorPanel> colorPanels; 

	public ColorPalettePanel() {
		this.colorPanels = new ArrayList<ColorPanel>();
		this.setLayout(new GridLayout());
	}

	public ColorPalettePanel(ArrayList<ColorPanel> colorPanels) {
		this.colorPanels = colorPanels;
		this.setLayout(new GridLayout());

		for (ColorPanel cp : colorPanels) {
			this.add(cp);
		}
	}

	public void addColorPanel(ColorPanel cp) {
		this.colorPanels.add(cp);
		this.add(cp);
	}

	public void setMouseListenerForColorPanels(MouseListener listener) {
		for (ColorPanel cp : colorPanels) {
			cp.addMouseListener(listener);
		}
	}

}
