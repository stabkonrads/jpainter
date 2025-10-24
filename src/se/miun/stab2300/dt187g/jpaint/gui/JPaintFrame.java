package se.miun.stab2300.dt187g.jpaint.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
* This class is the class that stiches together ColorPalettePanel, DrawingPanel and StatusBarPanel.
* The class has a init() method that determins the size of the window, set exit action, set applikation name and icon.
* The class extends from JFrame to utilize grahical elemets for the applikation.
*
* @author Stefan Abramsson (stab2300)
* @version 1.0
*/
public class JPaintFrame extends JFrame {

	public static String APP_NAME = "JavaPainter";

	private Container c = this.getContentPane();
	private StatusBarPanel statusBarPanel;
	private DrawingPanel drawingPanel;
	private ColorPalettePanel colorPalettePanel;
	private String drawingTitle;

	public JPaintFrame() {
		init();
	}

	private void init() {
		this.setSize(500, 500);
		setExitAction();
		setIcon("img/JPaint.png");
		setHeader(APP_NAME);
		setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(0, 50));

		ArrayList<ColorPanel> colorPanels = setColorsForDrawing();
		colorPalettePanel = new ColorPalettePanel(colorPanels);

		JComboBox<String> shapesComboBox = setCbShapes();

		drawingPanel = new DrawingPanel();
		CustomMouseAdapter mouseAdapter = new CustomMouseAdapter();
		drawingPanel.addMouseListener(mouseAdapter);
		drawingPanel.addMouseMotionListener(mouseAdapter);

		statusBarPanel = new StatusBarPanel();
		statusBarPanel.setSize(5, 25);

		colorPalettePanel.setMouseListenerForColorPanels(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ColorPanel selectionColor = (ColorPanel) e.getSource();
				statusBarPanel.updateSelectedColor(selectionColor.getColor());
			}
		});

		statusBarPanel.setOnChangeListener(new OnChangeListener<StatusBarPanel>() {
			@Override
			public void onChange(StatusBarPanel object) {
				drawingPanel.setDrawColor((Color) object.getSelectedColor());
			}
		});

		topPanel.setLayout(new BorderLayout());
		topPanel.add(colorPalettePanel, BorderLayout.CENTER);
		topPanel.add(shapesComboBox, BorderLayout.EAST);
		
		c.add(topPanel, BorderLayout.PAGE_START);
		c.add(drawingPanel);
		c.add(statusBarPanel, BorderLayout.PAGE_END);

		Color startColor = Color.BLUE;
		drawingPanel.setActiveShape(shapesComboBox.getSelectedItem().toString());
		drawingPanel.setDrawColor(startColor);
		statusBarPanel.updateSelectedColor(startColor);

		setMenuManager();
	}
	
	private void setMenuManager() {
		MenuManager menuManager = new MenuManager(this, drawingPanel);
		setJMenuBar(menuManager.getMenu());
	}

	private ArrayList<ColorPanel> setColorsForDrawing() {
		ArrayList<ColorPanel> colorPanels = new ArrayList<ColorPanel>();
		colorPanels.add(new ColorPanel(Color.WHITE));
		colorPanels.add(new ColorPanel(Color.PINK));
		colorPanels.add(new ColorPanel(Color.RED));
		colorPanels.add(new ColorPanel(Color.ORANGE));
		colorPanels.add(new ColorPanel(Color.YELLOW));
		colorPanels.add(new ColorPanel(Color.GREEN));
		colorPanels.add(new ColorPanel(Color.CYAN));
		colorPanels.add(new ColorPanel(Color.BLUE));
		colorPanels.add(new ColorPanel(Color.MAGENTA));
		colorPanels.add(new ColorPanel(Color.BLACK));


		return colorPanels;
	}

	private JComboBox<String> setCbShapes() {
			String[] shapeStrings = {"Rectangle", "Circle"};
			JComboBox<String> shapesComboBox = new JComboBox<String>(shapeStrings);

			shapesComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					@SuppressWarnings("unchecked")
					JComboBox<String> selectedShape = (JComboBox<String>) e.getSource();
					drawingPanel.setActiveShape(selectedShape.getSelectedItem().toString());
				}
			});
			shapesComboBox.setSelectedItem(1);
			
			return shapesComboBox;
		}

	private void setIcon(String iconPath) {
		setIconImage(new ImageIcon(iconPath).getImage());
	}

	private void setHeader(String header) {
		this.setTitle(header);
	}

	private void setExitAction() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void updateHeader() {
		String updatedDrawingTitle = getDrawingTitle();
		this.setTitle(updatedDrawingTitle);
	}

	public void setDrawingTitle(String name, String author) {

		String trimedName = name.trim();
		String trimedAuthor = author.trim();
		String buildString = APP_NAME;

		if(!trimedName.isEmpty()) {
			buildString += " - " + trimedName;
		}
		else if(!author.isEmpty()) {
			buildString += " - [untitled drawing]";
		}

		if(!trimedAuthor.isEmpty()) {
			buildString += " by " + trimedAuthor;
		}
		
		setHeader(buildString);
	}
	
	public String getDrawingTitle() {
		return this.drawingTitle;
	}

	@FunctionalInterface
	public interface OnChangeListener<T> {
		void onChange(T object);
	}

	class CustomMouseAdapter extends MouseAdapter {
		@Override
		public void mouseDragged(MouseEvent e) {
			if (((Component) e.getSource()).getMousePosition() != null) {
				statusBarPanel.updateCoordinates(e.getX(), e.getY());
				
				drawingPanel.setEndPoint(e.getX(), e.getY());
				repaint();
			} else {
				statusBarPanel.updateCoordinates(0, 0);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			statusBarPanel.updateCoordinates(0, 0);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			statusBarPanel.updateCoordinates(e.getX(), e.getY());
		}

		@Override
		public void mousePressed(MouseEvent e) {
			drawingPanel.setDrawIsActive(true);
			drawingPanel.setStartPoint(e.getX(), e.getY());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			drawingPanel.setEndPoint(e.getX(), e.getY());
			drawingPanel.addShape();
			repaint();
			drawingPanel.setDrawIsActive(false);
		}
	}

}


