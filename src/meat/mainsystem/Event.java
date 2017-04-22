package meat.mainsystem;

import java.util.Date;
/**
 * 
 * @author CSCE 740 Group 1 - Jessica Anderson, Natasha Delahunt, Urban Jaklin
 */


/**
 * The <code>Event</code> class manages the different types of events that can
 * be created with the MEAT system. Its methods allow you to retrieve parameters
 * from Event objects and presents a standardization of information which can be
 * made available to each type of event.
 * 
 * @author CSCE 740 Group 1 - Jessica Anderson, Natasha Delahunt, Urban Jaklin
 *
 */

public class Event {

	public int eventID;
	public String eventDescription;
	public Date startTime;
	public Date endTime;
	public String[] empArray;

	/**
	 * @return the event ID for the Event object
	 */
	public int getEventID() {
		return eventID;
	}

	/**
	 * @param eventID
	 *            a unique ID for the event
	 */
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	/**
	 * @return the event description
	 */
	public String getEventDescription() {
		return eventDescription;
	}

	/**
	 * @param eventDescription
	 *            a description of the event (optional)
	 */
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	/**
	 * @return the start time (including the date) for the event
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the start time to set for the event
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the end time (including the date) for the event
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the end time to set for the event
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return an array which indicates the participants in the event
	 */
	public String[] getEmpArray() {
		return empArray;
	}

	/**
	 * @param empArray
	 *            the array of participants to set for the event
	 */
	public void setEmpArray(String[] empArray) {
		this.empArray = empArray;
	}

	/**
	 * Primary, most useful constructor. (For invocation by subclass
	 * constructors.)
	 * 
	 * @param eventID
	 *            a unique ID for the event
	 * @param eventDescription
	 *            a description of the event
	 * @param startTime
	 *            the start time to set for the event
	 * @param endTime
	 *            the end time to set for the event
	 * @param empArray
	 *            the array of participants to set for the event
	 */
	public Event(int eventID, String eventDescription, Date startTime, Date endTime, String[] empArray) {
		this.eventID = eventID;
		this.eventDescription = eventDescription;
		this.startTime = startTime;
		this.endTime = endTime;
		this.empArray = empArray;
	}

	/**
	 * 
	 */
	public Event() {

	}

}
