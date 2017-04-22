package meat.mainsystem;

import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JButton;

/**
 * @author CSCE 740 Group 1 - Jessica Anderson, Natasha Delahunt, Urban Jaklin
 *
 */
public class SelectionPanel extends JPanel {

	/**
	 * @serial http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html
	 *         
	 */
	private static final long serialVersionUID = 1L;

	// Create Buttons
	JButton btnSchedule = new JButton("Schedule An Event");
	JButton btnView = new JButton("View Schedules");
	MainFrame mainFrame;

	/**
	 * Create the panel.
	 */
	public SelectionPanel(MainFrame parent) {
		this.mainFrame = parent;
		setUpPanel();
		setLayout(null);
	}

	private void setUpPanel() {
		setBackground(new Color(204, 255, 153));
		btnSchedule.setBounds(146, 146, 146, 23);
		add(btnSchedule);
		btnView.setBounds(146, 180, 146, 23);
		add(btnView);
		
		// Add ActionListeners
		btnSchedule.addActionListener(mainFrame);
		btnView.addActionListener(mainFrame);
	}

}
