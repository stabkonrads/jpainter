package se.miun.stab2300.dt187g.jpaint.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import se.miun.stab2300.dt187g.jpaint.gui.JPaintFrame.OnChangeListener;


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
	private OnChangeListener<StatusBarPanel> listener;

	private JLabel coordinates;
	private JPanel selectedColor;
	private JPanel leftPanel;
	private JPanel rightPanel;

	public StatusBarPanel() {
		this.setBackground(Color.LIGHT_GRAY);

		coordinates = new JLabel("0, 0");
		selectedColor = new JPanel();
		leftPanel = new JPanel();
		rightPanel = new JPanel();

		this.setLayout(new BorderLayout());

		selectedColor.setBackground(Color.BLUE);

		leftPanel.setBackground(Color.LIGHT_GRAY);
		JLabel leftLabel = new JLabel("Coordinates: ");
		leftPanel.add(leftLabel, BorderLayout.WEST);
		leftPanel.add(coordinates, BorderLayout.EAST);
		
		rightPanel.setBackground(Color.LIGHT_GRAY);
		JLabel rightLabel = new JLabel("Selected color: ");
		rightPanel.add(rightLabel, BorderLayout.WEST);
		rightPanel.add(selectedColor, BorderLayout.EAST);

		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);
	}

	public void updateCoordinates(int x, int y) {
		coordinates.setText(x+", " + y);
	}

	public void updateSelectedColor(Color color) {
		selectedColor.setBackground(color);
		
		this.listener.onChange(this);
		repaint();
	}


	public Color getSelectedColor() {
		return selectedColor.getBackground();
	}

	public void setOnChangeListener(OnChangeListener<StatusBarPanel> listener) {
		this.listener = listener;
	}
}
