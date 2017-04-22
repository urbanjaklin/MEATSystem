package meat.mainsystem;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;

/**
 * @author CSCE 740 Group 1 - Jessica Anderson, Natasha Delahunt, Urban Jaklin
 *
 */
public class ViewSelectPanel extends JPanel {

	/**
	 * @serial <a href="http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html">Default Serials</a>
	 *         
	 */
	private static final long serialVersionUID = 1L;

	// Create Buttons
	JButton btnEmployee = new JButton("View By Employee");
	JButton btnRoom = new JButton("View By Room");
	JButton btnAll = new JButton("View All");
	JButton btnBack = new JButton("Back");
	
	// Declare MainFrame element as ActionListener
	MainFrame mainFrame;

	/**
	 * Create the panel.
	 */
	public ViewSelectPanel(MainFrame parent) {
		this.mainFrame = parent;
		setBounds(0, 0, 450, 300);
		setUpPanel();
//		setLayout(new BorderLayout());
		//setLayout(getLayout());
		//parent.setLayout(getLayout());

		
		
		
		

	}

	/**
	 * Create the panel
	 */
	private void setUpPanel() {
		setBackground(new Color(204, 255, 153));
		setLayout(null);
		btnEmployee.setBounds(146, 76, 161, 23);
		add(btnEmployee);
		btnRoom.setBounds(146, 110, 161, 23);
		add(btnRoom);
		btnAll.setBounds(146, 144, 161, 23);
		add(btnAll);
		btnBack.setBounds(10, 11, 89, 23);
		btnBack.setName("fromViewSelectPanel");
		add(btnBack);
		// Add ActionListeners
		btnEmployee.addActionListener(mainFrame);
		btnRoom.addActionListener(mainFrame);
		btnAll.addActionListener(mainFrame);
		btnBack.addActionListener(mainFrame);

	}

}
