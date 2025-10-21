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

	/*
	 * Eftersom Menu utökar JMenuBar så kommer en JMenu läggas till på precis samma
	 * sätt som man lägger till en JMenu till en JMenuBar
	 */
	public void addJMenu(String name) {
		JMenu jMenu = new JMenu(name);
		this.add(jMenu);
	}

	/*
	 * Använd metoden getComponentByName (du måste skriva den först)
	 * för att hitta vilken JMenu som ett JMenuItem ska läggas till.
	 * Tänk på att metoden getComponentByName kan returnera null!
	 */
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

	/*
	 * Ni får själv bestämma om ni vill anropa addJMenuItem(parentName, itemName)
	 * för att på så sätt "kedja" anropen och göra koden lättare att underhålla.
	 * ActionListener ska helt enkelt "addas" till det JMenuItem vi skapar.
	 */
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

	/*
	 * För att kunna använda tangentbord-kommandon för att nå meny-alternativ, så anropar man
	 * metoden setAccelerator(KeyStroke keyStroke).
	 */
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

	/*
	 * Denna metod kommer lägga till ett JMenu-objekt
	 * till ett befintligt JMenu-objekt.
	 * parentName är namnet på den JMenu som sub-menyn ska
	 * tillhöra.
	 */
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

	/*
	 * Ganska självförklarande. Läs dokumentationen över JMenuBar
	 * för att hitta lämplig metod
	 */
	public JMenu getJMenu(int index) {
		return this.getMenu(index);
	}


	/*
	 * Denna metod kommer returnera en komponent som har lagts till
	 * i våran Menu-klass, oavsett om komponenten är en JMenu eller ett JMenuItem,
	 * Vi är alltså ute efter komponenter utifrån "namn".
	 * Med namn så menar vi i det här fallet vad JMenu- och JMenuItem-objekt
	 * heter i vårat GUI, såsom "File", "Load...", "Drawing" osv.
	 * En sak som kan vara bra att veta är att JMenu faktiskt ärver från JMenuItem.
	 * Så även fast JMenuItem är något som ligger inuti en JMenu, såsom våra kodexempel 
	 * visar, så är JMenu en mer specificerad klass än JMenuItem.
	 */
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
		/*
		 * 1. JMenuBar ärver från 
		 * javax.swing.JComponent, som ärver från
		 * java.awt.Container, som ärver från
		 * java.awt.Component, som ärver från
		 * java.lang.Object.
		 * 
		 * ¯\_(ツ)_/¯
		 *  
		 * Tack vare java.awt.Container så ärver JMenuBar metoden getComponents().
		 * getComponents() returnerar en array med samtliga barn-komponenter (Component).
		 * Använd en for-each loop för att loopa över våran Menu-klass samtliga barn-komponenter.
		 */

		/*
		 * 2. Vi är egentligen säkra på att alla "närmaste" barn-komponenter till våran Menu-klass
		 * kommer vara JMenu-objekt, så vi kan tryggt "casta" varje objekt som loopas igenom
		 * till ett JMenu-objekt. (egentligen kan vi casta till JMenuItem också. Hur kommer det sig tror du?)
		 */
		
		/*
		 * 3. Varför vill vi casta till ett JMenu-objekt? Jo, för att kunna anropa metoden
		 * getText() och jämföra med parametern "name". Om så är fallet,
		 * då har vi hittar våran komponent, och kan returnera den. Kom ihåg att INTE använda
		 * likhetsoperatorn (==) när du kollar efter att texten är densamma.
		 */

		/*
		 * 4. Ok, med den kod som skrivits fram till nu så kan vi hitta alla JMenu-objekt som är
		 * addade direkt till våran JMenuBar. Men hur gör vi för att nå JMenu-objekt som är addade
		 * till andra JMenu-objekt, såsom fallet med att Drawing är tillagd till Edit?
		 * Well, JMenu-klassen har en metod som heter getMenuComponents(), 
		 * så vi kan på precis samma sätt som innan köra en for-each loop. Denna loop ska
		 * ske inuti den befintliga loopen.
		 */

		/*
		 * 5. I det här fallet kan vi inte casta allt som passerar loopen som JMenu.
		 * Just nu har denna klass ingen nytta av att komma åt JMenuItems, men det
		 * är ändå inte så snyggt att begränsa en metod med namnet "getComponentByName"
		 * att bara returnera JMenu-objekt.
		 * Men en till orsak är att JMenu är en subklass till JMenuItem, så att försöka casta
		 * ett JMenuItem till en JMenu kommer leda till att ett Exception kastas.
		 * Och sen har vi en JSeparator i en av menyerna, och den kan vi inte heller
		 * casta till vare sig JMenuItem eller JMenu.
		 * 
		 * Vi behöver använda oss av "instanceof"-operatorn. 
		 * Eftersom JMenu ärver från JMenuItem, och eftersom getText(), vilket är den metod vi behöver, 
		 * är en metod som tillhör en föräldraklassen till JMenuItem (javax.swing.AbstractButton), 
		 * så räcker det med att vi kollar om det rör sig om ett JMenuItem.
		 */
		
		/*
		 * 6. Om det objekt som passerar genom loopen är ett JMenuItem, så är det bara
		 * att casta objektet och returnera det om den har samma text som parametern. 
		 */

		/*
		 * 7. Om inga av dessa vilkor har kunna uppfyllas, då måste metoden returnera null.
		 */
		
		// T O D O ...eller vänta, den bjuder jag på ;-)
	}

}
