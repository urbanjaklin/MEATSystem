package meat.testsystem;
/**
*
* @author CSCE 740 Group 1 - Jessica Anderson, Natasha Delahunt, Urban Jaklin
*/


/**
* The <code>Vacation</code> class manages the Vacation type of Event that can
* be created with the MEAT system. Its methods allow you to retrieve parameters
* from Vacation objects and presents a standardization of information which can be
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
import meat.mainsystem.Vacation;

public class TestVacation {

	@Test
	public void test() {
		//Setup a test Vacation to compare values of fields
		// and test functionality
		Vacation testVac;
		int testEventID = 1001;
		String testEventDescrp = "Vacation to Grand Canyon";
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		String startString = "2017.03.13 AD at 00:00:00 EST";
		String endString = "2017.03.15 AD at 23:59:59 EST";
		Date testStartTime = null; Date testEndTime = null;
		try {
			testStartTime = df.parse(startString);
			testEndTime = df.parse(endString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] testEmpArray = {"Natasha Delahunt"};
		testVac = new Vacation(testEventID, testEventDescrp, testStartTime, testEndTime, testEmpArray);

		assertEquals(testVac.eventID, testEventID);
		assertEquals(testVac.eventDescription, testEventDescrp);
		assertEquals(testVac.startTime, testStartTime);
		assertEquals(testVac.endTime, testEndTime);
		assertArrayEquals(testVac.empArray, testEmpArray);

		//Testing EventID interactions
		int a = testVac.getEventID();
		assertEquals(a, testEventID);
		int oracle_a = 1015;
		testVac.setEventID(oracle_a);
		assertEquals(testVac.getEventID(), oracle_a);
		
		//Testing Event Description interactions
		String b = testVac.getEventDescription();
		assertEquals(b, testEventDescrp);
		String oracle_b = "Out of Office";
		testVac.setEventDescription(oracle_b);
		assertEquals(testVac.getEventDescription(), oracle_b);

		//Testing Start Time interactions
		Date c = testVac.getStartTime();
		assertEquals(c, testStartTime);
		String oracleCString = "2017.03.12 AD at 07:00:00 EST";
		Date oracle_c = null;
		try {
			oracle_c = df.parse(oracleCString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		testVac.setStartTime(oracle_c);
		assertEquals(testVac.getStartTime(), oracle_c);

		//Testing End Time Interactions
		Date d = testVac.getEndTime();
		assertEquals(d, testEndTime);
		String oracleDString = "2017.03.18 AD at 13:15:05 EST";
		Date oracle_d = null;
		try {
			oracle_d = df.parse(oracleDString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		testVac.setEndTime(oracle_d);
		assertEquals(testVac.getEndTime(), oracle_d);

		//Testing Employee Array interactions
		String[] e = testVac.getEmpArray();
		assertArrayEquals(e, testEmpArray);
		String[] oracle_e = {"Jessica Anderson"};
		testVac.setEmpArray(oracle_e);
		assertArrayEquals(testVac.getEmpArray(), oracle_e);
		
		//Testing toString interactions
		String oracle_f_start = testVac.startTime.toString();
		String oracle_f_end = testVac.endTime.toString();
		String oracle_f = "Vacation with ID: " + oracle_a + "\nStart Time: " + oracle_f_start 
		+ "\nEnd Time: " +  oracle_f_end + "\nParticipants: " + oracle_e;
		String f = testVac.toString();
		assertEquals(f, oracle_f);
		
		//Clearing the test vacation created for this test
		testVac = null;

	}

}
