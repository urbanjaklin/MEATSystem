package meat.mainsystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Dimension;

import java.util.Iterator;

/**
 * 
 * @author CSCE 740 Group 1 - Jessica Anderson, Natasha Delahunt, Urban Jaklin
 */
public class MainFrame extends JFrame implements ActionListener {

	/**
	 * @serial http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html
	 *         
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane = new JPanel();

	private SelectionPanel select;
	private ViewSelectPanel viewSelect;
	private EventSelectPanel eventSelect;
	private EventSchedulePanel eventMeeting;
	private EventSchedulePanel eventVacation;
	private EventSchedulePanel eventHoliday;
	private ViewEventsPanel viewAllEvents;
	private ViewEventsPanel viewRoomEvents;
	private ViewEventsPanel viewEmpEvents;
	private JScrollPane scroll;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setSize(1000,500);
					frame.setVisible(true);				
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
		setTitle("M.E.A.T.");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setBackground(new Color(204, 255, 153));
		

		// contentPane
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(1000,500));
		setContentPane(contentPane);

		select = new SelectionPanel(this);
		select.setBounds(0, 0, 800, 600);
		contentPane.add(select);

		viewSelect = new ViewSelectPanel(this);
		//viewSelect.setLayout(contentPane.getLayout());
		viewSelect.setBounds(0, 0, 800, 600);

		eventSelect = new EventSelectPanel(this);
		eventSelect.setBounds(0, 0, 800, 600);

		eventMeeting = new EventSchedulePanel(this, "meeting");
		eventMeeting.setBounds(0, 0, 800, 600);

		eventVacation = new EventSchedulePanel(this, "vacation");
		eventVacation.setBounds(0, 0, 800, 600);

		eventHoliday = new EventSchedulePanel(this, "holiday");
		eventHoliday.setBounds(0, 0, 800, 600);
	}
	
	public void actionPerformed(ActionEvent e) {
		if ("Schedule An Event".equals(e.getActionCommand())) {
//			System.out.println("Schedule");
			contentPane.remove(select);
			contentPane.add(eventSelect);
			contentPane.repaint();
		}
		if ("Back".equals(e.getActionCommand())) {
			String buttonSource = ((JButton) e.getSource()).getName();
			switch (buttonSource) {
			case "fromEventSelectPanel":
//				System.out.println("Back from Event Select");
				contentPane.remove(eventSelect);
				contentPane.add(select);
				break;
			case "fromViewSelectPanel":
//				System.out.println("Back from View Select");
				contentPane.remove(viewSelect);
				contentPane.add(select);
				break;
			case "fromEventSchedulePanel":
//				System.out.println("Back from Event Schedule");
				// Clear fields
				eventMeeting.textAreaDescription.setText("");
				eventMeeting.textParticipants.setText("");
				eventMeeting.textRoom.setText("");
				contentPane.remove(eventMeeting);
				// Clear fields
				eventVacation.textAreaDescription.setText("");
				eventVacation.textParticipants.setText("");
				eventVacation.textRoom.setText("");
				contentPane.remove(eventVacation);
				// Clear fields
				eventHoliday.textAreaDescription.setText("");
				eventHoliday.textParticipants.setText("");
				eventHoliday.textRoom.setText("");
				contentPane.remove(eventHoliday);
				contentPane.add(select);		
				break;
			case "fromViewEmployee":
//				System.out.println("Back from View Events by Employee");
//				contentPane.remove(viewEmpEvents);
				contentPane.remove(scroll);
				contentPane.add(viewSelect);
				break;
			case "fromViewRoom":
//				System.out.println("Back from View Events by Room");
				contentPane.remove(viewRoomEvents);
				contentPane.add(viewSelect);
				break;
			case "fromViewAll":
//				System.out.println("Back from View All Events");
				contentPane.remove(viewAllEvents);
				contentPane.add(viewSelect);
				break;
			}
			contentPane.repaint();
		} else if ("Schedule A Meeting".equals(e.getActionCommand())) {
//			System.out.println("Schedule Meeting");
			contentPane.remove(eventSelect);
			contentPane.add(eventMeeting);
			contentPane.repaint();
		} else if ("Schedule Vacation Time".equals(e.getActionCommand())) {
//			System.out.println("Schedule Vacation");
			contentPane.remove(eventSelect);
			contentPane.add(eventVacation);
			contentPane.repaint();
		} else if ("Schedule A Holiday".equals(e.getActionCommand())) {
//			System.out.println("Schedule Holiday");
			contentPane.remove(eventSelect);
			contentPane.add(eventHoliday);
			contentPane.repaint();
		} else if ("Cancel".equals(e.getActionCommand())) {
//			System.out.println("Cancel Event");
			// Clear fields
			eventMeeting.textAreaDescription.setText("");
			eventMeeting.participantList.setListData(new Object[0]);
			eventMeeting.textRoom.setText("");
			// Clear fields
			eventVacation.textAreaDescription.setText("");
			eventVacation.participantList.setListData(new Object[0]);
			eventVacation.textRoom.setText("");
			// Clear fields
			eventHoliday.textAreaDescription.setText("");
			eventHoliday.textRoom.setText("");
			
			contentPane.remove(eventMeeting);
			contentPane.remove(eventVacation);
			contentPane.remove(eventHoliday);
			contentPane.add(eventSelect);
			contentPane.repaint();
		} else if ("View Schedules".equals(e.getActionCommand())) {
//			System.out.println("View");
			contentPane.remove(select);
			contentPane.add(viewSelect);
			contentPane.repaint();
		} else if ("View By Employee".equals(e.getActionCommand())) {
			viewEmpEvents = new ViewEventsPanel(this, "byEmployee");
			viewEmpEvents.setBounds(0, 0, 450, 300);
			viewEmpEvents.setPreferredSize(new Dimension(450, 500));
			scroll = new JScrollPane(viewEmpEvents);
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.setBounds(0, 0, 450, 300);
//			System.out.println("Employee View");
			contentPane.remove(viewSelect);
//			contentPane.add(viewEmpEvents);
			contentPane.add(scroll);
			contentPane.repaint();
//			pack();
		} else if ("View By Room".equals(e.getActionCommand())) {
			viewRoomEvents = new ViewEventsPanel(this, "byRoom");
			viewRoomEvents.setBounds(0, 0, 450, 300);
//			System.out.println("Room View");
			contentPane.remove(viewSelect);
			contentPane.add(viewRoomEvents);
			contentPane.repaint();
		} else if ("View All".equals(e.getActionCommand())) {
			viewAllEvents = new ViewEventsPanel(this, "all");
			viewAllEvents.setBounds(0, 0, 450, 300);
//			System.out.println("All View");
			contentPane.remove(viewSelect);
			contentPane.add(viewAllEvents);
			contentPane.repaint();
		}
		contentPane.repaint();
	}

}
