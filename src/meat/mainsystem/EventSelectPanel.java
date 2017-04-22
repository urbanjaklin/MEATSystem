package meat.mainsystem;

import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JButton;

/**
 * @author CSCE 740 Group 1 - Jessica Anderson, Natasha Delahunt, Urban Jaklin
 *
 */
public class EventSelectPanel extends JPanel {

	/**
	 * @serial http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.
	 *         html
	 */
	private static final long serialVersionUID = 1L;
	// Create Buttons
	JButton btnScheduleAMeeting = new JButton("Schedule A Meeting");
	JButton btnScheduleVacationTime = new JButton("Schedule Vacation Time");
	JButton btnScheduleAHoliday = new JButton("Schedule A Holiday");
	JButton btnBack = new JButton("Back");
	
	MainFrame mainFrame;

	/**
	 * Create the panel.
	 */
	public EventSelectPanel(MainFrame parent) {
		this.mainFrame = parent;
		setUpPanel();
		setOpaque(true);
		setLayout(null);
	}

	private void setUpPanel() {
//		setBackground(new Color(204, 255, 153));
		btnScheduleAMeeting.setBounds(149, 88, 204, 23);
		add(btnScheduleAMeeting);
		btnScheduleVacationTime.setBounds(149, 122, 204, 23);
		add(btnScheduleVacationTime);
		btnScheduleAHoliday.setBounds(149, 156, 204, 23);
		add(btnScheduleAHoliday);
		btnBack.setBounds(10, 11, 89, 23);
		btnBack.setName("fromEventSelectPanel");
		add(btnBack);
		
		// Add ActionListeners
		btnScheduleAMeeting.addActionListener(mainFrame);
		btnScheduleVacationTime.addActionListener(mainFrame);
		btnScheduleAHoliday.addActionListener(mainFrame);
		btnBack.addActionListener(mainFrame);

	}

}
