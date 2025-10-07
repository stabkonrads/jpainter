package se.miun.stab2300.dt187g.jpaint.gui;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import se.miun.stab2300.dt187g.jpaint.Drawing;
import se.miun.stab2300.dt187g.jpaint.DrawingException;

/**
 * <h1>MenuManager</h1>
 *	MenuManager is used to create the Menu witch creates JMenu, JMenuItem and adds both ActionListeners and KeyStroke to JMenuItem.
 *	It creates the avalible ActionListeners to create a new drawing, add author or add name of the drawing.
 * 
 * @author Stefan Abramsson
 * @version 0.1
 * @since 2025-09-26
 */
public class MenuManager {
    private JPaintFrame frame;
    private DrawingPanel drawingPanel;
    private Menu menu;
    

    public MenuManager(JPaintFrame frame, DrawingPanel drawingPanel) {
        this.frame = frame;
        this.drawingPanel = drawingPanel;
        this.menu = new Menu();
		
        createMenu();
    }
    
    public Menu getMenu() {
        return menu;
    }

    private void createMenu() {
        createFileMenu();
        createEditMenu();
        createFilterMenu(); // Empty for now
    }

    private void createFileMenu() {
		String sFile = "File";
		menu.addJMenu(sFile);
		menu.getJMenu(0).setMnemonic(KeyEvent.VK_F);

		menu.addJMenuItem(sFile, "New...", createNewDrawingAction(),
				KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		menu.addJMenuItem(sFile, "Load...", createLoadAction(),
				KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));
		menu.addJMenuItem(sFile, "Save As...", createSaveAction(),
				KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		menu.addJMenuItem(sFile, "Info", showInfoAction());

		menu.getJMenu(0).addSeparator();
		menu.addJMenuItem(sFile, "Exit", al -> System.exit(0));

	}

    private void createEditMenu() {
		String sEdit = "Edit";
		String sDrawing = "Drawing";
		menu.addJMenu(sEdit);
		menu.addSubJMenu(sEdit, sDrawing);
		menu.getJMenu(1).setMnemonic(KeyEvent.VK_E);

		menu.addJMenuItem(sEdit, "Undo", createUndoAction(),
				KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
		menu.addJMenuItem(sDrawing, "Name...", createChangeNameAction());
		menu.addJMenuItem(sDrawing, "Author...", createChangeAuthorAction());

		/* Denna rad, som du inte får ta bort, kommer skapa ett NullException.
		 * Du måste hantera denna situation i Menu-klassen. I vanliga fall
		 * hade det varit rimligt att ett Exception kastades (klienten bör 
		 * i vanliga fall göras medveten om att den försöker skapa ett 
		 * JMenuItem till en JMenu som inte existerar), men nu räcker
		 * det med att ingenting alls händer i det läget man anropar
		 * addJMenuItem med en sträng som inte kan hittas.
		 */
		menu.addJMenuItem("This JMenu doesn't exist", "abc");

	}
    
    private void createFilterMenu() {
		// TODO for assignment 6
	}
    
    /*
     * Flera av metoderna nedan kommer anropa JOptionPane.showInputDialog(...).
     * Denna metod returnerar en String. Tänk på att om användaren trycker på
     * "Cancel" så kommer null att returneras. När en användare trycker på "Cancel"
     * så ska givetvis ingenting alls hända; inget felmeddelande till användaren,
     * inget ändring av det grafiska gränssnittets tillstånd (en teckning ska
     * inte plötsligt få namnet "null"). Jag har sett många inlämningar där
     * "Cancel" har hanterats på tämligen oväntade sätt. Så håll det i åtanke,
     * att Cancel/Avbryt innebär just den saken.
     * 
     */
    
    private ActionListener createNewDrawingAction() {
		return al -> {
			String nameInputDialog = JOptionPane.showInputDialog(drawingPanel, "Enter name of the drawing: ", "Enter drawing name", JOptionPane.QUESTION_MESSAGE);
			if (nameInputDialog == null)
				return;

			String authorInputDialog = JOptionPane.showInputDialog(drawingPanel, "Enter name of the author: ", "Enter author name", JOptionPane.QUESTION_MESSAGE);
			if(authorInputDialog == null)
				return;

			try {
					Drawing createdDrawing = new Drawing(nameInputDialog, authorInputDialog);
					drawingPanel.setDrawing(createdDrawing);
					frame.setDrawingTitle(nameInputDialog, authorInputDialog);
				} catch (DrawingException e) {

				switch (e.getMessage()) {
					case "Name can't be null.":
						JOptionPane.showMessageDialog(drawingPanel, e.getMessage(), "Missing name", 0);
						e.printStackTrace();
						break;
					case "Name can't be empty.":
						JOptionPane.showMessageDialog(drawingPanel, e.getMessage(), "Missing name", 0);
						e.printStackTrace();
						break;	
					case "Author cant't be null.":
						JOptionPane.showMessageDialog(drawingPanel, e.getMessage(), "Missing author", 0);
						e.printStackTrace();
						break;
					case "Author can't be empty.":
						JOptionPane.showMessageDialog(drawingPanel, e.getMessage(), "Missing author", 0);
						e.printStackTrace();
						break;
					case "Name and author can't be null or empty.":
						JOptionPane.showMessageDialog(drawingPanel, e.getMessage(), "Missing name and author", 0);
						e.printStackTrace();
					default:
						break;
					}
				}
		};
	}

    private ActionListener createChangeNameAction() {
		return al -> {
			String nameInput = JOptionPane.showInputDialog(drawingPanel,"Would you like to change name for drawing?", 
															"Change name for drawing?", JOptionPane.QUESTION_MESSAGE);
			if ( nameInput == null)
			return;

			try {
				Drawing d = drawingPanel.getDrawing();
				d.setName(nameInput);
				frame.setDrawingTitle(nameInput, d.getAuthor());

			} catch (DrawingException e) {
				JOptionPane.showMessageDialog(drawingPanel, e.getMessage(), nameInput, 0);
				e.printStackTrace();

			} catch (Exception e) {
				System.err.println("An error has occurred at createChangeNameAction. " + e.getMessage());
				e.printStackTrace();
			}
		};
	}

	private ActionListener createChangeAuthorAction() {
		return al -> {
			String authorInput = JOptionPane.showInputDialog(drawingPanel,"Would you like to change name of author of the painting?", 
															"Would you like to change name of author of the painting?", JOptionPane.QUESTION_MESSAGE);
			if ( authorInput == null)
			return;
			
			try {
				Drawing d = drawingPanel.getDrawing();
				d.setAuthor(authorInput);
				frame.setDrawingTitle(d.getName(), authorInput);;
				
			} catch (DrawingException e) {
				JOptionPane.showMessageDialog(drawingPanel, e.getMessage(), authorInput, 0);
				e.printStackTrace();

			} catch (Exception e) {
				System.err.println("An error has occurred at createChangeAuthorAction. " + e.getMessage());
				e.printStackTrace();
			}
		};
	}

	private ActionListener createUndoAction() {
		return al -> {
			try {
				Drawing d = drawingPanel.getDrawing();
				
				if(d.getSize() == 0) {
					return;
				}
				int latestShape = d.getSize() - 1;

				drawingPanel.removeShape(latestShape);
				drawingPanel.repaint();
			} catch (Exception e) {
				System.err.println("Error at createUndoAction: " + e.getMessage());
			}
		};
	}
	
	private ActionListener showInfoAction() {
		return al -> {
			Drawing d = drawingPanel.getDrawing();
			String drawingName = getDrawingAuthorName();

			JOptionPane.showMessageDialog(drawingPanel, 
						drawingName + "\n" + 
						"Number of Shapes: " + d.getSize() + "\n" +
						"Total area: " + d.getTotalArea() + "\n" + 
						"Total circumference: " + d.getTotalCircumference(), 
						"Info", 
						1);
		};
	}

	private ActionListener createLoadAction() {
		return al -> {
			// TODO for assignment 6
		};
	}

	private ActionListener createSaveAction() {
		return al -> {
			// TODO for assignment 6
		};
	}

	private String getDrawingAuthorName() {
		Drawing d = drawingPanel.getDrawing();
		String author = d.getAuthor();
		String name = d.getName();

		String trimedName = name.trim();
		String trimedAuthor = author.trim();
		String buildString = "";

		if(trimedName.isEmpty() && trimedAuthor.isEmpty()) {
			return "[Unnamed Drawing]";
		}

		if(!trimedName.isEmpty()) {
			buildString += trimedName;
		}
		else if(!author.isEmpty()) {
			buildString += "[untitled drawing]";
		}

		if(!trimedAuthor.isEmpty()) {
			buildString += " by " + trimedAuthor;
		}

		return buildString;
	}

}
