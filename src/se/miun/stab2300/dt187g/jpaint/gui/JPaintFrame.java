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

import se.miun.stab2300.dt187g.jpaint.Drawing;
import se.miun.stab2300.dt187g.jpaint.DrawingException;

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

	private String header;
	private Container c = this.getContentPane();
	private StatusBarPanel statusBarPanel;
	private DrawingPanel drawingPanel;
	private ColorPalettePanel colorPalettePanel;
	private String drawingTitle;

	public JPaintFrame() {
		init();
	}

	private void init() {

		// 1. Sätt storleken på JFrame till vad ni nu känner för.
		this.setSize(500, 500);

		// 2. Se till att programmet stängs ner när man trycker på krysset upp i högra
		// hörnet.
		setExitAction();

		/*
		 * 3. Välj ikon för programmet. Ni kan skapa en mapp som heter "img" i
		 * root-katalogen och hänvisa till den genom "img/filenameOfYourIcon.png".
		 */
		setIcon("img/JPaint.png");
		

		/*
		 * 4. Initialisera strängen "header" med något värde ("JPaint" exempelvis), och
		 * sätt detta som title för programmet. Att vi lagrar vårat applikationsnamn i
		 * en String kommer bli tydligare till kommande uppgifter.
		 */
		setHeader(APP_NAME);

		/*
		 * 5. Sätt layout för denna klass till BorderLayout
		 */
		setLayout(new BorderLayout());

		
		/*
		 * 6. Följande kod skapar en JPanel där vi sätter en önskad storlek på höjden
		 * genom att skicka ett Dimension-objekt till prefferedSize (Dimension(width,
		 * height)). Att vi anger width till 0 är mest för att vi inte kommer kunna
		 * påverka detta ändå (den kommer bli så bred som applikationen är bred). Det är
		 * detta JPanel-objekt som kommer inhysa våran ColorPalettePanel samt våran
		 * JComboBox (den som visar vilken typ av form vi ritar).
		 */
		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(0, 50));

		/*
		 * 7. Initialisera ColorPalettePanel. Om du väljer att initialisera
		 * ColorPalettePanel via "default"-constructorn (den utan argument), då måste du
		 * anropa addColorPanel för varje ColorPanel-objekt du vill lägga till.
		 * 
		 * Alternativ så anropar du ColorPalettePanel(ArrayList<ColorPanel>) och då
		 * sköter ColorPalettePanel resten
		 */
		ArrayList<ColorPanel> colorPanels = setColorsForDrawing();
		colorPalettePanel = new ColorPalettePanel(colorPanels);

		/*
		 * 8.
		 * 8.1 Skapa en String[] som håller "Rectangle" och "Circle" 
		 * 8.2 Skapa en JComboBox<String> och initalisera den med arrayen. 
		 * 8.3 Välj vilken form som ska vara default.
		 * 
		 * Våran JComboBox kommer vara bunden till den höjd som anges av topPanel.
		 * Däremot så har vi här möjlighet att ange bredd. Sätt bredden till något
		 * rimligt, exempelvis 100.
		 */
		JComboBox<String> shapesComboBox = setCbShapes();

		/*
		 * 9.
		 * 9.1 Initialisera DrawingPanel
		 * 9.2 Deklarera en CustomMouseAdapter och initialisera den.
		 * 9.3 Lägg till denna CustomMouseAdapter som MouseListener till drawingPanel
		 * 9.4 Lägg även till CustomMouseAdapter som MouseMotionListener till drawingPanel
		 */
		drawingPanel = new DrawingPanel();
		CustomMouseAdapter mouseAdapter = new CustomMouseAdapter();
		drawingPanel.addMouseListener(mouseAdapter);
		drawingPanel.addMouseMotionListener(mouseAdapter);
		
		/*
		 * 10.
		 * 10.1 Initialisera StatusBarPanel
		 * 10.2 Sätt en rimlig höjd på StatusBarPanel, exempelvis 25.
		 */
		statusBarPanel = new StatusBarPanel();
		statusBarPanel.setSize(5, 25);

		/*
		 * 11. Nu när StatusBarPanel väl är initialiserad så kan vi
		 * sätta en MouseListener för våra ColorPanel's. Eftersom vi inte har gått igenom
		 * anonyma klasser än, och eftersom det enkaste sättet att uträtta detta är genom en
		 * anonym klass, så följer den med här. 
		 * Ni måste fortfarande implementera mousePressed dock.
		 * Det vi vill ska hända är att när ett objekt klickas på, så ska dess bakgrundsfärg skickas
		 * som argument till StatusBarPanel.updateSelectedColor(Color color).
		 * Vi kommer behöva anropa MouseEvent.getSource() (i ren syntax innebär det alltså "e.getSource()".
		 * MouseEvent.getSource() returnerar ett Object. Vi kan inte få reda på bakgrundsfärgen bara genom 
		 * ett Object. Så vi måste "casta" det Object som returneras från getSource() till en ColorPanel.
		 * 
		 * 
		 * */
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

		/*
		 * 12.
		 * 12.1 Sätt layouten för topPanel till BorderLayout.
		 * 12.2 "adda" colorPalettePanel med lämplig constraint (dvs BorderLayout.LÄMPLIG_CONSTRAINT)
		 * 12.3 "adda" din JComboBox med lämplig constraint (dvs BorderLayout.LÄMPLIG_CONSTRAINT)
		 */
		topPanel.setLayout(new BorderLayout());
		topPanel.add(colorPalettePanel, BorderLayout.CENTER);
		topPanel.add(shapesComboBox, BorderLayout.EAST);
		
		/*
		 * 13. Avslutningsvis, lägg till topPanel, drawingPanel och statusBarPanel till 
		 * Container c.
		 */
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
		colorPanels.add(new ColorPanel(Color.BLUE));
		colorPanels.add(new ColorPanel(Color.WHITE));
		colorPanels.add(new ColorPanel(Color.MAGENTA));
		colorPanels.add(new ColorPanel(Color.YELLOW));
		colorPanels.add(new ColorPanel(Color.BLACK));
		colorPanels.add(new ColorPanel(Color.PINK));
		colorPanels.add(new ColorPanel(Color.ORANGE));
		colorPanels.add(new ColorPanel(Color.GREEN));
		colorPanels.add(new ColorPanel(Color.CYAN));
		colorPanels.add(new ColorPanel(Color.RED));


		return colorPanels;
	}

	private JComboBox<String> setCbShapes() {
			String[] shapeStrings = {"Rectangle", "Circle"};
			JComboBox<String> shapesComboBox = new JComboBox<String>(shapeStrings);

			shapesComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JComboBox selectedShape = (JComboBox) e.getSource();
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
		this.header = header;
		this.setTitle(header);
	}

	private void setExitAction() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void updateHeader() {
		String updatedDrawingTitle = getDrawingTitle();
		this.header = updatedDrawingTitle;
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
				//  Uppdatera koordinater i statusBarPanel
			} else {
				// Nollställ koordinater i statusBarPanel
				statusBarPanel.updateCoordinates(0, 0);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// Nollställ koordinater i statusBarPanel
			statusBarPanel.updateCoordinates(0, 0);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// Uppdatera koordinater i statusBarPanel
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


