package se.miun.stab2300.dt187g.jpaint.gui;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * <h1>Menu</h1>
 *	This class extends from JMenuBar and is used to create and add both JMenu and JMenuItem for the menubar.
 * 	It has a method that fetches a created component of both JMenu and JmenuItem and adds actionlisteners for the items.
 * 	It throws an error to the MenuManager if the parentName or itemName is null.
 * 
 * @author Stefan Abramsson
 * @version 0.1
 * @since 2025-09-26
 */
public class Menu extends JMenuBar {
	public void addJMenu(String name) {
		JMenu jMenu = new JMenu(name);
		this.add(jMenu);
	}

	public void addJMenuItem(String parentName, String itemName) {
		try {
			JComponent fetchedMenu = getComponentByName(parentName);
			if (fetchedMenu != null && fetchedMenu instanceof JMenu) {
				{
					JMenu menu = (JMenu) fetchedMenu;
					JMenuItem menuItem = new JMenuItem(itemName);
					
					menu.add(menuItem);
				}
			}
			else {
				System.err.println(parentName + " has been null.");
			}
		}
		catch (NullPointerException e) {
			System.err.println(parentName + " does not exist in the components for addJMenuItem.");
			e.printStackTrace();
		}
		 catch (Exception e) {
			System.err.println(parentName + " does not exist in the components for addJMenuItem.");
			e.printStackTrace();
		}
	}

	public void addJMenuItem(String parentName, String itemName, ActionListener al) {

		this.addJMenuItem(parentName, itemName);

		try {
			JComponent fetchedMenuItem = getComponentByName(itemName);
	
			if (fetchedMenuItem != null && fetchedMenuItem instanceof JMenuItem) {
				JMenuItem item = (JMenuItem) fetchedMenuItem;	
				item.addActionListener(al);
				}
		}  catch (Exception e) {
			System.err.println(parentName + " does not exist in the components for addJMenuItem.");
			e.printStackTrace();
		}
	}

	public void addJMenuItem(String parentName, String itemName, ActionListener al, KeyStroke keyStroke) {

		this.addJMenuItem(parentName, itemName, al);

		try {
			JComponent fetchedMenuItem = getComponentByName(itemName);
	
			if (fetchedMenuItem != null && fetchedMenuItem instanceof JMenuItem) {
				JMenuItem item = (JMenuItem) fetchedMenuItem;
				item.setAccelerator(keyStroke);
			}
		}  catch (Exception e) {
			System.err.println(parentName + " does not exist in the components for addJMenuItem.");
			e.printStackTrace();
		}
	}

	public void addSubJMenu(String parentName, String subMenuName) {
		
		try {
			JComponent parentJComponent = getComponentByName(parentName);
	
			if(parentJComponent != null && parentJComponent instanceof JMenu) {
				JMenu parentJMenu = (JMenu) parentJComponent;
				JMenu subJMenu = new JMenu(subMenuName);
				
				parentJMenu.add(subJMenu);
			}
		}  catch (Exception e) {
			System.err.println(parentName + " does not exist in the components for addSubJMenu.");
			e.printStackTrace();
		}
	}

	public JMenu getJMenu(int index) {
		return this.getMenu(index);
	}

	private JComponent getComponentByName(String name) {
		
		Component[] componentList = this.getComponents();

		for (Component component : componentList) {
			JMenu menu = (JMenu) component;

			String menuItemText = menu.getText();
			if(menuItemText.equals(name)) {
				return menu;
			}

			Component[] menuComponents = menu.getMenuComponents();
			for (Component menuComponent : menuComponents) {
				if (menuComponent instanceof JMenu) {
					JMenu subJMenu = (JMenu) menuComponent;
					menuItemText = subJMenu.getText();

					if(menuItemText.equals(name)) {
						return subJMenu;
					}
					Component[] subMenuComponents = subJMenu.getMenuComponents();

					for(Component subComponent : subMenuComponents) {
						JMenuItem subJMenuItem = (JMenuItem) subComponent;
						menuItemText = subJMenuItem.getText();

						if(menuItemText.equals(name)) {
							return subJMenuItem;
						}
					}
				}
				if(menuComponent instanceof JMenuItem) {
					JMenuItem menuItem = (JMenuItem) menuComponent;
					menuItemText = menuItem.getText();
					
					if (menuItemText.equals(name)) {
						return menuItem;
					}
				}
			}
		}

		return null;
	}

}
