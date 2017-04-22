package meat.mainsystem;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * @author CSCE 740 Group 1 - Jessica Anderson, Natasha Delahunt, Urban Jaklin
 *
 */
public class ViewEventsPanel extends JPanel {
	
	private HandleJSON jsonHandler = new HandleJSON();
//	public Event[] meetingEvents;
//	public Event[] vacationEvents;
//	public Event[] holidayEvents;

	private MainFrame mainFrame;
	private String viewType;
	

	/**
	 * @serial <a href=
	 *         "http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html">
	 *         Default Serials</a>
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Event[][] selectedEvents = new Event[4][0];

	// Create Buttons
	JButton btnBack = new JButton("Back");

	/**
	 * Create the panel.
	 */
	public ViewEventsPanel(MainFrame parent, String viewType) {
		this.mainFrame = parent;
		this.viewType = viewType;
		setLayout(null);
		setupPanel();
	}
	
	public void setupPanel() {
		//This currently doesn't work properly anyway, so commenting:
//		selectedEvents = jsonHandler.parseEvents(jsonHandler.getAllEvents());
//		System.out.println(Arrays.toString(selectedEvents[0]));
//		meetingEvents = selectedEvents[0];
//		vacationEvents = selectedEvents[1];
//		holidayEvents = selectedEvents[2];
		
		JComboBox employeeComboBox = new JComboBox(jsonHandler.retrieveEmployees());
		employeeComboBox.setBounds(161, 11, 163, 23);
		
		// Add ActionListener to listen for change in employee combo box selection
		ActionListener employeeActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Get item selected from employee combo box
                String employee = (String) employeeComboBox.getSelectedItem();
              //This currently doesn't work properly anyway, so commenting:
//                selectedEvents = jsonHandler.parseEvents(jsonHandler.getEventsByEmployee(employee));    
                revalidate();
                repaint();
            }
        };
        
        employeeComboBox.addActionListener(employeeActionListener);
		
		JComboBox roomComboBox = new JComboBox(jsonHandler.retrieveRooms());
		roomComboBox.setBounds(161, 11, 163, 23);
		
		// Add ActionListener to listen for change in room combo box selection
		ActionListener roomActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Get item selected from employee combo box
                String employee = (String) employeeComboBox.getSelectedItem();
                //This currently doesn't work properly anyway, so commenting:
//                selectedEvents = jsonHandler.parseEvents(jsonHandler.getEventsByEmployee(employee));
                revalidate();
                repaint();
            }
        };
        
        // These if-statements check the type of view you are performing
        // For each type, it sets a name for the back button and populates
        // the JPanel with the appropriate JLists
		if("byEmployee".equals(viewType)) {
			btnBack.setName("fromViewEmployee");
			add(employeeComboBox);
//			add(meetingList);
//			add(vacationList);
//			add(holidayList);
		}
		else if("byRoom".equals(viewType)) {
			btnBack.setName("fromViewRoom");
			add(roomComboBox);
//			add(meetingList);
		}
		else if("all".equals(viewType)) {
			btnBack.setName("fromViewAll");
//			add(meetingList);
//			add(vacationList);
//			add(holidayList);
		}
		else if("company".equals(viewType)) {
//			add(holidayList);
		}
		
		// Set location and size for "Back" button
		btnBack.setBounds(10, 11, 89, 23);
		// Add action listener to enable MainFrame to access its click action
		btnBack.addActionListener(mainFrame);
		// Add button to screen
		add(btnBack);
		JPanel meetingsPanel = new JPanel();
		meetingsPanel.setBounds(20, 45, 407, 100);
		add(meetingsPanel);
		meetingsPanel.setLayout(null);
//		for (int i = 0; i < meetingEvents.length; i++) {
//			JLabel lblNewLabel = new JLabel(meetingEvents[i].getEventID() + " " + meetingEvents[i].getEventDescription() + " " + meetingEvents[i].getStartTime().toString());
//			lblNewLabel.setBounds(0, 0 + (i*25), 308, 14);
//			meetingsPanel.add(lblNewLabel);
//			
//			JButton btnEdit = new JButton("Edit");
//			btnEdit.setBounds(318, -4 + (i*25), 89, 23);
//			meetingsPanel.add(btnEdit);
//		}
		
//		JPanel panel_1 = new JPanel();
//		panel_1.setBounds(30, 156, 397, 102);
//		add(panel_1);
//		panel_1.setLayout(null);
//		
//		JLabel lblNewLabel_1 = new JLabel("New label");
//		lblNewLabel_1.setBounds(0, 0, 46, 14);
//		panel_1.add(lblNewLabel_1);
//		
//		JLabel lblNewLabel_2 = new JLabel("New label");
//		lblNewLabel_2.setBounds(0, 25, 46, 14);
//		panel_1.add(lblNewLabel_2);
//		
//		JButton btnNewButton = new JButton("New button");
//		btnNewButton.setBounds(56, -4, 89, 23);
//		panel_1.add(btnNewButton);
//		
//		JButton btnNewButton_1 = new JButton("New button");
//		btnNewButton_1.setBounds(56, 21, 89, 23);
//		panel_1.add(btnNewButton_1);
	}
}
