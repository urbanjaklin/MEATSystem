/**
 * 
 */
package meat.testsystem;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/*import meat.mainsystem.Event;*/
import meat.mainsystem.EventFactory;
import meat.mainsystem.EventSchedulePanel;
/*import meat.mainsystem.HandleJSON;*/
import meat.mainsystem.MainFrame;
import meat.mainsystem.Meeting;

/**
*
* @author CSCE 740 Group 1 - Jessica Anderson, Natasha Delahunt, Urban Jaklin
*/

public class TestEventScheudle {
	// Variable Declarations
	private String mtgTestEventType;
	private String vacTestEventType;
	private String hldyTestEventType;
	private String emptyTestEventType;
	private String testEventDescrp;
	private String testRoom;
	
	private DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
	private String startString = "2016.12.13 AD at 09:30:00 EST";
	private String endString = "2016.12.13 AD at 10:30:00 EST";
	private Date testStartTime = null; Date testEndTime = null;

	private String[] testEmpArray = {"Jessica Anderson", "Natasha Delahunt", "Urban Jaklin"};
	private String[] testEmpArray2 = {""};

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
	}

	@Test
	public void submitEventTest() {
		
		try {
			testStartTime = df.parse(startString);
			testEndTime = df.parse(endString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		// set up test events
		MainFrame parent = new MainFrame();
		mtgTestEventType = "meeting";
		testEventDescrp = "This is a submit meeting test.";
		EventSchedulePanel mtgTester = new EventSchedulePanel(parent, mtgTestEventType);
		mtgTester.submitEvent(testEventDescrp, testStartTime, testEndTime, testEmpArray, testRoom);
		
		vacTestEventType = "vacation";
		testEventDescrp = "This is a submit vacation test.";
		EventSchedulePanel vacTester = new EventSchedulePanel(parent, vacTestEventType);
		vacTester.submitEvent(testEventDescrp, testStartTime, testEndTime, testEmpArray, testRoom);
		
		hldyTestEventType = "holiday";
		testEventDescrp = "This is a submit holiday test.";
		EventSchedulePanel hldyTester = new EventSchedulePanel(parent,hldyTestEventType);
		hldyTester.submitEvent(testEventDescrp, testStartTime, testEndTime, testEmpArray, testRoom);
		
		testEventDescrp = "This is a submit holiday test.";
		EventSchedulePanel hldyTester2 = new EventSchedulePanel(parent,hldyTestEventType);
		hldyTester.submitEvent(testEventDescrp, testStartTime, testEndTime, testEmpArray2, testRoom);
		
		emptyTestEventType = "";
		testEventDescrp = "";
		testStartTime = null;
		testEndTime = null;
		testRoom = "";
		EventSchedulePanel emptyTester = new EventSchedulePanel(parent, emptyTestEventType);
		emptyTester.submitEvent(testEventDescrp, testStartTime, testEndTime, testEmpArray2, testRoom);
		
		// assert statements
		assertEquals(mtgTestEventType, mtgTester.eventType);
		assertEquals(vacTestEventType, vacTester.eventType);
		assertEquals(hldyTestEventType, hldyTester.eventType);
		assertEquals(hldyTestEventType, hldyTester2.eventType);
		assertEquals(emptyTestEventType, emptyTester.eventType);


		
		
	}

}
