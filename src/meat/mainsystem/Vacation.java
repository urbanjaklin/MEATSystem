package meat.mainsystem;

import java.util.Date;

/**
 * @author CSCE 740 Group 1 - Jessica Anderson, Natasha Delahunt, Urban Jaklin
 *
 */
public class Vacation extends Event {

	public int eventID;
	public String eventDescription;
	public Date startTime;
	public Date endTime;
	public String[] empArray;

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String[] getEmpArray() {
		return empArray;
	}

	public void setEmpArray(String[] empArray) {
		this.empArray = empArray;
	}

	/**
	 * @param empArray
	 */
	public Vacation(int eventID, String eventDescription, Date startTime, Date endTime, String[] empArray) {
		HandleJSON temp = new HandleJSON();
		if (eventID == 2) {
			long count = temp.getVacationCount();
			eventID = (int) count + 1;
		}
		this.eventID = eventID;
		this.eventDescription = eventDescription;
		this.startTime = startTime;
		this.endTime = endTime;
		if (empArray.length > 1) {
			System.out.println("Error: Cannot enter more than one participant for vacation"
					+ "\nUsing first entry in array for event creation.");
			String[] newArray = { empArray[0] };
			empArray = newArray;
		}
		this.empArray = empArray;
	}

	public String toString() {
		String startTime = this.startTime.toString();
		String endTime = this.endTime.toString();
		return "Vacation with ID: " + eventID + "\nStart Time: " + startTime + "\nEnd Time: " + endTime
				+ "\nParticipants: " + empArray;
	}
}
