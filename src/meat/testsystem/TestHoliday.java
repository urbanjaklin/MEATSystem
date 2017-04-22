package meat.testsystem;

/**
*
* @author CSCE 740 Group 1 - Jessica Anderson, Natasha Delahunt, Urban Jaklin
*/


/**
* The <code>Holiday</code> class manages the Holiday types of events that can
* be created with the MEAT system. Its methods allow you to retrieve parameters
* from Holiday objects.
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

import meat.mainsystem.Holiday;

public class TestHoliday {

	@Test
	public void test() {
		//Setup a test Vacation to compare values of fields
		// and test functionality
		Holiday testHol;
		int testEventID = 1001;
		String testEventDescrp = "Winter Holiday";
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		String startString = "2016.12.12 AD at 00:00:00 EST";
		String endString = "2017.01.06 AD at 23:59:59 EST";
		Date testStartTime = null; Date testEndTime = null;
		try {
			testStartTime = df.parse(startString);
			testEndTime = df.parse(endString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] testEmpArray = {"Jessica Anderson"};
		testHol = new Holiday(testEventID, testEventDescrp, testStartTime, testEndTime, testEmpArray);

		assertEquals(testHol.eventID, testEventID);
		assertEquals(testHol.eventDescription, testEventDescrp);
		assertEquals(testHol.startTime, testStartTime);
		assertEquals(testHol.endTime, testEndTime);
		assertArrayEquals(testHol.empArray, testEmpArray);

		//Testing EventID interactions
		int a = testHol.getEventID();
		assertEquals(a, testEventID);
		int oracle_a = 1015;
		testHol.setEventID(oracle_a);
		assertEquals(testHol.getEventID(), oracle_a);
		
		//Testing Event Description interactions
		String b = testHol.getEventDescription();
		assertEquals(b, testEventDescrp);
		String oracle_b = "Out of Office";
		testHol.setEventDescription(oracle_b);
		assertEquals(testHol.getEventDescription(), oracle_b);

		//Testing Start Time interactions
		Date c = testHol.getStartTime();
		assertEquals(c, testStartTime);
		String oracleCString = "2016.12.22 AD at 00:00:10 EST";
		Date oracle_c = null;
		try {
			oracle_c = df.parse(oracleCString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		testHol.setStartTime(oracle_c);
		assertEquals(testHol.getStartTime(), oracle_c);

		//Testing End Time Interactions
		Date d = testHol.getEndTime();
		assertEquals(d, testEndTime);
		String oracleDString = "2017.01.18 AD at 23:59:00 EST";
		Date oracle_d = null;
		try {
			oracle_d = df.parse(oracleDString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		testHol.setEndTime(oracle_d);
		assertEquals(testHol.getEndTime(), oracle_d);

		//Testing Employee Array interactions
		String[] e = testHol.getEmpArray();
		assertArrayEquals(e, testEmpArray);
		String[] oracle_e = {"Urban Jaklin"};
		testHol.setEmpArray(oracle_e);
		assertArrayEquals(testHol.getEmpArray(), oracle_e);
		
		//Testing toString interactions
		String oracle_f_start = testHol.startTime.toString();
		String oracle_f_end = testHol.endTime.toString();
		String oracle_f = "Holiday with ID: " + oracle_a + "\nStart Time: " + oracle_f_start 
		+ "\nEnd Time: " +  oracle_f_end + "\nParticipants: " + oracle_e;
		String f = testHol.toString();
		assertEquals(f, oracle_f);
		
		//Clearing the test vacation created for this test
		testHol = null;
	}

}
