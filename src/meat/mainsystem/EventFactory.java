package meat.mainsystem;

import java.util.Date;

/**
 * This factory class will handle the maintenance of events as they are used by
 * the system. In other words, events which are to be created, read, updated, or
 * deleted by the system must pass through the <code>EventFactory</code>.
 * 
 * @author CSCE 740 Group 1 - Jessica Anderson, Natasha Delahunt, Urban Jaklin
 */
public class EventFactory {

	/**
	 * This <code>createEvent</code> method will perform the tasks of generating
	 * and storing an event. The <code>Event</code> subclasses (Meeting,
	 * Vacation, and Holiday) will handle the initial creation of the event,
	 * then implementation of the <code>HandleJSON</code> class will allow for
	 * storage of the event in an external .txt file.
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
	 * @param eventRoom
	 *            the room in which the event occurs
	 * 
	 * @return the <code>Event</code> class object which holds all the event's
	 *         information
	 */
	public Event createEvent(int eventID, String eventDescription, Date startTime, Date endTime, String[] empArray,
			String eventRoom) {
		// Initializing a HANDLEJson object which will be in control of managing
		// the text file which stores our newly created event
		HandleJSON test = new HandleJSON();

		// *!! NOTE TO FELLOW DEVS: We may be able to move this date validity
		// check
		// to a different class! Thoughts? !!*
		// Checking the validity of the start and end times entered (to ensure
		// that the start time is before the end time)
		if (!checkDateValid(startTime, endTime)) {
			System.err.println("Error: Start Time is not before End Time");
			return null;
		}

		// Take the eventID and get the first number.
		int eventIDStartsWith = Integer.parseInt(Integer.toString(eventID).substring(0, 1));

		// Use the first number of the eventID (eventIDStartsWith) to determine
		// type of event to be created, then prompt HandleJSON object to add
		// the event to the storage document
		if (eventIDStartsWith == 1) {
			Meeting meeting = new Meeting(eventID, eventDescription, startTime, endTime, empArray, eventRoom);
			test.addMeeting(meeting);
			return meeting;
		} else if (eventIDStartsWith == 2) {
			Vacation vacation = new Vacation(eventID, eventDescription, startTime, endTime, empArray);
			test.addVacation(vacation);
			return vacation;
		} else if (eventIDStartsWith == 3) {
			Holiday holiday = new Holiday(eventID, eventDescription, startTime, endTime, empArray);
			test.addHoliday(holiday);
			return holiday;
		} else {
			return null;
		}

	}

	/**
	 * The <code>checkDateValid</code> method simply ensures that the start date
	 * is before the end date
	 * 
	 * @param start
	 *            a start time (and date) for the event
	 * @param end
	 *            an end time (and date) for the event
	 * @return true if start time is before end time, false otherwise
	 */
	public boolean checkDateValid(Date start, Date end) {
		if (start.before(end)) {
			return true;
		} else {
			return false;
		}
	}

}
