package meat.mainsystem;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JList;

/**
 * 
 * @author CSCE 740 Group 1 - Jessica Anderson, Natasha Delahunt, Urban Jaklin
 */
public class EventSchedulePanel extends JPanel implements ActionListener {
	/**
	 * @serial <a href=
	 *         "http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html">
	 *         Default Serials</a>
	 * 
	 */
	// Variable Declarations
	private HandleJSON jsonHandler = new HandleJSON();
	private static final long serialVersionUID = 1L;
	// Strings
	public String eventType;
	private String getText = "";
	// arrays
	private String[] textArray;
	private String[] empListArray;
	private String[] participantArray = {};
	private String[] removalArray;
	// Lists
	private List<String> transferEmps = new ArrayList<String>();
	private List<String> removeParticipants = new ArrayList<String>();
	ArrayList<String> transferArrayList = new ArrayList<String>();
	// List Models for looking into JLists
	ListModel<Class<?>> empModel;
	ListModel<Class<?>> partModel;

	// TextAreas
	JTextArea textAreaDescription = new JTextArea();

	// Spinners
	private JSpinner spinnerStartTime = new JSpinner(new SpinnerDateModel());
	private JSpinner spinnerEndTime = new JSpinner(new SpinnerDateModel());

	// TextFields
	JTextField textParticipants = new JTextField();
	JTextField textRoom = new JTextField();
	JTextField addOthersTextField = new JTextField();

	// Labels
	JLabel lblDescription = new JLabel("Description");
	JLabel lblEndTime = new JLabel("End Time");
	JLabel lblStartTime = new JLabel("Start Time");
	JLabel lblParticipants = new JLabel("Participants");
	JLabel lblRoom = new JLabel("Room");
	JLabel lblEmployees = new JLabel("Employees");
	JLabel lblNonEmployees = new JLabel("Non-Employees");

	// Buttons
	JButton btnSubmit = new JButton("Submit");
	JButton btnCancel = new JButton("Cancel");
	JButton btnBack = new JButton("Back");
	JButton btnAddEmployees = new JButton("Add Employee(s) >>");
	JButton btnAddNonemployees = new JButton("Add NonEmployee(s) >>");
	JButton btnRemoveParticipants = new JButton("Remove Participants <<");

	// ComboBoxes
	JComboBox comboBoxRooms = new JComboBox();

	// JLists
	private JList employeeList = new JList();
	public JList participantList = new JList();

	// Array for employees
	private final String employeeNames[] = jsonHandler.retrieveEmployees();

	// Array for rooms
	private final String rooms[] = jsonHandler.retrieveRooms();

	// ScrollPanes
	private final JScrollPane scrollPaneEmployees = new JScrollPane();
	private final JScrollPane scrollPaneParticipants = new JScrollPane();

	MainFrame mainFrame;

	/**
	 * Create the panel.
	 */
	public EventSchedulePanel(MainFrame parent, String eventType) {
		this.mainFrame = parent;
		this.eventType = eventType;
		setLayout(null);
		setOpaque(true);
		setupPanel();
	}

	public void setupPanel() {
		// Create Labels
		lblDescription.setBounds(22, 50, 81, 14);
		add(lblDescription);
		lblStartTime.setBounds(22, 89, 64, 14);
		add(lblStartTime);
		lblEndTime.setBounds(351, 89, 64, 14);
		add(lblEndTime);
		lblParticipants.setBounds(414, 145, 81, 14);
		lblRoom.setBounds(22, 120, 49, 14);
		lblEmployees.setBounds(22, 145, 81, 14);
		lblNonEmployees.setBounds(220, 201, 162, 14);

		// Create Text Area
		textAreaDescription.setLineWrap(true);
		textAreaDescription.setBounds(113, 45, 479, 33);
		add(textAreaDescription);

		// Create DateTime Spinners
		spinnerStartTime.setBounds(113, 86, 152, 20);
		add(spinnerStartTime);
		spinnerEndTime.setBounds(438, 86, 154, 20);
		add(spinnerEndTime);

		// Create TextFields
		textParticipants.setColumns(10);
		textParticipants.setBounds(167, 163, 191, 20);
		addOthersTextField.setToolTipText("Add Name or Names Separated by Commas and click Add NonEmployees");
		addOthersTextField.setBounds(220, 216, 162, 20);
		addOthersTextField.setColumns(10);

		// Set up ComboBoxes
		comboBoxRooms = new JComboBox(rooms);
		comboBoxRooms.setBounds(113, 117, 152, 20);

		// comboBoxRooms.set

		// Set up ScrollPanes
		scrollPaneEmployees.setBounds(22, 159, 173, 112);
		scrollPaneParticipants.setBounds(413, 159, 179, 112);

		// Set up JLists
		participantList = new JList(participantArray);
		scrollPaneParticipants.setViewportView(participantList);
		participantList.setVisibleRowCount(10);
		participantList.setFixedCellWidth(100);
		participantList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		participantList.setListData(participantArray);

		employeeList = new JList(employeeNames);
		scrollPaneEmployees.setViewportView(employeeList);
		employeeList.setVisibleRowCount(10);
		employeeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		employeeList.setListData(employeeNames);

		// Set Up Buttons
		btnSubmit.setBounds(151, 337, 89, 23);
		btnSubmit.addActionListener(this);
		add(btnSubmit);
		btnCancel.setBounds(268, 337, 89, 23);
		btnCancel.addActionListener(mainFrame);
		add(btnCancel);
		btnAddEmployees.setBounds(220, 157, 161, 23);
		btnAddEmployees.addActionListener(new ActionListener() {// anonymous
																// inner class
			// handle button event
			public void actionPerformed(ActionEvent event) {
				// place selected values in participantList
				transferEmps = employeeList.getSelectedValuesList(); // add the
																		// selected
																		// values
																		// to
																		// the
																		// List
																		// transferEmps
				// take the List transferEmps and put it in an array
				empListArray = transferEmps.toArray(new String[transferEmps.size()]);

				/*
				 * // add current participants to transferArrayList for (int r =
				 * 0; r < participantArray.length; r++) {
				 * transferArrayList.add(participantArray[r]); }
				 */

				// add elements from the empListArray to transferArrayList
				for (int m = 0; m < empListArray.length; m++) {
					transferArrayList.add(empListArray[m]);
				}

				// create "new" participantArray with the new members
				participantArray = transferArrayList.toArray(new String[transferArrayList.size()]);
				participantList.setListData(participantArray);
			}
		} // end anonymous inner class
		); // end call to addActionListener

		btnAddNonemployees.setBounds(219, 244, 160, 23);
		btnAddNonemployees.addActionListener(new ActionListener() {// anonymous
																	// inner
																	// class
			// handle button event
			public void actionPerformed(ActionEvent event) {
				// place selected values in participantList
				getText = addOthersTextField.getText();
				textArray = getText.split(",");

				/*
				 * // Take the names already in the participantArray and add
				 * them // to the transferArrayList for (int r = 0; r <
				 * participantArray.length; r++) {
				 * transferArrayList.add(participantArray[r]); }
				 */

				// add the names from the textbox (now in textArray) to
				// transferArrayList
				for (int m = 0; m < textArray.length; m++) {
					transferArrayList.add(textArray[m]);
				}
				// create "new" participantArray with the new members
				participantArray = transferArrayList.toArray(new String[transferArrayList.size()]);
				participantList.setListData(participantArray);
			}
		} // end anonymous inner class
		); // end call to addActionListener

		btnRemoveParticipants.setBounds(414, 282, 161, 23);
		btnRemoveParticipants.addActionListener(new ActionListener() {// anonymous inner class
			// handle button event
			public void actionPerformed(ActionEvent event) {
				// place selected values in removeParticipants List
				removeParticipants = participantList.getSelectedValuesList(); 
				// take the List removeParticipants and put it in an array
				removalArray = removeParticipants.toArray(new String[removeParticipants.size()]);
				// Take away participants listed in removalArray from the particpantArray
				for (int i = removalArray.length - 1; i >= 0; i--) {
					System.out.println("i = " + i);
					for (int j = 0; j < participantArray.length; j++) {
						System.out.println("j = " + j);
						System.out.println("item in removalArray " + removalArray[i].toString());
						System.out.println("item in participantArray " + participantArray[j].toString());
						if (removalArray[i].toString() == participantArray[j].toString()) {
							// transferArrayList.remove(j);
							transferArrayList.remove(removalArray[i]);
							System.out.println("REMOVED " + removalArray[i]);
						}
					}
				}

				// create "new" participantArray with the new members
				participantArray = transferArrayList.toArray(new String[transferArrayList.size()]);
				participantList.setListData(participantArray);
			}
		} // end anonymous inner class
		); // end call to addActionListener

		if ("meeting".equals(eventType)) {
			add(lblParticipants);
			add(lblRoom);
			add(scrollPaneEmployees);
			add(scrollPaneParticipants);
			add(addOthersTextField);
			add(btnAddEmployees);
			add(btnAddNonemployees);
			add(lblEmployees);
			add(lblNonEmployees);
			add(btnRemoveParticipants);
			add(comboBoxRooms);
		} else if ("vacation".equals(eventType)) {
			add(lblParticipants);
			add(scrollPaneEmployees);
			add(scrollPaneParticipants);
			add(btnAddEmployees);
			add(lblEmployees);
			add(btnRemoveParticipants);
		} else if ("holiday".equals(eventType)) {

		}

		mainFrame.repaint();
	}

	public void actionPerformed(ActionEvent e) {
		String eventDescription = textAreaDescription.getText();
		Date startTime = (Date) spinnerStartTime.getValue();
		Date endTime = (Date) spinnerEndTime.getValue();
		List<String> empArrayList = new ArrayList<String>();
		for (int i = 0; i < participantArray.length; i++) {
			String participant = participantArray[i];
			String[] partSplit = participant.split(" ", -1);
			if(partSplit.length == 3) {
				empArrayList.add(partSplit[2]);
			} else {
				empArrayList.add(participant);
			}
		}
		String[] empArray = new String[empArrayList.size()];
		empArray = empArrayList.toArray(empArray);

		//String eventRoom = textRoom.getText();
		String[] eventRoomSplit = comboBoxRooms.getSelectedItem().toString().split(" ", -1);
		String eventRoom = eventRoomSplit[1];

		this.submitEvent(eventDescription, startTime, endTime, empArray, eventRoom);
	}

	public void submitEvent(String eventDescription, Date startTime, Date endTime, String[] empArray,
			String eventRoom) {

		EventFactory eventFactory = new EventFactory();
		if ("meeting".equals(eventType)) {
			eventFactory.createEvent(1, eventDescription, startTime, endTime, empArray, eventRoom);
		} else if ("vacation".equals(eventType)) {
			eventFactory.createEvent(2, eventDescription, startTime, endTime, empArray, null);
		} else if ("holiday".equals(eventType)) {
			String[] company = { "Company" };
			eventFactory.createEvent(3, eventDescription, startTime, endTime, company, null);
		}

		// Clear fields
		textAreaDescription.setText("");
		textParticipants.setText("");
		textRoom.setText("");
		participantList.setListData(new Object[0]);
	}
}