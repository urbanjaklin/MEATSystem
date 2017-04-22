package meat.mainsystem;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * The <code>HandleJSON</code> class handles the creation, deletion, and
 * modification of events as they appear in the storage document. The storage
 * document will display an array of events as JSON objects, along with
 * key-value pairs which store the counters which determine event IDs.
 * <p>
 * The storage document is saved under the name <code>events.json</code> in the
 * parent directory of the source code files. The data will be stored as one
 * JSON object with the following structure:
 * </p>
 * <code>{ "MeetingCount" : 10000000, "VacationCount" : 20000000, "HolidayCount" :
 * 30000000, "Events" : [ { A JSON object with event information}, {Another JSON object}] }
 * </code>
 * <p>
 * The "Events" JSON array (seen above) which stores all the events saved by the
 * MEAT system will contain objects having the following structure:
 * </p>
 * <code>{ "id" : 10000000, "desc" : "a description", "start" : "Date.toString output",
 * "end" : "Date.toString output", "room" : "testRoom", "emp" : [ "urban", "natasha",
 * "jessica" ] }</code>
 * <p>
 * <b>Note</b>: The JSON objects referred to above may not appear with its
 * key-value pairs in exactly the same order or with those exact values. This
 * serves only as a template for what one should expect to see within the
 * storage document.
 * </p>
 * 
 * 
 * http://www.mkyong.com/java/java-read-a-file-from-resources-folder/
 * 
 * <p>Credit to App Shah of Crunchify for providing code to aid in writing JSON objects
 * to a file. Sources below:
 * <ul>
 * <li><a href="http://crunchify.com/how-to-read-json-object-from-file-in-java/">
 * How to read JSON object from File in Java?</a>, crunchify.com</li>
 * <li><a href="http://crunchify.com/how-to-write-json-object-to-file-in-java/">
 * How to write JSON object to File in Java?</a>, crunchify.com</li>
 * </ul>
 * </p>
 * 
 * @author CSCE 740 Group 1 - Jessica Anderson, Natasha Delahunt, Urban Jaklin
 *
 */
public class HandleJSON {

	private static JSONObject[] jsonEventArray;

	/**
	 * This constructor currently only initializes the HandleJSON object.
	 */
	public HandleJSON() {

	}

	/**
	 * The <code>addMeeting</code> method checks for the existence of a storage
	 * document and, if a document is found, appends a meeting event to the
	 * existing "Events" array within the document. If no document is found, one
	 * is created with the meeting ID as the "MeetingCount" counter, and with
	 * the meeting as the first event in the "Events" array.
	 * 
	 * @param mtg
	 *            a meeting object to be parsed and added to a current list of
	 *            events stored in the storage document, or to be the first
	 *            event in an array of events in the storage document
	 */
	public void addMeeting(Meeting mtg) {
		// Initialize the JSON object variable to eventually be stored in
		// the storage document
		JSONObject newEntry = new JSONObject();
		
		// Initialize a JSON object to store in the "Events" array
		JSONObject obj = new JSONObject();

		// Get a few of the attributes from the meeting event object
		String room = mtg.getEventRoom();
		Date start = mtg.getStartTime();
		Date end = mtg.getEndTime();
		int eventID = mtg.getEventID();

		// Create a File object with the file location and name of our
		// storage document
		File check = new File("./resources/events.json");

		// Check for existence of the file at its specified location
		if (check.exists() && !check.isDirectory()) {
			// Use readFile method to get contents of file as JSON object
			JSONObject objectInFile = readFile("./resources/events.json");

			// Use checkRoomAvailability method to ensure there are no
			// conflicts in adding this meeting event
			checkRoomAvailability(room, start, end);

			// Populate the key-value pairs in the JSON object
			obj.put("id", eventID);
			obj.put("desc", mtg.getEventDescription());
			obj.put("start", start.toString());
			obj.put("end", end.toString());
			obj.put("room", room);

			// Initialize a JSON array variable to store the array of
			// participants in the meeting
			JSONArray empArray = new JSONArray();

			// Check if mtg has an array of participants assigned, then
			// add those to the JSON array
			if (mtg.getEmpArray() != null) {
				for (int i = 0; i < mtg.getEmpArray().length; i++) {
					empArray.add(mtg.getEmpArray()[i]);
				}
			}

			// Put the JSON array of participants in the mtg JSON object
			obj.put("emp", empArray);

			// Put the events previously in the "Events" array in the
			// storage document in a JSON array
			JSONArray events = (JSONArray) objectInFile.get("Events");

			// Add the mtg object (event) you have created into the "Events"
			// array
			events.add(obj);

			// Put the "MeetingCount" into the main JSON object to be stored
			newEntry.put("MeetingCount", eventID);

			// If a "VacationCount" was previously in the main JSON object,
			// make sure it is a stored in the new one
			if (objectInFile.containsKey("VacationCount")) {
				newEntry.put("VacationCount", objectInFile.get("VacationCount"));
			}
			// If a "HolidayCount" was previously in the main JSON object,
			// make sure it is a stored in the new one
			if (objectInFile.containsKey("HolidayCount")) {
				newEntry.put("HolidayCount", objectInFile.get("HolidayCount"));
			}

			// Store the events array you created on lines 113 and 117 in the
			// main JSON object to be stored
			newEntry.put("Events", events);

		}
		// Case if storage document file doesn't exist yet
		else {
			// Initialize a JSON Array as the "Events" array
			JSONArray newArray = new JSONArray();

			// Populate the key-value pairs in the JSON object
			obj.put("id", eventID);
			obj.put("desc", mtg.getEventDescription());
			obj.put("start", start.toString());
			obj.put("end", end.toString());
			obj.put("room", room);

			// Initialize a JSON array variable to store the array of
			// participants in the meeting
			JSONArray empArray = new JSONArray();

			// Check if mtg has an array of participants assigned, then
			// add those to the JSON array
			if (mtg.getEmpArray() != null) {
				for (int i = 0; i < mtg.getEmpArray().length; i++) {
					empArray.add(mtg.getEmpArray()[i]);
				}
			}

			// Put the JSON array of participants in the mtg JSON object
			obj.put("emp", empArray);

			// Add the mtg event JSON object you have created to the
			// "Events" array
			newArray.add(obj);

			// Put the "MeetingCount" into the main JSON object to be stored
			newEntry.put("MeetingCount", eventID);

			// Add the "Events" array to the main JSON object to be stored
			newEntry.put("Events", newArray);

		}

		// Attempt to write the main JSON object to the storage document file
		try (FileWriter file = new FileWriter("./resources/events.json")) {
			file.write(newEntry.toJSONString());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("New meeting created successfully.\n" + obj);

	}

	/**
	 * The <code>addVacation</code> method checks for the existence of a storage
	 * document and, if a document is found, appends a vacation event to the
	 * existing "Events" array within the document. If no document is found, one
	 * is created with the meeting ID as the "VacationCount" counter, and with
	 * the vacation as the first event in the "Events" array.
	 * 
	 * @param vac
	 *            a vacation object to be parsed and added to a current list of
	 *            events stored in the storage document, or to be the first
	 *            event in an array of events in the storage document
	 */
	public void addVacation(Vacation vac) {

		JSONObject newEntry = new JSONObject();
		Date start = vac.getStartTime();
		Date end = vac.getEndTime();
		int eventID = vac.getEventID();
		
		JSONObject obj = new JSONObject();

		File check = new File("./resources/events.json");
		if (check.exists() && !check.isDirectory()) {
//			System.out.println("events.json exists!");
			JSONObject objectInFile = readFile("./resources/events.json");
			// checkRoomAvailability(objectsInFile, room, start, end);
			JSONArray newArray = new JSONArray();
			// String id = Integer.toString(eventID);
			obj.put("id", eventID);
			obj.put("desc", vac.getEventDescription());
			obj.put("start", start.toString());
			obj.put("end", end.toString());
			obj.put("room", "");

			JSONArray empArray = new JSONArray();
			if (vac.getEmpArray() != null) {
				for (int i = 0; i < vac.getEmpArray().length; i++) {
					empArray.add(vac.getEmpArray()[i]);
				}
			}
			obj.put("emp", empArray);
			JSONArray events = (JSONArray) objectInFile.get("Events");
			events.add(obj);
			// System.out.println(obj);
			// System.out.println(events.toString());

			newEntry.put("VacationCount", eventID);

			if (objectInFile.containsKey("MeetingCount")) {
				newEntry.put("MeetingCount", objectInFile.get("MeetingCount"));
			}
			if (objectInFile.containsKey("HolidayCount")) {
				newEntry.put("HolidayCount", objectInFile.get("HolidayCount"));
			}
			newEntry.put("Events", events);

		} else {
			JSONArray newArray = new JSONArray();
			// String id = Integer.toString(eventID);
			obj.put("id", eventID);
			obj.put("desc", vac.getEventDescription());
			obj.put("start", start.toString());
			obj.put("end", end.toString());
			obj.put("room", "");

			JSONArray empArray = new JSONArray();
			if (vac.getEmpArray() != null) {
				for (int i = 0; i < vac.getEmpArray().length; i++) {
					empArray.add(vac.getEmpArray()[i]);
				}
			}
			obj.put("emp", empArray);

			newArray.add(obj);

			newEntry.put("VacationCount", eventID);

			newEntry.put("Events", newArray);

		}

		try (FileWriter file = new FileWriter("./resources/events.json")) {
			file.write(newEntry.toJSONString());
//			System.out.println("Successfully copied JSON Object to File...");
//			System.out.println("\nJSON Array: " + newEntry);
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("New vacation created successfully.\n" + obj);

	}

	/**
	 * The <code>addHoliday</code> method checks for the existence of a storage
	 * document and, if a document is found, appends a holiday event to the
	 * existing "Events" array within the document. If no document is found, one
	 * is created with the meeting ID as the "HolidayCount" counter, and with
	 * the holiday as the first event in the "Events" array.
	 * 
	 * @param hldy
	 *            a holiday object to be parsed and added to a current list of
	 *            events stored in the storage document, or to be the first
	 *            event in an array of events in the storage document
	 */
	public void addHoliday(Holiday hldy) {

		JSONObject newEntry = new JSONObject();
		Date start = hldy.getStartTime();
		Date end = hldy.getEndTime();
		int eventID = hldy.getEventID();
		JSONObject obj = new JSONObject();

		File check = new File("./resources/events.json");
		if (check.exists() && !check.isDirectory()) {
//			System.out.println("events.json exists!");
			JSONObject objectInFile = readFile("./resources/events.json");
			// checkRoomAvailability(objectsInFile, room, start, end);
			JSONArray newArray = new JSONArray();
			// String id = Integer.toString(eventID);
			obj.put("id", eventID);
			obj.put("desc", hldy.getEventDescription());
			obj.put("start", start.toString());
			obj.put("end", end.toString());
			obj.put("room", "");

			JSONArray empArray = new JSONArray();
			if (hldy.getEmpArray() != null) {
				for (int i = 0; i < hldy.getEmpArray().length; i++) {
					empArray.add(hldy.getEmpArray()[i]);
				}
			}
			obj.put("emp", empArray);
			JSONArray events = (JSONArray) objectInFile.get("Events");
			events.add(obj);
			// System.out.println(obj);
			// System.out.println(events.toString());

			newEntry.put("HolidayCount", eventID);

			if (objectInFile.containsKey("MeetingCount")) {
				newEntry.put("MeetingCount", objectInFile.get("MeetingCount"));
			}
			if (objectInFile.containsKey("VacationCount")) {
				newEntry.put("VacationCount", objectInFile.get("VacationCount"));
			}
			newEntry.put("Events", events);

		} else {
			JSONArray newArray = new JSONArray();
			// String id = Integer.toString(eventID);
			obj.put("id", eventID);
			obj.put("desc", hldy.getEventDescription());
			obj.put("start", start.toString());
			obj.put("end", end.toString());
			obj.put("room", "");

			JSONArray empArray = new JSONArray();
			if (hldy.getEmpArray() != null) {
				for (int i = 0; i < hldy.getEmpArray().length; i++) {
					empArray.add(hldy.getEmpArray()[i]);
				}
			}
			obj.put("emp", empArray);

			newArray.add(obj);

			newEntry.put("HolidayCount", eventID);

			newEntry.put("Events", newArray);

		}

		try (FileWriter file = new FileWriter("./resources/events.json")) {
			file.write(newEntry.toJSONString());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("New holiday created successfully.\n" + obj);

	}

	/**
	 * The <code>readFile</code> method attempts to read a file at the given
	 * file location using <code>FileReader</code>. Once read, the contents of
	 * the file are converted to a JSON object which can be used for the
	 * addition, modification, or deletion of event objects found in the
	 * "Events" JSON array, and/or can be used for modification or use of the
	 * event counters.
	 * 
	 * @param fileLocation
	 *            a string to indicate the file location
	 * @return a single JSONObject containing the storage document contents
	 */
	public JSONObject readFile(String fileLocation) {
		JSONParser parser = new JSONParser();
		JSONObject returnObject = new JSONObject();

		try {
			FileReader fileRead = new FileReader(fileLocation);
			Object obj = parser.parse(fileRead);

			if (obj instanceof JSONObject) {
				JSONArray eventArray = new JSONArray();
				JSONObject jsonObject = (JSONObject) obj;
				if (jsonObject.containsKey("MeetingCount")) {
					returnObject.put("MeetingCount", (long) jsonObject.get("MeetingCount"));
				}
				if (jsonObject.containsKey("VacationCount")) {
					returnObject.put("VacationCount", (long) jsonObject.get("VacationCount"));
				}
				if (jsonObject.containsKey("HolidayCount")) {
					returnObject.put("HolidayCount", (long) jsonObject.get("HolidayCount"));
				}
				if (jsonObject.containsKey("Events")) {
					eventArray = ((JSONArray) jsonObject.get("Events"));
					List<JSONObject> eventArrayList = new ArrayList<JSONObject>();
					Iterator<JSONObject> iterator = eventArray.iterator();
					while (iterator.hasNext()) {
						eventArrayList.add(iterator.next());
					}
					JSONObject[] jsonObjectArray = new JSONObject[eventArrayList.size()];
					JSONObject[] jsonEventArray = eventArrayList.toArray(jsonObjectArray);
					this.jsonEventArray = jsonEventArray;
					returnObject.put("Events", eventArray);
				}
			} else {
				System.out.println("Error: No JSON Object found in file");
			}
			fileRead.close();
		} catch (Exception e) {
			System.out.println("Error: Storage file not found");
		}
		return returnObject;
	}

//	/**
//	 * This method currently not in use.. disregard for now. May delete later.
//	 * 
//	 * @param jsonObjectArray
//	 * @return
//	 */
//	public JSONObject[] getEventsFromArray(JSONObject[] jsonObjectArray) {
//		JSONObject[] returnArray = new JSONObject[jsonObjectArray.length];
//		for (int i = 0; i < jsonObjectArray.length; i++) {
////			System.out.println(jsonObjectArray[i].toString());
//			long id = (long) jsonObjectArray[i].get("id");
////			System.out.println(id);
//			String desc = (String) jsonObjectArray[i].get("desc");
//			String start = (String) jsonObjectArray[i].get("start");
//			String end = (String) jsonObjectArray[i].get("end");
//
//			returnArray[i].put("id", id);
//			returnArray[i].put("desc", desc);
//			returnArray[i].put("start", start);
//			returnArray[i].put("end", end);
//
//			JSONArray empArray = (JSONArray) jsonObjectArray[i].get("emp");
//			// List<String> empArrayList = new ArrayList<String>();
//			// Iterator<String> iterator = empArray.iterator();
//			// while(iterator.hasNext()) {
//			// empArrayList.add(iterator.next());
//			// }
//			// String[] emp = new String[empArrayList.size()];
//			// emp = empArrayList.toArray(emp);
//			returnArray[i].put("emp", empArray);
//			if (jsonObjectArray[i].containsKey("room")) {
//				String room = (String) jsonObjectArray[i].get("room");
//				returnArray[i].put("room", room);
//			}
////			System.out.println(returnArray[i].toString());
//		}
//		return returnArray;
//	}

	/**
	 * The <code>getEventsByEmployee</code> method finds all events which a
	 * given employee (String) is scheduled to participate in.
	 * 
	 * @param employee
	 *            the employee which you are filtering events by
	 * 
	 * @return an array of JSON objects / events in the storage document
	 */
	public JSONObject[] getEventsByEmployee(String employee) {
		JSONObject[] returnArray;
		List<JSONObject> objectArrayList = new ArrayList<JSONObject>();
		File check = new File("./resources/events.json");
		if (check.exists() && !check.isDirectory()) {
			readFile("./resources/events.json");
			for (int i = 0; i < jsonEventArray.length; i++) {
				JSONArray empJsonArray = (JSONArray) jsonEventArray[i].get("emp");
				Iterator<String> iterator = empJsonArray.iterator();
				while (iterator.hasNext()) {
					if ((iterator.next()).equals(employee)) {
//						System.out.println("event found for " + employee);
						objectArrayList.add(jsonEventArray[i]);
//						System.out.println(jsonEventArray[i]);
					}
				}
			}
			returnArray = new JSONObject[objectArrayList.size()];
			returnArray = objectArrayList.toArray(returnArray);
		} else {
			returnArray = null;
			System.out.println("Error: No Events file exists.");
		}
		return returnArray;
	}

	/**
	 * The <code>getEventsByRoom</code> method finds all events which are held
	 * in the room provided as the parameter.
	 * 
	 * @param room
	 *            the room which you are filtering events by
	 * 
	 * @return an array of JSON objects / events in the storage document
	 */
	public JSONObject[] getEventsByRoom(String room) {
		JSONObject[] returnArray;
		List<JSONObject> objectArrayList = new ArrayList<JSONObject>();
		File check = new File("./resources/events.json");
		if (check.exists() && !check.isDirectory()) {
			readFile("./resources/events.json");
			for (int i = 0; i < jsonEventArray.length; i++) {
				if (jsonEventArray[i].containsValue(room)) {
//					System.out.println("event found for " + room);
					objectArrayList.add(jsonEventArray[i]);
//					System.out.println(jsonEventArray[i]);
				}
			}
			returnArray = new JSONObject[objectArrayList.size()];
			returnArray = objectArrayList.toArray(returnArray);
		} else {
			returnArray = null;
			System.out.println("Error: No Events file exists.");
		}
		return returnArray;
	}
	
	/**
	 * The <code>getEventByID</code> method finds the event which has the
	 * event id provided by id.
	 * 
	 * @param id
	 *            the id which you are filtering events by
	 * 
	 * @return an array of JSON objects / events in the storage document
	 */
	public JSONObject getEventByID(int id) {
		JSONObject returnObject = new JSONObject();
		File check = new File("./resources/events.json");
		if (check.exists() && !check.isDirectory()) {
			readFile("./resources/events.json");
			for (int i = 0; i < jsonEventArray.length; i++) {
				if (jsonEventArray[i].containsValue((long) id)) {
//					System.out.println("event found for " + id);
//					System.out.println(jsonEventArray[i]);
					returnObject = jsonEventArray[i];
//					System.out.println(returnObject);
				}
			}

		} else {
			returnObject = null;
			System.out.println("Error: No Events file exists.");
		}
//		System.out.println(returnObject);
		return returnObject;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public int getIndexOfEventByID(int id) {
		int index = -1;
		File check = new File("./resources/events.json");
		if (check.exists() && !check.isDirectory()) {
			readFile("./resources/events.json");
			for (int i = 0; i < jsonEventArray.length; i++) {
				if (jsonEventArray[i].containsValue((long) id)) {
//					System.out.println("event found for " + id);
//					System.out.println(jsonEventArray[i]);
					index = i;
				}
			}
		} else {
			System.out.println("Error: No Events file exists.");
		}
//		System.out.println(index);
		return index;
	}

	/**
	 * The <code>getAllEvents</code> method finds all events in the storage
	 * document.
	 * 
	 * @return an array of JSON objects / events in the storage document
	 */
	public JSONObject[] getAllEvents() {
		JSONObject[] returnArray;
		File check = new File("./resources/events.json");
		if (check.exists() && !check.isDirectory()) {
			readFile("./resources/events.json");
			returnArray = jsonEventArray;
		} else {
			System.out.println("Error: No events file exists");
			returnArray = null;
		}
		return returnArray;
	}
	
//	public Event convertToEvent(JSONObject jsonEvent) {
//		Event returnEvent = new Event();
//		int eventID = (int) (long) jsonEvent.get("id");
//		String eventDescription = (String) jsonEvent.get("desc");
//		String startString = (String) jsonEvent.get("start");
//		String endString = (String) jsonEvent.get("end");
//		JSONArray empJsonArray = (JSONArray) jsonEvent.get("emp");
//		Date startTime = null, endTime = null;
//		try {
//			startTime = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(startString);
//			endTime = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(endString);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		List<String> empArrayList = new ArrayList<String>();
//		Iterator<String> iterator = empJsonArray.iterator();
//		while (iterator.hasNext()) {
//			empArrayList.add(iterator.next());
//		}
//		String[] empArray = new String[empArrayList.size()];
//		empArray = empArrayList.toArray(empArray);
//		String eventRoom = (String) jsonEvent.get("room");
//				
//		int eventIDStartsWith = Integer.parseInt(Integer.toString(eventID).substring(0, 1));
//		if(eventIDStartsWith == 1) {
//			returnEvent = new Meeting(eventID, eventDescription, startTime, endTime, empArray, eventRoom);
//		}
//		else if(eventIDStartsWith == 2) {
//			returnEvent = new Vacation(eventID, eventDescription, startTime, endTime, empArray);
//		}
//		else if(eventIDStartsWith == 3) {
//			returnEvent = new Holiday(eventID, eventDescription, startTime, endTime, empArray);
//		}
//		return returnEvent;
//	}
//	
//	public Event[][] parseEvents(JSONObject[] events) {
//		Event[][] returnArray;
//		List<Event> meetingsArrayList = new ArrayList<Event>();
//		List<Event> vacationsArrayList = new ArrayList<Event>();
//		List<Event> holidaysArrayList = new ArrayList<Event>();
//		List<Event> companyArrayList = new ArrayList<Event>();
//		for (int i = 0; i < events.length; i++) {
//			int eventID = (int) (long) events[i].get("id");
//			int eventIDStartsWith = Integer.parseInt(Integer.toString(eventID).substring(0, 1));
//			if(eventIDStartsWith == 1) {
//				meetingsArrayList.add(convertToEvent(events[i]));
//			}
//			else if(eventIDStartsWith == 2) {
//				vacationsArrayList.add(convertToEvent(events[i]));
//			}
//			else if(eventIDStartsWith == 3) {
//				holidaysArrayList.add(convertToEvent(events[i]));
//			}
//		}
//		returnArray = new Event[4][];
//		if(meetingsArrayList.size() > 0) {
//			returnArray[0] = new Event[meetingsArrayList.size()];
//			returnArray[0] = meetingsArrayList.toArray(returnArray[0]);
//		}
//		if(vacationsArrayList.size() > 0) {
//			returnArray[1] = new Event[vacationsArrayList.size()];
//			returnArray[1] = meetingsArrayList.toArray(returnArray[1]);
//		}
//		if(holidaysArrayList.size() > 0) {
//			returnArray[2] = new Event[holidaysArrayList.size()];
//			returnArray[2] = meetingsArrayList.toArray(returnArray[2]);
//		}
////		System.out.println("Index 0: " + Arrays.toString(returnArray[0]));
//		return returnArray;
//	}

	public void checkRoomAvailability(String room, Date start, Date end) {
		JSONObject[] events = getEventsByRoom(room);
		for (int i = 0; i < events.length; i++) {
			String startString = (String) events[i].get("start");
			String endString = (String) events[i].get("end");
			Date startDate = null, endDate = null;
			try {
				startDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(startString);
				endDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(endString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (startDate != null && endDate != null) {
				if (!(start.before(startDate) || start.after(endDate)) || start.equals(startDate)
						|| end.equals(endDate)) {
					System.out.println("Warning: New event falls within range of another event in the same room");
					break;
				}
			}
		}
	}

	public long getMeetingCount() {
		long meetingCount = 0;
		File check = new File("./resources/events.json");
		if (check.exists() && !check.isDirectory()) {
//			System.out.println("events.json exists!");
			JSONObject objectInFile = readFile("./resources/events.json");
			if (objectInFile.containsKey("MeetingCount")) {
				meetingCount = (long) objectInFile.get("MeetingCount");
			} else {
				meetingCount = 9999999;
				System.out.println("Error: No MeetingCount value found");
			}
		} else {
			meetingCount = 9999999;
			System.out.println("Error: No file containing events was found");
		}
		return meetingCount;
	}

	public long getVacationCount() {
		long vacationCount = 0;
		File check = new File("./resources/events.json");
		if (check.exists() && !check.isDirectory()) {
//			System.out.println("events.json exists!");
			JSONObject objectInFile = readFile("./resources/events.json");
			if (objectInFile.containsKey("VacationCount")) {
				vacationCount = (long) objectInFile.get("VacationCount");
			} else {
				vacationCount = 19999999;
				System.out.println("Error: No VacationCount value found");
			}
		} else {
			vacationCount = 19999999;
			System.out.println("Error: No file containing events was found");
		}
		return vacationCount;
	}

	public long getHolidayCount() {
		long holidayCount = 0;
		File check = new File("./resources/events.json");
		if (check.exists() && !check.isDirectory()) {
//			System.out.println("/JSonFiles/events.json exists!");
			JSONObject objectInFile = readFile("./resources/events.json");
			if (objectInFile.containsKey("HolidayCount")) {
				holidayCount = (long) objectInFile.get("HolidayCount");
			} else {
				holidayCount = 29999999;
				System.out.println("Error: No HolidayCount value found");
			}
		} else {
			holidayCount = 29999999;
			System.out.println("Error: No file containing events was found");
		}
		return holidayCount;
	}
	
	public String[] retrieveEmployees() {
		String[] returnArray = {};
		JSONParser parser = new JSONParser();
		try {		
			Object obj = parser.parse(new FileReader("./resources/employees.json"));
				
			if (obj instanceof JSONArray) {
				JSONArray empJsonArray = (JSONArray) obj;
				List<String> empArrayList = new ArrayList<String>();
				Iterator<JSONObject> iterator = empJsonArray.iterator();
				while (iterator.hasNext()) {
					JSONObject empObject =  iterator.next();
					String id = (String) empObject.get("id");
					String first = (String) empObject.get("firstName");
					String last = (String) empObject.get("lastName");
					empArrayList.add(first + " " + last + " " + id);
				}
				returnArray = new String[empArrayList.size()];
				returnArray = empArrayList.toArray(returnArray);
			} else {
				System.out.println("Error: No JSON Array found in file");
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnArray;
	}
	
	public String[] retrieveRooms() {
		String[] returnArray = {};
		JSONParser parser = new JSONParser();
		try {		
			Object obj = parser.parse(new FileReader("./resources/rooms.json"));
				
			if (obj instanceof JSONArray) {
				JSONArray roomJsonArray = (JSONArray) obj;
				List<String> roomArrayList = new ArrayList<String>();
				Iterator<JSONObject> iterator = roomJsonArray.iterator();
				while (iterator.hasNext()) {
					JSONObject roomObject =  iterator.next();
					String building = (String) roomObject.get("building");
					String id = (String) roomObject.get("id");
					String room = building + " " + id;
					roomArrayList.add(room);
				}
				returnArray = new String[roomArrayList.size()];
				returnArray = roomArrayList.toArray(returnArray);
			} else {
				System.err.println("Error: No JSON Array found in file");
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnArray;
	}
	
	public void executeScript(String file) {
//		System.out.println("File: " + file);
		JSONParser parser = new JSONParser();
		File check = new File(file);
		if (check.exists() && !check.isDirectory()) {
			try {
				Object obj = parser.parse(new FileReader(file));
				if(obj instanceof JSONArray) {
					JSONArray scriptArray = (JSONArray) obj;
//					List<JSONObject> objectArrayList = new ArrayList<JSONObject>();
					Iterator<JSONObject> iterator = scriptArray.iterator();
					while (iterator.hasNext()) {
//						objectArrayList.add(iterator.next());
						JSONObject theObject = iterator.next();
//						System.out.println(theObject);
//						System.out.println(theObject.get("commands"));
						JSONArray commands = (JSONArray) theObject.get("commands");
//						System.out.println(commands.toString());
						if(theObject.containsKey("commands")) {
							executeCommands(commands);
						} else {
							System.out.println("Error: No commands found in script file");
						}
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void executeCommands(JSONArray commands) {
		Iterator<JSONObject> iterator = commands.iterator();
		while (iterator.hasNext()) {
			JSONObject theObject = iterator.next();
			String name = (String) theObject.get("name");
			JSONArray arguments = (JSONArray) theObject.get("arguments");
			if(name.equals("add-meeting")) {
				addMeetingFromScript(arguments);
			}
			if(name.equals("add-vacation")) {
				addVacationFromScript(arguments);
			}
			if(name.equals("add-holiday")) {
				addHolidayFromScript(arguments);
			}
			if(name.equals("edit-meeting-details")) {
				editMeetingFromScript(arguments);
			}
			if(name.equals("edit-meeting-add-attendees")) {
				editMeetingAddAttendees(arguments);
			}
			if(name.equals("edit-meeting-remove-attendees")) {
				editMeetingRemoveAttendees(arguments);
			}
			if(name.equals("delete-meeting")) {
				deleteMeetingFromScript(arguments);
			}
			if(name.equals("print-schedule-all")) {
				printScheduleAll(arguments);
			}
			if(name.equals("print-schedule-employee")) {
				printScheduleEmployee(arguments);
			}
			if(name.equals("print-schedule-room")) {
				printScheduleRoom(arguments);
			}
		}
		
	}
	
	public void addMeetingFromScript(JSONArray arguments) {
		// Initialize EventFactory
		EventFactory eventFactory = new EventFactory();
		// Initialize variables to be used as parameters to new Event creation
		String date = "";
		String startString = "";
		String endString = "";
		String eventRoom = "";
		String eventDescription = "";
		// Set date format to be used for Date creation
		SimpleDateFormat format = new SimpleDateFormat("MMddyyyy HH:mm");
		// Initialize start and end times as Date
		Date startTime = new Date();
		Date endTime = new Date();
		// Initialize ArrayList that will store meeting attendees
		List<String> empArrayList = new ArrayList<String>();
		
//		System.out.println("adding a meeting from script!");
		// Iterator to go through the JSON objects (arguments) in the JSON array
		Iterator<JSONObject> iterator = arguments.iterator();
		while (iterator.hasNext()) {
			JSONObject theObject = iterator.next();
			String name = (String) theObject.get("name");
			String value = (String) theObject.get("value");
			if(name.equals("date")) {
				date = value;
			}
			else if(name.equals("start-time")) {
				startString = value;
			}
			else if(name.equals("end-time")) {
				endString = value;
			}
			else if(name.equals("room-id")) {
				eventRoom = value;
			}
			else if(name.equals("attendee")) {
				empArrayList.add(value);
			}
			else if(name.equals("description")) {
				eventDescription = value;
			}
		}
		
		try {
			startTime = format.parse(date + " " + startString);
			endTime = format.parse(date + " " + endString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String[] empArray = new String[empArrayList.size()];
		empArray = empArrayList.toArray(empArray);
		
		Event event = eventFactory.createEvent(1, eventDescription, startTime, endTime, empArray, eventRoom); 
	}
	
	public void addVacationFromScript(JSONArray arguments) {
		// Initialize EventFactory
		EventFactory eventFactory = new EventFactory();
		// Initialize variables to be used as parameters to new Event creation
		String employee = "";
		String startString = "";
		String endString = "";
		String eventDescription = "";
		// Set date format to be used for Date creation
		SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
		// Initialize start and end times as Date
		Date startTime = new Date();
		Date endTime = new Date();

		
//		System.out.println("adding a vacation from script!");
		// Iterator to go through the JSON objects (arguments) in the JSON array
		Iterator<JSONObject> iterator = arguments.iterator();
		while (iterator.hasNext()) {
			JSONObject theObject = iterator.next();
			String name = (String) theObject.get("name");
			String value = (String) theObject.get("value");
			if(name.equals("employee-id")) {
				employee = value;
			}
			else if(name.equals("start-date")) {
				startString = value;
			}
			else if(name.equals("end-date")) {
				endString = value;
			}
			else if(name.equals("description")) {
				eventDescription = value;
			}
		}
		
		try {
			startTime = format.parse(startString);
			endTime = format.parse(endString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String[] empArray = { employee };
		
		Event event = eventFactory.createEvent(2, eventDescription, startTime, endTime, empArray, null); 
	}
	
	public void addHolidayFromScript(JSONArray arguments) {
		// Initialize EventFactory
		EventFactory eventFactory = new EventFactory();
		// Initialize variables to be used as parameters to new Event creation
		String employee = "company";
		String startString = "";
		String endString = "";
		String eventDescription = "";
		// Set date format to be used for Date creation
		SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
		// Initialize start and end times as Date
		Date startTime = new Date();
		Date endTime = new Date();

		
//		System.out.println("adding a vacation from script!");
		// Iterator to go through the JSON objects (arguments) in the JSON array
		Iterator<JSONObject> iterator = arguments.iterator();
		while (iterator.hasNext()) {
			JSONObject theObject = iterator.next();
			String name = (String) theObject.get("name");
			String value = (String) theObject.get("value");
			if(name.equals("start-date")) {
				startString = value;
			}
			else if(name.equals("end-date")) {
				endString = value;
			}
			else if(name.equals("description")) {
				eventDescription = value;
			}
		}
		
		try {
			startTime = format.parse(startString);
			endTime = format.parse(endString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String[] empArray = { employee };
		
		Event event = eventFactory.createEvent(3, eventDescription, startTime, endTime, empArray, null); 
	}
	
	public void editMeetingFromScript(JSONArray arguments) {
		// readFile to initialize and have access to the Event file as JSON Objects
		readFile("./resources/events.json");
		
		// Initialize variables to be used as parameters to edit Meeting
		String id = "";
		String date = "";
		String startString = "";
		String endString = "";
		String eventRoom = "";
		String eventDescription = "";
		
		// Set date format to be used for Date creation
		SimpleDateFormat format = new SimpleDateFormat("MMddyyyy HH:mm");
		
		// Initialize start and end times as Date
		Date startTime = new Date();
		Date endTime = new Date();
		
//		System.out.println("editing a meeting from script!");
		
		// Iterator to go through the JSON objects (arguments) in the JSON array
		Iterator<JSONObject> iterator = arguments.iterator();
		while (iterator.hasNext()) {
			JSONObject theObject = iterator.next();
			String name = (String) theObject.get("name");
			String value = (String) theObject.get("value");
			if(name.equals("meeting-id")) {
				id = value;
			}
			else if(name.equals("date")) {
				date = value;
			}
			else if(name.equals("start-time")) {
				startString = value;
			}
			else if(name.equals("end-time")) {
				endString = value;
			}
			else if(name.equals("room-id")) {
				eventRoom = value;
			}
			else if(name.equals("description")) {
				eventDescription = value;
			}
		}
		
//		System.out.println("The id we're editing is: " + Integer.valueOf(id));
		
		// Retrieve meeting with meeting id given
		JSONObject meeting = getEventByID(Integer.valueOf(id));
		
		// Retrieve index of meeting within events array in storage document
		int index = getIndexOfEventByID(Integer.valueOf(id));
		
		// If getEventByID returns null or if index is -1, print error
		// Else, edit the meeting found 
		if(meeting == null || index == -1) {
			System.out.println("Error: Meeting was not found by the specified ID.");
		}
		else {
			// Initialize JSON object which will serve as new object within storage document
			JSONObject newEntry = new JSONObject();
			
			// Initialize variables in which to store counts
			int meetingCount;
			int vacationCount;
			int holidayCount;
			
			// Get the current contents of the storage file
			JSONObject eventsAndCounts = readFile("./resources/events.json");
			
			// Store the counts in variables
			if(eventsAndCounts.containsKey("MeetingCount")) {
				meetingCount = (int) (long) eventsAndCounts.get("MeetingCount");
				newEntry.put("MeetingCount", meetingCount);
			}
			if(eventsAndCounts.containsKey("VacationCount")) {
				vacationCount = (int) (long) eventsAndCounts.get("VacationCount");
				newEntry.put("VacationCount", vacationCount);
			}
			if(eventsAndCounts.containsKey("HolidayCount")) {
				holidayCount = (int) (long) eventsAndCounts.get("HolidayCount");
				newEntry.put("HolidayCount", holidayCount);
			}
			
			// Get Events array from JSON object retrieved
			JSONArray events = (JSONArray) eventsAndCounts.get("Events");
			
			// Remove event we are editing from the Events array
			events.remove(index);
			
//			System.out.println(meeting.toString());
			
			// Retrieve the start and end dates from the existing meeting, and format them to 
			// appear as Date variables
			String existingStart = (String) meeting.get("start");
			String existingEnd = (String) meeting.get("end");
			Date existingStartDate = null, existingEndDate = null;
			SimpleDateFormat existingFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
			try {
				existingStartDate = existingFormat.parse(existingStart);
				existingEndDate = existingFormat.parse(existingEnd);
//				System.out.println(existingEndDate.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			// Use Calendar to get month, day, year, hours, and minutes as separate values for start time
			Calendar startCal = new GregorianCalendar();
			startCal.setTime(existingStartDate);
			String existingStartDateString =  (startCal.get(Calendar.MONTH)+1) + "" + startCal.get(Calendar.DAY_OF_MONTH) + "" + startCal.get(Calendar.YEAR);
			String existingStartTimeString = startCal.get(Calendar.HOUR) + ":" + startCal.get(Calendar.MINUTE);
			
			// Use Calendar to get month, day, year, hours, and minutes as separate values for end time
			Calendar endCal = new GregorianCalendar();
			endCal.setTime(existingEndDate);
			String existingEndDateString =  (endCal.get(Calendar.MONTH)+1) + "" + endCal.get(Calendar.DAY_OF_MONTH) + "" + endCal.get(Calendar.YEAR);
			String existingEndTimeString = endCal.get(Calendar.HOUR) + ":" + endCal.get(Calendar.MINUTE);
			
			// If date was listed as field to modify
			if(!date.equals("")) {
				// And if start time was listed as a field to modify, generate a new startTime Date
				if(!startString.equals("")) {
					try {
						startTime = format.parse(date + " " + startString);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} 
				// If date was changed but start time was not, still need to generate a new startTime Date
				else {
					try {
						startTime = format.parse(date + " " + existingStartTimeString);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
				// Put start time in updated meeting event object
				meeting.put("start", startTime.toString());
				
				// Similarly for end time
				if(!endString.equals("")) {
					try {
						endTime = format.parse(date + " " + endString);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else {
					try {
						startTime = format.parse(date + " " + existingEndTimeString);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
				// Put end time in updated meeting event object
				meeting.put("end", endTime.toString());
			} 
			
			// If date was not modified, we still need to see if start or end times need to be updated
			else {
				// Now we update the start time to have the existing date paired with the updated time
				if(!startString.equals("")) {
					try {
						startTime = format.parse(existingStartDateString + " " + startString);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					meeting.put("start", startTime.toString());
				}
				// And now we update the end time to have the existing date paired with the updated time
				if(!endString.equals("")) {
					try {
						endTime = format.parse(existingEndDateString + " " + endString);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					meeting.put("end", endTime.toString());
				}
			}
			
			// If the room was modified, we alter the updated meeting object to reflect this
			if(!eventRoom.equals("")) {
				meeting.put("room", eventRoom);
			}
			
			// If the description was modified, we alter the updated meeting object to reflect this
			if(!eventDescription.equals("")) {
				meeting.put("desc", eventDescription);
			}
			
			// Add this meeting back into the Events array
			events.add(meeting);

			// Add the "Events" array to the main JSON object to be stored
			newEntry.put("Events", events);
			
			// Attempt to write the main JSON object to the storage document file
			try (FileWriter file = new FileWriter("./resources/events.json")) {
				file.write(newEntry.toJSONString());
				file.close();
//				System.out.println("Successfully copied JSON Object to File...");
//				System.out.println("\nJSON Array: " + newEntry);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("Edited the meeting with id: " + id + " to appear as the following: " + meeting.toString());
		}	
	}
	
	public void editMeetingAddAttendees(JSONArray arguments) {
		// Initialize variables to be used as parameters to edit Meeting
		String id = "";
		String attendee = "";
		
//		System.out.println("editing a meeting from script!");
		
		// Iterator to go through the JSON objects (arguments) in the JSON array
		Iterator<JSONObject> iterator = arguments.iterator();
		while (iterator.hasNext()) {
			JSONObject theObject = iterator.next();
			String name = (String) theObject.get("name");
			String value = (String) theObject.get("value");
			if(name.equals("meeting-id")) {
				id = value;
			}
			else if(name.equals("attendee")) {
				attendee = value;
			}
		}
		
		// Retrieve meeting with meeting id given
		JSONObject meeting = getEventByID(Integer.valueOf(id));
		
		// Retrieve index of meeting within events array in storage document
		int index = getIndexOfEventByID(Integer.valueOf(id));
		
		// If getEventByID returns null, print error
		// Else, edit the meeting found 
		if(meeting == null || index == -1) {
			System.out.println("Error: Meeting was not found by the specified ID.");
		}
		else {
			// Initialize JSON object which will serve as new object within storage document
			JSONObject newEntry = new JSONObject();
			
			// Initialize variables in which to store counts
			int meetingCount;
			int vacationCount;
			int holidayCount;
			
			// Get the current contents of the storage file
			JSONObject eventsAndCounts = readFile("./resources/events.json");
			
			// Store the counts in variables
			if(eventsAndCounts.containsKey("MeetingCount")) {
				meetingCount = (int) (long) eventsAndCounts.get("MeetingCount");
				newEntry.put("MeetingCount", meetingCount);
			}
			if(eventsAndCounts.containsKey("VacationCount")) {
				vacationCount = (int) (long) eventsAndCounts.get("VacationCount");
				newEntry.put("VacationCount", vacationCount);
			}
			if(eventsAndCounts.containsKey("HolidayCount")) {
				holidayCount = (int) (long) eventsAndCounts.get("HolidayCount");
				newEntry.put("HolidayCount", holidayCount);
			}
			
			// Get Events array from JSON object retrieved
			JSONArray events = (JSONArray) eventsAndCounts.get("Events");
			
			// Remove event we are editing from the Events array
			events.remove(index);
			
			// Get the existing list of participants as a JSON array
			JSONArray empJsonArray = (JSONArray) meeting.get("emp");
			
			// Add the specified attendee to the array
			empJsonArray.add(attendee);
			
			// Put the array list back under the key "emp"
			meeting.put("emp", empJsonArray);
			
			// Add this meeting back into the Events array
			events.add(meeting);

			// Add the "Events" array to the main JSON object to be stored
			newEntry.put("Events", events);
			
			// Attempt to write the main JSON object to the storage document file
			try (FileWriter file = new FileWriter("./resources/events.json")) {
				file.write(newEntry.toJSONString());
				file.close();
//				System.out.println("Successfully copied JSON Object to File...");
//				System.out.println("\nJSON Array: " + newEntry);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Edited the meeting with id: " + id + " to appear as the following: " + meeting.toString());
		}
	}
	
	public void editMeetingRemoveAttendees(JSONArray arguments) {
		// Initialize variables to be used as parameters to edit Meeting
		String id = "";
		String attendee = "";
		
//		System.out.println("editing a meeting from script!");
		
		// Iterator to go through the JSON objects (arguments) in the JSON array
		Iterator<JSONObject> iterator = arguments.iterator();
		while (iterator.hasNext()) {
			JSONObject theObject = iterator.next();
			String name = (String) theObject.get("name");
			String value = (String) theObject.get("value");
			if(name.equals("meeting-id")) {
				id = value;
			}
			else if(name.equals("attendee")) {
				attendee = value;
			}
		}
		
		// Retrieve meeting with meeting id given
		JSONObject meeting = getEventByID(Integer.valueOf(id));
		
		// Retrieve index of meeting within events array in storage document
		int index = getIndexOfEventByID(Integer.valueOf(id));
		
		// If getEventByID returns null, print error
		// Else, edit the meeting found 
		if(meeting == null || index == -1) {
			System.out.println("Error: Meeting was not found by the specified ID.");
		}
		else {
			// Initialize JSON object which will serve as new object within storage document
			JSONObject newEntry = new JSONObject();
			
			// Initialize variables in which to store counts
			int meetingCount;
			int vacationCount;
			int holidayCount;
			
			// Get the current contents of the storage file
			JSONObject eventsAndCounts = readFile("./resources/events.json");
			
			// Store the counts in variables
			if(eventsAndCounts.containsKey("MeetingCount")) {
				meetingCount = (int) (long) eventsAndCounts.get("MeetingCount");
				newEntry.put("MeetingCount", meetingCount);
			}
			if(eventsAndCounts.containsKey("VacationCount")) {
				vacationCount = (int) (long) eventsAndCounts.get("VacationCount");
				newEntry.put("VacationCount", vacationCount);
			}
			if(eventsAndCounts.containsKey("HolidayCount")) {
				holidayCount = (int) (long) eventsAndCounts.get("HolidayCount");
				newEntry.put("HolidayCount", holidayCount);
			}
			
			// Get Events array from JSON object retrieved
			JSONArray events = (JSONArray) eventsAndCounts.get("Events");
			
			// Remove event we are editing from the Events array
			events.remove(index);
			
			// Get the existing list of participants as a JSON array
			JSONArray empJsonArray = (JSONArray) meeting.get("emp");
			
			// Check for attendee in array of participants and remove
			for (int i = 0; i < empJsonArray.size(); i++) {
				String participant = (String) empJsonArray.get(i);
				if(participant.equals(attendee)) {
					empJsonArray.remove(i);
				}
			}
			
			// Put the array list back under the key "emp"
			meeting.put("emp", empJsonArray);
			
			// Add this meeting back into the Events array
			events.add(meeting);

			// Add the "Events" array to the main JSON object to be stored
			newEntry.put("Events", events);
			
			// Attempt to write the main JSON object to the storage document file
			try (FileWriter file = new FileWriter("./resources/events.json")) {
				file.write(newEntry.toJSONString());
				file.close();
//				System.out.println("Successfully copied JSON Object to File...");
//				System.out.println("\nJSON Array: " + newEntry);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Edited the meeting with id: " + id + " to appear as the following: " + meeting.toString());
		}
	}
	
	public void deleteMeetingFromScript(JSONArray arguments) {
		// Initialize variables to be used as parameters to delete Meeting
		String id = "";
				
		// Iterator to go through the JSON objects (arguments) in the JSON array
		Iterator<JSONObject> iterator = arguments.iterator();
		while (iterator.hasNext()) {
			JSONObject theObject = iterator.next();
			String name = (String) theObject.get("name");
			String value = (String) theObject.get("value");
			if(name.equals("meeting-id")) {
				id = value;
			}
			else {
				System.err.println("Error: Invalid arguments for delete-meeting.");
			}
		}
		
		// Retrieve meeting with meeting id given
		JSONObject meeting = getEventByID(Integer.valueOf(id));
		
		// Retrieve index of meeting within events array in storage document
		int index = getIndexOfEventByID(Integer.valueOf(id));
		
		// If getEventByID returns null, print error
		if(meeting == null || index == -1) {
			System.out.println("Error: Meeting was not found by the specified ID.");
		}
		// Else, delete the meeting found 
		else {
			// Initialize JSON object which will serve as new object within storage document
			JSONObject newEntry = new JSONObject();
			
			// Initialize variables in which to store counts
			int meetingCount;
			int vacationCount;
			int holidayCount;
			
			// Get the current contents of the storage file
			JSONObject eventsAndCounts = readFile("./resources/events.json");
			
			// Store the counts in variables
			if(eventsAndCounts.containsKey("MeetingCount")) {
				meetingCount = (int) (long) eventsAndCounts.get("MeetingCount");
				newEntry.put("MeetingCount", meetingCount);
			}
			if(eventsAndCounts.containsKey("VacationCount")) {
				vacationCount = (int) (long) eventsAndCounts.get("VacationCount");
				newEntry.put("VacationCount", vacationCount);
			}
			if(eventsAndCounts.containsKey("HolidayCount")) {
				holidayCount = (int) (long) eventsAndCounts.get("HolidayCount");
				newEntry.put("HolidayCount", holidayCount);
			}
			
			// Get Events array from JSON object retrieved
			JSONArray events = (JSONArray) eventsAndCounts.get("Events");
			
			// Remove event we are editing from the Events array
			events.remove(index);

			// Add the "Events" array to the main JSON object to be stored
			newEntry.put("Events", events);
			
			// Attempt to write the main JSON object to the storage document file
			try (FileWriter file = new FileWriter("./resources/events.json")) {
				file.write(newEntry.toJSONString());
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Deleted the meeting with id: " + id);
		}
	}
	
	public void printScheduleAll(JSONArray arguments) {
		// Initialize variables to be used as parameters to print events
		String startString = "";
		String endString = "";
		String outputFile = "";
		
		// Iterator to go through the JSON objects (arguments) in the JSON array
		Iterator<JSONObject> iterator = arguments.iterator();
		while (iterator.hasNext()) {
			JSONObject theObject = iterator.next();
			String name = (String) theObject.get("name");
			String value = (String) theObject.get("value");
			if(name.equals("start-date")) {
				startString = value;
			}
			else if(name.equals("end-date")) {
				endString = value;
			}
			else if(name.equals("output-file")) {
				outputFile = value;
			}
		}
		
		//Initialize variables to hold Date equivalents of startString and endString
		Date startDate = null, endDate = null;
		
		//Attempt to convert the startString and endString to Date variables
		try {
			startDate = new SimpleDateFormat("MMddyyyy").parse(startString);
			endDate = new SimpleDateFormat("MMddyyyy").parse(endString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// Initialize JSON object which will serve as new object to place in output file
		JSONObject newEntry = new JSONObject();
		
		// Initialize ArrayList that will store the meetings we need
		List<JSONObject> eventArrayList = new ArrayList<JSONObject>();
		
		//Retrieve an array of JSONObjects from the storage document
		JSONObject[] events = getAllEvents();
		
		//Check each entry in events array to ensure all start and end times fall
		//between those provided
		for (int i = 0; i < events.length; i++) {
			//Retrieve start and end date strings from events array
			String start = (String) events[i].get("start");
			String end = (String) events[i].get("end");
			
			//Initialize variables to hold Date equivalents of start and end
			Date startEventDate = null, endEventDate = null;
			
			//Attempt to convert start and end to Date variables
			try {
				startEventDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(start);
				endEventDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(end);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (startEventDate != null && endEventDate != null) {
				if (startEventDate.after(startDate) && endEventDate.before(endDate)) {
					eventArrayList.add(events[i]);
				}
			}
		}
		
		//Initialize JSONArray object to hold the events from eventArrayList
		JSONArray eventArray = new JSONArray();
		
		//Use for loop to put JSONObjects into the JSONArray
		for (int i = 0; i < eventArrayList.size(); i++) {
			eventArray.add(eventArrayList.get(i));
		}
		
		// Add the "Events" array to the main JSON object to be stored
		newEntry.put("events", eventArray);
		
		// Attempt to write the main JSON object to the storage document file
		try (FileWriter file = new FileWriter(outputFile)) {
			file.write(newEntry.toJSONString());
			file.close();
			System.out.println("Events written to output file: " + outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printScheduleEmployee(JSONArray arguments) {
		// Initialize variables to be used as parameters to print events
		String employeeID = "";
		String startString = "";
		String endString = "";
		String outputFile = "";
		
		// Iterator to go through the JSON objects (arguments) in the JSON array
		Iterator<JSONObject> iterator = arguments.iterator();
		while (iterator.hasNext()) {
			JSONObject theObject = iterator.next();
			String name = (String) theObject.get("name");
			String value = (String) theObject.get("value");
			if(name.equals("employee-id")) {
				employeeID = value;
			}
			else if(name.equals("start-date")) {
				startString = value;
			}
			else if(name.equals("end-date")) {
				endString = value;
			}
			else if(name.equals("output-file")) {
				outputFile = value;
			}
		}
		
		//Initialize variables to hold Date equivalents of startString and endString
		Date startDate = null, endDate = null;
		
		//Attempt to convert the startString and endString to Date variables
		try {
			startDate = new SimpleDateFormat("MMddyyyy").parse(startString);
			endDate = new SimpleDateFormat("MMddyyyy").parse(endString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// Initialize JSON object which will serve as new object to place in output file
		JSONObject newEntry = new JSONObject();
		
		// Initialize ArrayList that will store the meetings we need
		List<JSONObject> eventArrayList = new ArrayList<JSONObject>();
		
		//Retrieve an array of JSONObjects from the storage document
		JSONObject[] events = getEventsByEmployee(employeeID);
		
		//Check each entry in events array to ensure all start and end times fall
		//between those provided
		for (int i = 0; i < events.length; i++) {
			//Retrieve start and end date strings from events array
			String start = (String) events[i].get("start");
			String end = (String) events[i].get("end");
			
			//Initialize variables to hold Date equivalents of start and end
			Date startEventDate = null, endEventDate = null;
			
			//Attempt to convert start and end to Date variables
			try {
				startEventDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(start);
				endEventDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(end);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (startEventDate != null && endEventDate != null) {
				if (startEventDate.after(startDate) && endEventDate.before(endDate)) {
					eventArrayList.add(events[i]);
				}
			}
		}
		
		//Initialize JSONArray object to hold the events from eventArrayList
		JSONArray eventArray = new JSONArray();
		
		//Use for loop to put JSONObjects into the JSONArray
		for (int i = 0; i < eventArrayList.size(); i++) {
			eventArray.add(eventArrayList.get(i));
		}
		
		// Add the "Events" array to the main JSON object to be stored
		newEntry.put("events", eventArray);
		
		// Attempt to write the main JSON object to the storage document file
		try (FileWriter file = new FileWriter(outputFile)) {
			file.write(newEntry.toJSONString());
			file.close();
			System.out.println("Events written to output file: " + outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printScheduleRoom(JSONArray arguments) {
		// Initialize variables to be used as parameters to print events
		String roomID = "";
		String startString = "";
		String endString = "";
		String outputFile = "";
		
		// Iterator to go through the JSON objects (arguments) in the JSON array
		Iterator<JSONObject> iterator = arguments.iterator();
		while (iterator.hasNext()) {
			JSONObject theObject = iterator.next();
			String name = (String) theObject.get("name");
			String value = (String) theObject.get("value");
			if(name.equals("room-id")) {
				roomID = value;
			}
			else if(name.equals("start-date")) {
				startString = value;
			}
			else if(name.equals("end-date")) {
				endString = value;
			}
			else if(name.equals("output-file")) {
				outputFile = value;
			}
		}
		
		//Initialize variables to hold Date equivalents of startString and endString
		Date startDate = null, endDate = null;
		
		//Attempt to convert the startString and endString to Date variables
		try {
			startDate = new SimpleDateFormat("MMddyyyy").parse(startString);
			endDate = new SimpleDateFormat("MMddyyyy").parse(endString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// Initialize JSON object which will serve as new object to place in output file
		JSONObject newEntry = new JSONObject();
		
		// Initialize ArrayList that will store the meetings we need
		List<JSONObject> eventArrayList = new ArrayList<JSONObject>();
		
		//Retrieve an array of JSONObjects from the storage document
		JSONObject[] events = getEventsByRoom(roomID);
		
		//Check each entry in events array to ensure all start and end times fall
		//between those provided
		for (int i = 0; i < events.length; i++) {
			//Retrieve start and end date strings from events array
			String start = (String) events[i].get("start");
			String end = (String) events[i].get("end");
			
			//Initialize variables to hold Date equivalents of start and end
			Date startEventDate = null, endEventDate = null;
			
			//Attempt to convert start and end to Date variables
			try {
				startEventDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(start);
				endEventDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(end);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (startEventDate != null && endEventDate != null) {
				if (startEventDate.after(startDate) && endEventDate.before(endDate)) {
					eventArrayList.add(events[i]);
				}
			}
		}
		
		//Initialize JSONArray object to hold the events from eventArrayList
		JSONArray eventArray = new JSONArray();
		
		//Use for loop to put JSONObjects into the JSONArray
		for (int i = 0; i < eventArrayList.size(); i++) {
			eventArray.add(eventArrayList.get(i));
		}
		
		// Add the "Events" array to the main JSON object to be stored
		newEntry.put("events", eventArray);
		
		// Attempt to write the main JSON object to the storage document file
		try (FileWriter file = new FileWriter(outputFile)) {
			file.write(newEntry.toJSONString());
			file.close();
			System.out.println("Events written to output file: " + outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
