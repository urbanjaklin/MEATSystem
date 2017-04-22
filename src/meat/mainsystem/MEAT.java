package meat.mainsystem;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author CSCE 740 Group 1 - Jessica Anderson, Natasha Delahunt, Urban Jaklin
 */
public class MEAT {

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Creates an eventFactory
		EventFactory eventFactory = new EventFactory();
		HandleJSON temp = new HandleJSON();
		
		if(args.length == 0) {
			String[] empArray = {};
	
			MainFrame main = new MainFrame();
			main.setVisible(true);
	
//			temp.getEventsByEmployee("urban");
//			temp.getEventsByRoom("3D15");
		} else {
			String file = args[0];
			temp.executeScript(file);
		}
	}

}
