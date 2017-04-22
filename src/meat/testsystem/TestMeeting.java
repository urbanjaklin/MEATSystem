package meat.testsystem;


/**
 *
 * @author CSCE 740 Group 1 - Jessica Anderson, Natasha Delahunt, Urban Jaklin
 */


/**
 * The <code>Meeting</code> class manages the Meeting type of Event that can
 * be created with the MEAT system. Its methods allow you to retrieve parameters
 * from Meeting objects and presents a standardization of information which can be
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

import meat.mainsystem.Meeting;

public class TestMeeting {

	@Test
	public void test() {
		//Setup a test Meeting to compare values of fields
		// and test functionality
		Meeting testMtg;
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
		String testRoom = "5F62";
		testMtg = new Meeting(testEventID, testEventDescrp, testStartTime, testEndTime, testEmpArray, testRoom);

		assertEquals(testMtg.eventID, testEventID);
		assertEquals(testMtg.eventDescription, testEventDescrp);
		assertEquals(testMtg.startTime, testStartTime);
		assertEquals(testMtg.endTime, testEndTime);
		assertArrayEquals(testMtg.empArray, testEmpArray);

		//Testing EventID interactions
		int a = testMtg.getEventID();
		assertEquals(a, testEventID);
		int oracle_a = 1015;
		testMtg.setEventID(oracle_a);
		assertEquals(testMtg.getEventID(), oracle_a);
		
		//Testing Event Description interactions
		String b = testMtg.getEventDescription();
		assertEquals(b, testEventDescrp);
		String oracle_b = "It just might work.";
		testMtg.setEventDescription(oracle_b);
		assertEquals(testMtg.getEventDescription(), oracle_b);

		//Testing Start Time interactions
		Date c = testMtg.getStartTime();
		assertEquals(c, testStartTime);
		String oracleCString = "2016.12.13 AD at 07:00:00 EST";
		Date oracle_c = null;
		try {
			oracle_c = df.parse(oracleCString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		testMtg.setStartTime(oracle_c);
		assertEquals(testMtg.getStartTime(), oracle_c);

		//Testing End Time Interactions
		Date d = testMtg.getEndTime();
		assertEquals(d, testEndTime);
		String oracleDString = "2016.12.13 AD at 13:15:05 EST";
		Date oracle_d = null;
		try {
			oracle_d = df.parse(oracleDString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		testMtg.setEndTime(oracle_d);
		assertEquals(testMtg.getEndTime(), oracle_d);

		//Testing Employee Array interactions
		String[] e = testMtg.getEmpArray();
		assertArrayEquals(e, testEmpArray);
		String[] oracle_e = {"It just might work.", "Probably not."};
		testMtg.setEmpArray(oracle_e);
		assertArrayEquals(testMtg.getEmpArray(), oracle_e);
		
		//Testing Event Room interactions
		String f = testMtg.getEventRoom();
		assertEquals(f, testRoom);
		String oracle_f = "Upstairs to the left.";
		testMtg.setEventRoom(oracle_f);
		assertEquals(testMtg.getEventRoom(), oracle_f);
		
		//Testing toString interactions
		String oracle_g_start = testMtg.startTime.toString();
		String oracle_g_end = testMtg.endTime.toString();
		String oracle_g = "Meeting with ID: " + oracle_a + "\nStart Time: " + oracle_g_start 
		+ "\nEnd Time: " +  oracle_g_end + "\nParticipants: " + oracle_e + "\nRoom: " + oracle_f;
		String g = testMtg.toString();
		assertEquals(g, oracle_g);
		
		//Clearing the test meeting created for this test
		testMtg = null;

	}
}


