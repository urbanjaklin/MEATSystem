package meat.testsystem;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import meat.mainsystem.HandleJSON;
import meat.mainsystem.Holiday;
import meat.mainsystem.Meeting;
import meat.mainsystem.Vacation;
/*
 * The testCheckRoomAvailabilityInvalid() function is not passing the test currently.
 *  It seems to be a problem with matching the exact string expected with the actual
 *  output to the console from that method. 
 * In general the CheckRoomAvailability functions are very tricky because a meeting
 *  must already exist within the events.json to run the function properly.
 *  
 *  ^ I believe this is fixed now! :)
 *  And on another note, some of these tests have ended up being cross-unit tests (in
 *  order to reduce the amount of time it'd take for me to write them), though I know
 *  this isn't the most efficient/safest way to do proper isolation testing. I hope
 *  this is okay. 
 */

public class TestHandleJSON {
	
	/**
	 * This setUp() method will delete the system's current storage document in order
	 * to allow us to use and reference specific events being added and/or modified
	 * within the test cases.
	 */
	@Before
	public void setUp() {
		// Create a File object with the file location and name of MEAT's storage document
		File storage = new File("./resources/events.json");

		// Check for existence of the file at its specified location
		if (storage.exists() && !storage.isDirectory()) {
			storage.delete();
		}
		
		// Create a File object with the file location and name of MEAT's test storage document
		File testStorage = new File("./resources/testevents.json");

		// Check for existence of the file at its specified location
		if (testStorage.exists() && !testStorage.isDirectory()) {
			try {
				Files.copy( testStorage.toPath(), storage.toPath() );
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	@After
	public void tearDown() {
		// Create a File object with the file location and name of MEAT's storage document
		File storage = new File("./resources/events.json");

		// Check for existence of the file at its specified location
		if (storage.exists() && !storage.isDirectory()) {
			storage.delete();
		}
		
//		// Create a File object which references the print file made in the print tests
//		File testfile = new File("./testprint.txt");
//
//		// Check for existence of the file at its specified location
//		if (testfile.exists() && !testfile.isDirectory()) {
//			testfile.delete();
//		}	
	}
	
	@Test
	public void testAddMeeting() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		String startString = "2016.12.13 AD at 10:30:00 EST";
		String endString = "2016.12.13 AD at 11:00:00 EST";
		Date testStartTime = null; Date testEndTime = null;
		try {
			testStartTime = df.parse(startString);
			testEndTime = df.parse(endString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		String[] empArray = { "Employee" };
		Meeting meeting = new Meeting(10000001, "Test adding meeting", testStartTime, testEndTime, empArray, "3D15");
		
		//JSONObject to compare test object to
		JSONObject oracle = new JSONObject();
		JSONArray empMeeting = new JSONArray();
		
		//Populate the meeting object to go into the events array
		oracle.put("start", "Tue Dec 13 10:30:00 EST 2016");
		empMeeting.add("Employee");
		oracle.put("emp", empMeeting);
		oracle.put("end", "Tue Dec 13 11:00:00 EST 2016");
		oracle.put("id", 10000001);
		oracle.put("room", "3D15");
		oracle.put("desc", "Test adding meeting");
		
		handleJSON.addMeeting(meeting);
		
		JSONObject object = handleJSON.getEventByID(10000001);
		
		assertEquals(oracle.toString(), object.toString());
	}
	
	@Test
	public void testAddMeetingNoEvents() {
		// Create a File object with the file location and name of MEAT's storage document
		File storage = new File("./resources/events.json");

		// Check for existence of the file at its specified location
		if (storage.exists() && !storage.isDirectory()) {
			storage.delete();
		}
		
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		String startString = "2016.12.13 AD at 10:30:00 EST";
		String endString = "2016.12.13 AD at 11:00:00 EST";
		Date testStartTime = null; Date testEndTime = null;
		try {
			testStartTime = df.parse(startString);
			testEndTime = df.parse(endString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		String[] empArray = { "Employee" };
		Meeting meeting = new Meeting(10000001, "Test adding meeting", testStartTime, testEndTime, empArray, "3D15");
		
		//JSONObject to compare test object to
		JSONObject oracle = new JSONObject();
		JSONArray empMeeting = new JSONArray();
		
		//Populate the meeting object to go into the events array
		oracle.put("start", "Tue Dec 13 10:30:00 EST 2016");
		empMeeting.add("Employee");
		oracle.put("emp", empMeeting);
		oracle.put("end", "Tue Dec 13 11:00:00 EST 2016");
		oracle.put("id", 10000001);
		oracle.put("room", "3D15");
		oracle.put("desc", "Test adding meeting");
		
		handleJSON.addMeeting(meeting);
		
		JSONObject object = handleJSON.getEventByID(10000001);
		
		assertEquals(oracle.toString(), object.toString());
	}
	
	@Test
	public void testAddVacation() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		String startString = "2016.12.13 AD at 00:00:00 EST";
		String endString = "2016.12.20 AD at 00:00:00 EST";
		Date testStartTime = null; Date testEndTime = null;
		try {
			testStartTime = df.parse(startString);
			testEndTime = df.parse(endString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		String[] empArray = { "Employee" };
		Vacation vacation = new Vacation(20000001, "Test adding vacation", testStartTime, testEndTime, empArray);
		
		//JSONObject to compare test object to
		JSONObject oracle = new JSONObject();
		JSONArray empMeeting = new JSONArray();
		
		//Populate the meeting object to go into the events array
		oracle.put("start", "Tue Dec 13 00:00:00 EST 2016");
		empMeeting.add("Employee");
		oracle.put("emp", empMeeting);
		oracle.put("end", "Tue Dec 20 00:00:00 EST 2016");
		oracle.put("id", 20000001);
		oracle.put("room", "");
		oracle.put("desc", "Test adding vacation");
		
		handleJSON.addVacation(vacation);
		
		JSONObject object = handleJSON.getEventByID(20000001);
		
		assertEquals(oracle.toString(), object.toString());
	}
	
	@Test
	public void testAddVacationNoEvents() {
		// Create a File object with the file location and name of MEAT's storage document
		File storage = new File("./resources/events.json");

		// Check for existence of the file at its specified location
		if (storage.exists() && !storage.isDirectory()) {
			storage.delete();
		}
		
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		String startString = "2016.12.13 AD at 00:00:00 EST";
		String endString = "2016.12.20 AD at 00:00:00 EST";
		Date testStartTime = null; Date testEndTime = null;
		try {
			testStartTime = df.parse(startString);
			testEndTime = df.parse(endString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		String[] empArray = { "Employee" };
		Vacation vacation = new Vacation(20000001, "Test adding vacation", testStartTime, testEndTime, empArray);
		
		//JSONObject to compare test object to
		JSONObject oracle = new JSONObject();
		JSONArray empMeeting = new JSONArray();
		
		//Populate the meeting object to go into the events array
		oracle.put("start", "Tue Dec 13 00:00:00 EST 2016");
		empMeeting.add("Employee");
		oracle.put("emp", empMeeting);
		oracle.put("end", "Tue Dec 20 00:00:00 EST 2016");
		oracle.put("id", 20000001);
		oracle.put("room", "");
		oracle.put("desc", "Test adding vacation");
		
		handleJSON.addVacation(vacation);
		
		JSONObject object = handleJSON.getEventByID(20000001);
		
		assertEquals(oracle.toString(), object.toString());
	}
	
	@Test
	public void testAddHoliday() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		String startString = "2016.12.13 AD at 00:00:00 EST";
		String endString = "2016.12.20 AD at 00:00:00 EST";
		Date testStartTime = null; Date testEndTime = null;
		try {
			testStartTime = df.parse(startString);
			testEndTime = df.parse(endString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		String[] empArray = { "Employee" };
		Holiday holiday = new Holiday(30000001, "Test adding holiday", testStartTime, testEndTime, empArray);
		
		//JSONObject to compare test object to
		JSONObject oracle = new JSONObject();
		JSONArray empMeeting = new JSONArray();
		
		//Populate the meeting object to go into the events array
		oracle.put("start", "Tue Dec 13 00:00:00 EST 2016");
		empMeeting.add("Employee");
		oracle.put("emp", empMeeting);
		oracle.put("end", "Tue Dec 20 00:00:00 EST 2016");
		oracle.put("id", 30000001);
		oracle.put("room", "");
		oracle.put("desc", "Test adding holiday");
		
		handleJSON.addHoliday(holiday);
		
		JSONObject object = handleJSON.getEventByID(30000001);
		
		assertEquals(oracle.toString(), object.toString());
	}
	
	@Test
	public void testAddHolidayNoEvents() {
		// Create a File object with the file location and name of MEAT's storage document
		File storage = new File("./resources/events.json");

		// Check for existence of the file at its specified location
		if (storage.exists() && !storage.isDirectory()) {
			storage.delete();
		}
		
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		String startString = "2016.12.13 AD at 00:00:00 EST";
		String endString = "2016.12.20 AD at 00:00:00 EST";
		Date testStartTime = null; Date testEndTime = null;
		try {
			testStartTime = df.parse(startString);
			testEndTime = df.parse(endString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		String[] empArray = { "Employee" };
		Holiday holiday = new Holiday(30000001, "Test adding holiday", testStartTime, testEndTime, empArray);
		
		//JSONObject to compare test object to
		JSONObject oracle = new JSONObject();
		JSONArray empMeeting = new JSONArray();
		
		//Populate the meeting object to go into the events array
		oracle.put("start", "Tue Dec 13 00:00:00 EST 2016");
		empMeeting.add("Employee");
		oracle.put("emp", empMeeting);
		oracle.put("end", "Tue Dec 20 00:00:00 EST 2016");
		oracle.put("id", 30000001);
		oracle.put("room", "");
		oracle.put("desc", "Test adding holiday");
		
		handleJSON.addHoliday(holiday);
		
		JSONObject object = handleJSON.getEventByID(30000001);
		
		assertEquals(oracle.toString(), object.toString());
	}

	@Test
	public void testCheckRoomAvailablityInvalid() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	     		
	    String testRoom = "47L2";
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		String startString = "2016.12.13 AD at 10:30:00 EST";
		String endString = "2016.12.13 AD at 11:00:00 EST";
		Date testStartTime = null; Date testEndTime = null;
		try {
			testStartTime = df.parse(startString);
			testEndTime = df.parse(endString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		handleJSON.checkRoomAvailability(testRoom, testStartTime, testEndTime);	
		
		String oracle = "Warning: New event falls within range of another event in the same room";
//		System.out.println(outContent.toString());
		assertEquals(oracle, outContent.toString().trim());
		
	}

	@Test
	public void testCheckRoomAvailablityValid() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	     		
	    String testRoom = "5F62";
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		String startString = "2017.12.13 AD at 10:30:00 EST";
		String endString = "2017.12.13 AD at 11:00:00 EST";
		Date testStartTime = null; Date testEndTime = null;
		try {
			testStartTime = df.parse(startString);
			testEndTime = df.parse(endString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		handleJSON.checkRoomAvailability(testRoom, testStartTime, testEndTime);	

		String oracle = "";
		assertEquals(oracle, outContent.toString());
		
	}

	@Test
	public void testGetMeetingCount(){
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		
		long test = handleJSON.getMeetingCount();

		JSONObject objectInFile = handleJSON.readFile("./resources/events.json");
		long oracle = (long) objectInFile.get("MeetingCount");

		assertEquals(oracle, test);
		
	}
	
	@Test
	public void testGetVacationCount(){
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		
		long test = handleJSON.getVacationCount();

		JSONObject objectInFile = handleJSON.readFile("./resources/events.json");
		long oracle = (long) objectInFile.get("VacationCount");

		assertEquals(oracle, test);
		
	}
	
	@Test
	public void testGetHolidayCount(){
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		
		long test = handleJSON.getHolidayCount();

		JSONObject objectInFile = handleJSON.readFile("./resources/events.json");
		long oracle = (long) objectInFile.get("HolidayCount");

		assertEquals(oracle, test);
		
	}
	
	@Test
	public void testReadFile() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		//JSONObject to compare test object to
		JSONObject oracle = new JSONObject();
		//Events array to be placed in oracle
		JSONArray events = new JSONArray();
		JSONObject meeting = new JSONObject();
		JSONArray empMeeting = new JSONArray();
		JSONObject vacation = new JSONObject();
		JSONArray empVacation = new JSONArray();
		JSONObject holiday = new JSONObject();
		JSONArray empHoliday = new JSONArray();
		
		JSONObject objectInFile = handleJSON.readFile("./resources/events.json");
		
		//Add known meeting and vacation counts to oracle
		oracle.put("MeetingCount", 10000000);
		oracle.put("VacationCount", 20000000);
		
		//Populate the meeting object to go into the events array
		meeting.put("start", "Tue Dec 13 10:30:00 EST 2016");
		empMeeting.add("Employee");
		meeting.put("emp", empMeeting);
		meeting.put("end", "Tue Dec 13 11:00:00 EST 2016");
		meeting.put("id", 10000000);
		meeting.put("room", "47L2");
		meeting.put("desc", "Meeting");
		
		//Populate the vacation object to go into the events array
		vacation.put("start", "Wed Nov 23 00:00:00 EST 2016");
		empVacation.add("Employee");
		vacation.put("emp", empVacation);
		vacation.put("end", "Sun Nov 27 00:00:00 EST 2016");
		vacation.put("id", 20000000);
		vacation.put("room", "");
		vacation.put("desc", "Vacation");
		
		//Populate the vacation object to go into the events array
		holiday.put("start", "Thu Nov 24 00:00:00 EST 2016");
		empHoliday.add("company");
		holiday.put("emp", empHoliday);
		holiday.put("end", "Fri Nov 25 00:00:00 EST 2016");
		holiday.put("id", 30000000);
		holiday.put("room", "");
		holiday.put("desc", "Holiday");
		
		//Add events to events JSONAray
		events.add(meeting);
		events.add(vacation);
		events.add(holiday);
		
		//Add events array to oracle
		oracle.put("Events", events);
		
		//Add known holiday count to oracle
		oracle.put("HolidayCount", 30000000);
		
		assertEquals(oracle.toString(), objectInFile.toString());
	}
	
	@Test
	public void testReadFileNoFile() {
		// Create a File object with the file location and name of MEAT's storage document
		File storage = new File("./resources/events.json");

		// Check for existence of the file at its specified location
		if (storage.exists() && !storage.isDirectory()) {
			storage.delete();
		}
		
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		//JSONObject to compare test object to
		String oracle = "Error: Storage file not found";

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
		
	    handleJSON.readFile("./resources/events.json");
	    
		assertEquals(oracle, outContent.toString().trim());
	}
	
	@Test
	public void testGetEventsByEmployee() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		//JSONObject to compare test object to
		JSONObject[] oracle = new JSONObject[2];
		//Events objects to be placed in oracle and their corresponding emp arrays
		JSONObject meeting = new JSONObject();
		JSONArray empMeeting = new JSONArray();
		JSONObject vacation = new JSONObject();
		JSONArray empVacation = new JSONArray();
		
		JSONObject[] objectArrayInFile = handleJSON.getEventsByEmployee("Employee");
		
		//Populate the meeting object to go into the events array
		meeting.put("start", "Tue Dec 13 10:30:00 EST 2016");
		empMeeting.add("Employee");
		meeting.put("emp", empMeeting);
		meeting.put("end", "Tue Dec 13 11:00:00 EST 2016");
		meeting.put("id", 10000000);
		meeting.put("room", "47L2");
		meeting.put("desc", "Meeting");
		
		//Populate the vacation object to go into the events array
		vacation.put("start", "Wed Nov 23 00:00:00 EST 2016");
		empVacation.add("Employee");
		vacation.put("emp", empVacation);
		vacation.put("end", "Sun Nov 27 00:00:00 EST 2016");
		vacation.put("id", 20000000);
		vacation.put("room", "");
		vacation.put("desc", "Vacation");
		

		
		//Add events to events JSONAray
		oracle[0] = meeting;
		oracle[1] = vacation;
		
		for(int i = 0; i < 2; i++) {
			assertEquals(oracle[i].toString(), objectArrayInFile[i].toString());
		}
	}
	
	@Test
	public void testGetEventsByRoom() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		//JSONObject to compare test object to
		JSONObject[] oracle = new JSONObject[2];
		//Events objects to be placed in oracle and their corresponding emp arrays
		JSONObject meeting = new JSONObject();
		JSONArray empMeeting = new JSONArray();
		
		JSONObject[] objectArrayInFile = handleJSON.getEventsByRoom("47L2");
		
		//Populate the meeting object to go into the events array
		meeting.put("start", "Tue Dec 13 10:30:00 EST 2016");
		empMeeting.add("Employee");
		meeting.put("emp", empMeeting);
		meeting.put("end", "Tue Dec 13 11:00:00 EST 2016");
		meeting.put("id", 10000000);
		meeting.put("room", "47L2");
		meeting.put("desc", "Meeting");
		
		//Add events to events JSONAray
		oracle[0] = meeting;
		
		assertEquals(oracle[0].toString(), objectArrayInFile[0].toString());
	}
	
	@Test
	public void testGetEventById() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		//JSONObject to compare test object to
		JSONObject oracle = new JSONObject();
		JSONArray empMeeting = new JSONArray();
		
		JSONObject objectInFile = handleJSON.getEventByID(10000000);
		
		//Populate the meeting object to go into the events array
		oracle.put("start", "Tue Dec 13 10:30:00 EST 2016");
		empMeeting.add("Employee");
		oracle.put("emp", empMeeting);
		oracle.put("end", "Tue Dec 13 11:00:00 EST 2016");
		oracle.put("id", 10000000);
		oracle.put("room", "47L2");
		oracle.put("desc", "Meeting");
		
		assertEquals(oracle.toString(), objectInFile.toString());
	}
	
	@Test
	public void testGetIndexOfEventById() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		//int to compare test object to
		int oracle = 0;
		
		int indexOfEvent = handleJSON.getIndexOfEventByID(10000000);
		
		assertEquals(oracle, indexOfEvent);
	}
	
	@Test
	public void testGetAllEvents() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		//JSONObject to compare test object to
		JSONObject[] oracle = new JSONObject[3];
		//Events objects to be placed in oracle and their corresponding emp arrays
		JSONObject meeting = new JSONObject();
		JSONArray empMeeting = new JSONArray();
		JSONObject vacation = new JSONObject();
		JSONArray empVacation = new JSONArray();
		JSONObject holiday = new JSONObject();
		JSONArray empHoliday = new JSONArray();
		
		JSONObject[] objectArrayInFile = handleJSON.getAllEvents();
		
		//Populate the meeting object to go into the events array
		meeting.put("start", "Tue Dec 13 10:30:00 EST 2016");
		empMeeting.add("Employee");
		meeting.put("emp", empMeeting);
		meeting.put("end", "Tue Dec 13 11:00:00 EST 2016");
		meeting.put("id", 10000000);
		meeting.put("room", "47L2");
		meeting.put("desc", "Meeting");
		
		//Populate the vacation object to go into the events array
		vacation.put("start", "Wed Nov 23 00:00:00 EST 2016");
		empVacation.add("Employee");
		vacation.put("emp", empVacation);
		vacation.put("end", "Sun Nov 27 00:00:00 EST 2016");
		vacation.put("id", 20000000);
		vacation.put("room", "");
		vacation.put("desc", "Vacation");
		
		//Populate the vacation object to go into the events array
		holiday.put("start", "Thu Nov 24 00:00:00 EST 2016");
		empHoliday.add("company");
		holiday.put("emp", empHoliday);
		holiday.put("end", "Fri Nov 25 00:00:00 EST 2016");
		holiday.put("id", 30000000);
		holiday.put("room", "");
		holiday.put("desc", "Holiday");
		
		//Add events to oracle
		oracle[0] = meeting;
		oracle[1] = vacation;
		oracle[2] = holiday;
		
		for(int i = 0; i < 2; i++) {
			assertEquals(oracle[i].toString(), objectArrayInFile[i].toString());
		}
	}
	
	@Test
	public void testRetrieveEmployees() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		//JSONObject to compare test object to
		String[] oracle = new String[9];
		
		String[] employees = handleJSON.retrieveEmployees();
		
		//Add events to oracle
		oracle[0] = "Luan Nguyen nguy0621";
		oracle[1] = "Gregory Gay gayxx067";
		oracle[2] = "Robert Bob bob099";
		oracle[3] = "Georganne Tolaas tolas9999";
		oracle[4] = "John Smith smith0001";
		oracle[5] = "Ian De Silva desil1337";
		oracle[6] = "Urban Jaklin jakline";
		oracle[7] = "Jessica Anderson jea2";
		oracle[8] = "Natasha Delahunt delahun2";
		
		assertArrayEquals(oracle, employees);
	}
	
	@Test
	public void testRetrieveRooms() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		//JSONObject to compare test object to
		String[] oracle = new String[9];
		
		String[] rooms = handleJSON.retrieveRooms();
		
		//Add events to oracle
		oracle[0] = "Swearingen 3D15";
		oracle[1] = "Swearingen 3A66";
		oracle[2] = "Swearingen 2A07";
		oracle[3] = "Swearingen 3D75";
		oracle[4] = "Sumwalt 222";
		oracle[5] = "Swearingen Amoco Hall";
		oracle[6] = "Sumwalt 361";
		oracle[7] = "Sumwalt 340";
		oracle[8] = "Sumwalt 244";
		
		assertArrayEquals(oracle, rooms);
	}
	
	@Test
	public void testExecuteScript() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		
		//JSONObject to compare test object to
		JSONObject oracle = new JSONObject();
		JSONArray empMeeting = new JSONArray();
		
		//Populate the meeting object to go into the events array
		oracle.put("start", "Tue Dec 06 09:30:00 EST 2016");
		empMeeting.add("Employee");
		oracle.put("emp", empMeeting);
		oracle.put("end", "Tue Dec 06 11:00:00 EST 2016");
		oracle.put("id", 10000001);
		oracle.put("room", "3A66");
		oracle.put("desc", "Adding a new meeting from script.");
		
		handleJSON.executeScript("./resources/testscriptfile.json");
		
		JSONObject object = handleJSON.getEventByID(10000001);
		
		assertEquals(oracle.toString(), object.toString());
	}
	
	@Test
	public void testAddMeetingFromScript() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		
		//JSONObject to compare test object to
		JSONObject oracle = new JSONObject();
		JSONArray empMeeting = new JSONArray();
		
		//Initialize arguments array
		JSONArray arguments = new JSONArray();
		//Initialize objects and set names and values to fill arguments array
		JSONObject date = new JSONObject();
		date.put("name", "date");
		date.put("value", "12062016");
		JSONObject startTime = new JSONObject();
		startTime.put("name", "start-time");
		startTime.put("value", "09:30");
		JSONObject endTime = new JSONObject();
		endTime.put("name", "end-time");
		endTime.put("value", "11:00");
		JSONObject roomId = new JSONObject();
		roomId.put("name", "room-id");
		roomId.put("value", "3A66");
		JSONObject attendee = new JSONObject();
		attendee.put("name", "attendee");
		attendee.put("value", "Employee");
		JSONObject description = new JSONObject();
		description.put("name", "description");
		description.put("value", "Adding a new meeting from script.");
		//Add JSONObjects to JSONArray
		arguments.add(date);
		arguments.add(startTime);
		arguments.add(endTime);
		arguments.add(roomId);
		arguments.add(attendee);
		arguments.add(description);
				
		//Populate the meeting object to go into the events array
		oracle.put("start", "Tue Dec 06 09:30:00 EST 2016");
		empMeeting.add("Employee");
		oracle.put("emp", empMeeting);
		oracle.put("end", "Tue Dec 06 11:00:00 EST 2016");
		oracle.put("id", 10000001);
		oracle.put("room", "3A66");
		oracle.put("desc", "Adding a new meeting from script.");
		
		handleJSON.addMeetingFromScript(arguments);
		
		JSONObject object = handleJSON.getEventByID(10000001);
		
		assertEquals(oracle.toString(), object.toString());
	}
	
	@Test
	public void testAddVacationFromScript() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		
		//JSONObject to compare test object to
		JSONObject oracle = new JSONObject();
		JSONArray empVacation = new JSONArray();
		
		//Initialize arguments array
		JSONArray arguments = new JSONArray();
		//Initialize objects and set names and values to fill arguments array
		JSONObject startDate = new JSONObject();
		startDate.put("name", "start-date");
		startDate.put("value", "12132016");
		JSONObject endDate = new JSONObject();
		endDate.put("name", "end-date");
		endDate.put("value", "01032017");
		JSONObject employeeID = new JSONObject();
		employeeID.put("name", "employee-id");
		employeeID.put("value", "Employee");
		JSONObject description = new JSONObject();
		description.put("name", "description");
		description.put("value", "Adding a new vacation from script.");
		//Add JSONObjects to JSONArray
		arguments.add(startDate);
		arguments.add(endDate);
		arguments.add(employeeID);
		arguments.add(description);
				
		//Populate the meeting object to go into the events array
		oracle.put("start", "Tue Dec 13 00:00:00 EST 2016");
		empVacation.add("Employee");
		oracle.put("emp", empVacation);
		oracle.put("end", "Tue Jan 03 00:00:00 EST 2017");
		oracle.put("id", 20000001);
		oracle.put("room", "");
		oracle.put("desc", "Adding a new vacation from script.");
		
		handleJSON.addVacationFromScript(arguments);
		
		JSONObject object = handleJSON.getEventByID(20000001);
		
		assertEquals(oracle.toString(), object.toString());
	}
	
	@Test
	public void testAddHolidayFromScript() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		
		//JSONObject to compare test object to
		JSONObject oracle = new JSONObject();
		JSONArray empHoliday = new JSONArray();
		
		//Initialize arguments array
		JSONArray arguments = new JSONArray();
		//Initialize objects and set names and values to fill arguments array
		JSONObject startDate = new JSONObject();
		startDate.put("name", "start-date");
		startDate.put("value", "12132016");
		JSONObject endDate = new JSONObject();
		endDate.put("name", "end-date");
		endDate.put("value", "01032017");
		JSONObject description = new JSONObject();
		description.put("name", "description");
		description.put("value", "Adding a new holiday from script.");
		//Add JSONObjects to JSONArray
		arguments.add(startDate);
		arguments.add(endDate);
		arguments.add(description);
				
		//Populate the meeting object to go into the events array
		oracle.put("start", "Tue Dec 13 00:00:00 EST 2016");
		empHoliday.add("company");
		oracle.put("emp", empHoliday);
		oracle.put("end", "Tue Jan 03 00:00:00 EST 2017");
		oracle.put("id", 30000001);
		oracle.put("room", "");
		oracle.put("desc", "Adding a new holiday from script.");
		
		handleJSON.addHolidayFromScript(arguments);
		
		JSONObject object = handleJSON.getEventByID(30000001);
		
		assertEquals(oracle.toString(), object.toString());
	}
	
	@Test
	public void testEditMeetingFromScript() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		
		//JSONObject to compare test object to
		JSONObject oracle = new JSONObject();
		JSONArray empMeeting = new JSONArray();
		
		//Initialize arguments array
		JSONArray arguments = new JSONArray();
		//Initialize objects and set names and values to fill arguments array
		JSONObject meetingID = new JSONObject();
		meetingID.put("name", "meeting-id");
		meetingID.put("value", "10000000");
		JSONObject date = new JSONObject();
		date.put("name", "date");
		date.put("value", "12062016");
		JSONObject startTime = new JSONObject();
		startTime.put("name", "start-time");
		startTime.put("value", "11:30");
		JSONObject endTime = new JSONObject();
		endTime.put("name", "end-time");
		endTime.put("value", "13:00");
		JSONObject roomId = new JSONObject();
		roomId.put("name", "room-id");
		roomId.put("value", "3D15");
		JSONObject description = new JSONObject();
		description.put("name", "description");
		description.put("value", "Editing a meeting from script.");
		//Add JSONObjects to JSONArray
		arguments.add(meetingID);
		arguments.add(date);
		arguments.add(startTime);
		arguments.add(endTime);
		arguments.add(roomId);
		arguments.add(description);
				
		//Populate the meeting object to go into the events array
		oracle.put("start", "Tue Dec 06 11:30:00 EST 2016");
		empMeeting.add("Employee");
		oracle.put("emp", empMeeting);
		oracle.put("end", "Tue Dec 06 13:00:00 EST 2016");
		oracle.put("id", 10000000);
		oracle.put("room", "3D15");
		oracle.put("desc", "Editing a meeting from script.");
		
		handleJSON.editMeetingFromScript(arguments);
		
		JSONObject object = handleJSON.getEventByID(10000000);
		
		assertEquals(oracle.toString(), object.toString());
	}
	
	@Test
	public void testEditMeetingFromScriptInvalidID() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
		String oracle = "Error: Meeting was not found by the specified ID.";
		
		//Initialize arguments array
		JSONArray arguments = new JSONArray();
		//Initialize objects and set names and values to fill arguments array
		JSONObject meetingID = new JSONObject();
		meetingID.put("name", "meeting-id");
		meetingID.put("value", "19999999");
		JSONObject date = new JSONObject();
		date.put("name", "date");
		date.put("value", "12062016");
		JSONObject startTime = new JSONObject();
		startTime.put("name", "start-time");
		startTime.put("value", "11:30");
		JSONObject endTime = new JSONObject();
		endTime.put("name", "end-time");
		endTime.put("value", "13:00");
		JSONObject roomId = new JSONObject();
		roomId.put("name", "room-id");
		roomId.put("value", "3D15");
		JSONObject description = new JSONObject();
		description.put("name", "description");
		description.put("value", "Editing a meeting from script.");
		//Add JSONObjects to JSONArray
		arguments.add(meetingID);
		arguments.add(date);
		arguments.add(startTime);
		arguments.add(endTime);
		arguments.add(roomId);
		arguments.add(description);
		
		handleJSON.editMeetingFromScript(arguments);
				
		assertEquals(oracle, outContent.toString().trim());
	}
	
	@Test
	public void testEditMeetingAddAttendees() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		
		//JSONObject to compare test object to
		JSONObject oracle = new JSONObject();
		JSONArray empMeeting = new JSONArray();
		
		//Initialize arguments array
		JSONArray arguments = new JSONArray();
		//Initialize objects and set names and values to fill arguments array
		JSONObject meetingID = new JSONObject();
		meetingID.put("name", "meeting-id");
		meetingID.put("value", "10000000");
		JSONObject attendee = new JSONObject();
		attendee.put("name", "attendee");
		attendee.put("value", "DiffEmployee");
		//Add JSONObjects to JSONArray
		arguments.add(meetingID);
		arguments.add(attendee);
				
		//Populate the meeting object to be tested against
		oracle.put("start", "Tue Dec 13 10:30:00 EST 2016");
		empMeeting.add("Employee");
		empMeeting.add("DiffEmployee");
		oracle.put("emp", empMeeting);
		oracle.put("end", "Tue Dec 13 11:00:00 EST 2016");
		oracle.put("id", 10000000);
		oracle.put("room", "47L2");
		oracle.put("desc", "Meeting");
		
		handleJSON.editMeetingAddAttendees(arguments);
		
		JSONObject object = handleJSON.getEventByID(10000000);
		
		assertEquals(oracle.toString(), object.toString());
	}
	
	@Test
	public void testEditMeetingRemoveAttendees() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		
		//JSONObject to compare test object to
		JSONObject oracle = new JSONObject();
		JSONArray empMeeting = new JSONArray();
		
		//Initialize arguments array
		JSONArray arguments = new JSONArray();
		//Initialize objects and set names and values to fill arguments array
		JSONObject meetingID = new JSONObject();
		meetingID.put("name", "meeting-id");
		meetingID.put("value", "10000000");
		JSONObject attendee = new JSONObject();
		attendee.put("name", "attendee");
		attendee.put("value", "Employee");
		//Add JSONObjects to JSONArray
		arguments.add(meetingID);
		arguments.add(attendee);
				
		//Populate the meeting object to be tested against
		oracle.put("start", "Tue Dec 13 10:30:00 EST 2016");
		oracle.put("emp", empMeeting);
		oracle.put("end", "Tue Dec 13 11:00:00 EST 2016");
		oracle.put("id", 10000000);
		oracle.put("room", "47L2");
		oracle.put("desc", "Meeting");
		
		handleJSON.editMeetingRemoveAttendees(arguments);
		
		JSONObject object = handleJSON.getEventByID(10000000);
		
		assertEquals(oracle.toString(), object.toString());
	}
	
	@Test
	public void testDeleteMeetingFromScript() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		
		//JSONObject to compare test object to
		JSONObject[] oracle = new JSONObject[2];
		//Events objects to be placed in oracle and their corresponding emp arrays
		JSONObject vacation = new JSONObject();
		JSONArray empVacation = new JSONArray();
		JSONObject holiday = new JSONObject();
		JSONArray empHoliday = new JSONArray();
		
		//Initialize arguments array
		JSONArray arguments = new JSONArray();
		
		//Initialize objects and set names and values to fill arguments array
		JSONObject meetingID = new JSONObject();
		meetingID.put("name", "meeting-id");
		meetingID.put("value", "10000000");
		
		//Add JSONObjects to JSONArray
		arguments.add(meetingID);
				
		//Populate the vacation object to go into the events array
		vacation.put("start", "Wed Nov 23 00:00:00 EST 2016");
		empVacation.add("Employee");
		vacation.put("emp", empVacation);
		vacation.put("end", "Sun Nov 27 00:00:00 EST 2016");
		vacation.put("id", 20000000);
		vacation.put("room", "");
		vacation.put("desc", "Vacation");
		
		//Populate the vacation object to go into the events array
		holiday.put("start", "Thu Nov 24 00:00:00 EST 2016");
		empHoliday.add("company");
		holiday.put("emp", empHoliday);
		holiday.put("end", "Fri Nov 25 00:00:00 EST 2016");
		holiday.put("id", 30000000);
		holiday.put("room", "");
		holiday.put("desc", "Holiday");
		
		//Add events to oracle
		oracle[0] = vacation;
		oracle[1] = holiday;
		
		handleJSON.deleteMeetingFromScript(arguments);
		JSONObject[] objectArrayInFile = handleJSON.getAllEvents();
		
		JSONObject object = handleJSON.getEventByID(10000000);
		
		for(int i = 0; i < 2; i++) {
			assertEquals(oracle[i].toString(), objectArrayInFile[i].toString());
		}
	}
	
	@Test
	public void testPrintScheduleAll() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		File outputTxt = new File("./testprint.txt");
		
		//JSONObject to compare test object to
		JSONObject objectToFile = new JSONObject();
		//JSONArray to store array of JSONObject events
		JSONArray events = new JSONArray();
		//Text file to compare against actual result
		File oracleTxt = new File("./oracle.txt");
		
		//Events objects to be placed in objectToFile and their corresponding emp arrays
		JSONObject vacation = new JSONObject();
		JSONArray empVacation = new JSONArray();
		JSONObject holiday = new JSONObject();
		JSONArray empHoliday = new JSONArray();
		
		//Initialize arguments array
		JSONArray arguments = new JSONArray();
		
		//Initialize objects and set names and values to fill arguments array
		JSONObject startDate = new JSONObject();
		startDate.put("name", "start-date");
		startDate.put("value", "11222016");
		JSONObject endDate = new JSONObject();
		endDate.put("name", "end-date");
		endDate.put("value", "11302016");
		JSONObject outputFile = new JSONObject();
		outputFile.put("name", "output-file");
		outputFile.put("value", "./testprint.txt");
		
		//Add JSONObjects to JSONArray
		arguments.add(startDate);
		arguments.add(endDate);
		arguments.add(outputFile);
				
		//Populate the vacation object to go into the events array
		vacation.put("start", "Wed Nov 23 00:00:00 EST 2016");
		empVacation.add("Employee");
		vacation.put("emp", empVacation);
		vacation.put("end", "Sun Nov 27 00:00:00 EST 2016");
		vacation.put("id", 20000000);
		vacation.put("room", "");
		vacation.put("desc", "Vacation");
		
		//Populate the vacation object to go into the events array
		holiday.put("start", "Thu Nov 24 00:00:00 EST 2016");
		empHoliday.add("company");
		holiday.put("emp", empHoliday);
		holiday.put("end", "Fri Nov 25 00:00:00 EST 2016");
		holiday.put("id", 30000000);
		holiday.put("room", "");
		holiday.put("desc", "Holiday");
		
		//Add events to events JSONArray
		events.add(vacation);
		events.add(holiday);
		
		//Add events JSONArray to objectToFile
		objectToFile.put("events", events);
		
		// Attempt to write objectToFile to an oracle txt file
		try (FileWriter file = new FileWriter(oracleTxt)) {
			file.write(objectToFile.toJSONString());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		handleJSON.printScheduleAll(arguments);
		
		boolean test = false;
		try {
			test = FileUtils.contentEquals(oracleTxt, outputTxt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(test);
	}
	
	@Test
	public void testPrintScheduleEmployee() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		File outputTxt = new File("./testprint.txt");
		
		//JSONObject to compare test object to
		JSONObject objectToFile = new JSONObject();
		//JSONArray to store array of JSONObject events
		JSONArray events = new JSONArray();
		//Text file to compare against actual result
		File oracleTxt = new File("./oracle.txt");
		
		//Events objects to be placed in objectToFile and their corresponding emp arrays
		JSONObject meeting = new JSONObject();
		JSONArray empMeeting = new JSONArray();
		JSONObject vacation = new JSONObject();
		JSONArray empVacation = new JSONArray();
		
		//Initialize arguments array
		JSONArray arguments = new JSONArray();
		
		//Initialize objects and set names and values to fill arguments array
		JSONObject employeeID = new JSONObject();
		employeeID.put("name", "employee-id");
		employeeID.put("value", "Employee");
		JSONObject startDate = new JSONObject();
		startDate.put("name", "start-date");
		startDate.put("value", "11222016");
		JSONObject endDate = new JSONObject();
		endDate.put("name", "end-date");
		endDate.put("value", "12142016");
		JSONObject outputFile = new JSONObject();
		outputFile.put("name", "output-file");
		outputFile.put("value", "./testprint.txt");
		
		//Add JSONObjects to JSONArray
		arguments.add(employeeID);
		arguments.add(startDate);
		arguments.add(endDate);
		arguments.add(outputFile);
			
		//Populate the meeting object to go into the events array
		meeting.put("start", "Tue Dec 13 10:30:00 EST 2016");
		empMeeting.add("Employee");
		meeting.put("emp", empMeeting);
		meeting.put("end", "Tue Dec 13 11:00:00 EST 2016");
		meeting.put("id", 10000000);
		meeting.put("room", "47L2");
		meeting.put("desc", "Meeting");
		
		//Populate the vacation object to go into the events array
		vacation.put("start", "Wed Nov 23 00:00:00 EST 2016");
		empVacation.add("Employee");
		vacation.put("emp", empVacation);
		vacation.put("end", "Sun Nov 27 00:00:00 EST 2016");
		vacation.put("id", 20000000);
		vacation.put("room", "");
		vacation.put("desc", "Vacation");
		
		//Add events to events JSONArray
		events.add(meeting);
		events.add(vacation);
		
		//Add events JSONArray to objectToFile
		objectToFile.put("events", events);
		
		// Attempt to write objectToFile to an oracle txt file
		try (FileWriter file = new FileWriter(oracleTxt)) {
			file.write(objectToFile.toJSONString());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		handleJSON.printScheduleEmployee(arguments);
				
		boolean test = false;
		try {
			test = FileUtils.contentEquals(oracleTxt, outputTxt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(test);
	}
	
	@Test
	public void testPrintScheduleRoom() {
		//Initialize
		HandleJSON handleJSON = new HandleJSON();
		File outputTxt = new File("./testprint.txt");
		
		//JSONObject to compare test object to
		JSONObject objectToFile = new JSONObject();
		//JSONArray to store array of JSONObject events
		JSONArray events = new JSONArray();
		//Text file to compare against actual result
		File oracleTxt = new File("./oracle.txt");
		
		//Events objects to be placed in objectToFile and their corresponding emp arrays
		JSONObject meeting = new JSONObject();
		JSONArray empMeeting = new JSONArray();
		
		//Initialize arguments array
		JSONArray arguments = new JSONArray();
		
		//Initialize objects and set names and values to fill arguments array
		JSONObject employeeID = new JSONObject();
		employeeID.put("name", "room-id");
		employeeID.put("value", "47L2");
		JSONObject startDate = new JSONObject();
		startDate.put("name", "start-date");
		startDate.put("value", "12122016");
		JSONObject endDate = new JSONObject();
		endDate.put("name", "end-date");
		endDate.put("value", "12142016");
		JSONObject outputFile = new JSONObject();
		outputFile.put("name", "output-file");
		outputFile.put("value", "./testprint.txt");
		
		//Add JSONObjects to JSONArray
		arguments.add(employeeID);
		arguments.add(startDate);
		arguments.add(endDate);
		arguments.add(outputFile);
			
		//Populate the meeting object to go into the events array
		meeting.put("start", "Tue Dec 13 10:30:00 EST 2016");
		empMeeting.add("Employee");
		meeting.put("emp", empMeeting);
		meeting.put("end", "Tue Dec 13 11:00:00 EST 2016");
		meeting.put("id", 10000000);
		meeting.put("room", "47L2");
		meeting.put("desc", "Meeting");
		
		//Add events to events JSONArray
		events.add(meeting);
		
		//Add events JSONArray to objectToFile
		objectToFile.put("events", events);
		
		// Attempt to write objectToFile to an oracle txt file
		try (FileWriter file = new FileWriter(oracleTxt)) {
			file.write(objectToFile.toJSONString());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		handleJSON.printScheduleRoom(arguments);
				
		boolean test = false;
		try {
			test = FileUtils.contentEquals(oracleTxt, outputTxt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(test);
	}
}
