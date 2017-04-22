package meat.testsystem;


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

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import meat.mainsystem.Event;

public class TestEventFormat {

	
	@Test
	public void test() {
		//Setup a test event to compare values of fields
		// and test functionality
		Event testEvent = new Event();
		int testEventID = 1001;
		String testEventDescrp = "This is a test.";
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		String startString = "2016.12.13 AD at 09:30:00 EST";
		String endString = "2016.12.13 AD at 10:30:00 EST";
		Date testStartTime = null; Date testEndTime = null;
		try {
			testStartTime = df.parse(startString);
			testEndTime = df.parse(endString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] testEmpArray = {"Jessica Anderson", "Natasha Delahunt", "Urban Jaklin"};
		testEvent = new Event(testEventID, testEventDescrp, testStartTime, testEndTime, testEmpArray);

		assertEquals(testEvent.eventID, testEventID);
		assertEquals(testEvent.eventDescription, testEventDescrp);
		assertEquals(testEvent.startTime, testStartTime);
		assertEquals(testEvent.endTime, testEndTime);
		assertArrayEquals(testEvent.empArray, testEmpArray);

		//Testing EventID interactions
		int a = testEvent.getEventID();
		assertEquals(a, testEventID);
		int oracle_a = 1015;
		testEvent.setEventID(oracle_a);
		assertEquals(testEvent.getEventID(), oracle_a);	
		
		//Testing Event Description interactions
		String b = testEvent.getEventDescription();
		assertEquals(b, testEventDescrp);
		String oracle_b = "It just might work.";
		testEvent.setEventDescription(oracle_b);
		assertEquals(testEvent.getEventDescription(), oracle_b);

		//Testing Start Time interactions
		Date c = testEvent.getStartTime();
		assertEquals(c, testStartTime);
		String oracleCString = "2016.12.13 AD at 07:00:00 EST";
		Date oracle_c = null;
		try {
			oracle_c = df.parse(oracleCString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		testEvent.setStartTime(oracle_c);
		assertEquals(testEvent.getStartTime(), oracle_c);

		//Testing End Time Interactions
		Date d = testEvent.getEndTime();
		assertEquals(d, testEndTime);
		String oracleDString = "2016.12.13 AD at 13:15:05 EST";
		Date oracle_d = null;
		try {
			oracle_d = df.parse(oracleDString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		testEvent.setEndTime(oracle_d);
		assertEquals(testEvent.getEndTime(), oracle_d);

		//Testing Employee Array interactions
		String[] e = testEvent.getEmpArray();
		assertArrayEquals(e, testEmpArray);
		String[] oracle_e = {"It just might work.", "Probably not."};
		testEvent.setEmpArray(oracle_e);
		assertArrayEquals(testEvent.getEmpArray(), oracle_e);
		
		//Clearing the test event created for this test
		testEvent = null;

	}
		
}
