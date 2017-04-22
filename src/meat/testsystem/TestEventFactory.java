package meat.testsystem;
/**
 * This factory class will handle the maintenance of events as they are used by
 * the system. In other words, events which are to be created, read, updated, or
 * deleted by the system must pass through the <code>EventFactory</code>.
 * 
 * @author CSCE 740 Group 1 - Jessica Anderson, Natasha Delahunt, Urban Jaklin
 */
import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import meat.mainsystem.Event;
import meat.mainsystem.EventFactory;
import meat.mainsystem.Holiday;
import meat.mainsystem.Meeting;
import meat.mainsystem.Vacation;

public class TestEventFactory {

	@Test
	public void testMtgCreation() {
		// Initialize EventFactory
		EventFactory eventFactory = new EventFactory();
		
		//Set up variables to test a Meeting Creation
		int testEventID = 1004;
		String testEventDescrp = "Design Review";
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		String startString = "2016.12.17 AD at 09:30:00 EST";
		String endString = "2016.12.17 AD at 10:30:00 EST";
		Date testStartTime = null; Date testEndTime = null;
		try {
			testStartTime = df.parse(startString);
			testEndTime = df.parse(endString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] testEmpArray = {"Jessica Anderson", "Natasha Delahunt", "Urban Jaklin"};
		String testRoom = "Downstairs, to the right.";
		
		Event testMtgCreation = eventFactory.createEvent(testEventID, testEventDescrp, testStartTime, testEndTime, 
				testEmpArray, testRoom);
		Meeting oracle_Mtg = new Meeting(testEventID, testEventDescrp, testStartTime, testEndTime, 
				testEmpArray, testRoom);
		
		assertEquals(oracle_Mtg.toString(), testMtgCreation.toString());

		//Reset Variables
		oracle_Mtg = null; testMtgCreation = null;
		
	}

	@Test
	public void testVacCreation() {
		// Initialize EventFactory
		EventFactory eventFactory = new EventFactory();
	
	
		//Set up variables to test a Vacation Creation
		int testEventID = 20001;
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
		String[] testEmpArray = {"Jessica Anderson"};
		
		Event testVacCreation = eventFactory.createEvent(testEventID, testEventDescrp, testStartTime, testEndTime, 
				testEmpArray, null);
		Vacation oracle_Vac = new Vacation(testEventID, testEventDescrp, testStartTime, testEndTime, testEmpArray);
		
		assertEquals(oracle_Vac.toString(), testVacCreation.toString());
		
		//Reset variables
		testVacCreation = null; oracle_Vac = null; 
	}
	
	@Test
	public void testHolCreation() {
		// Initialize EventFactory
		EventFactory eventFactory = new EventFactory();
		
		//Set up variables to test a Holiday Creation
		int testEventID = 30001;
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
		String[] testEmpArray = {"Jessica Anderson", "Natasha Delahunt", "Urban Jaklin"};
		
		Event testHolCreation = eventFactory.createEvent(testEventID, testEventDescrp, testStartTime, testEndTime, 
				testEmpArray, null);
		Holiday oracle_Hol = new Holiday(testEventID, testEventDescrp, testStartTime, testEndTime, testEmpArray);
		
		assertEquals(oracle_Hol.toString(), testHolCreation.toString());
		
		//Reset variables
		testHolCreation = null; oracle_Hol = null; 
	}
	
	@Test
	public void testInvalidFormat(){
		// Initialize EventFactory
		EventFactory eventFactory = new EventFactory();
				
		//Set up variables to test an invalid Creation
		int testEventID = 50001;
		String testEventDescrp = "Requirements Review";
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		String startString = "2016.12.19 AD at 09:30:00 EST";
		String endString = "2016.12.19 AD at 10:30:00 EST";
		Date testStartTime = null; Date testEndTime = null;
		try {
			testStartTime = df.parse(startString);
			testEndTime = df.parse(endString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] testEmpArray = {"Jessica Anderson", "Natasha Delahunt", "Urban Jaklin"};
		String testRoom = "G5672";
		
		Event testMtgCreation = eventFactory.createEvent(testEventID, testEventDescrp, testStartTime, testEndTime, 
				testEmpArray, testRoom);
		
		assertNull("Should return a null meeting.", testMtgCreation);

		//Reset Variables - just in case
		testMtgCreation = null;
	}
	
	@Test
	public void testValidDate(){
		// Initialize EventFactory
		EventFactory eventFactory = new EventFactory();
		
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		String startString = "2016.12.10 AD at 03:15:82 EST";
		String endString = "2017.01.05 AD at 14:02:59 EST";
		Date testStartTime = null; Date testEndTime = null;
		try {
			testStartTime = df.parse(startString);
			testEndTime = df.parse(endString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Boolean checkDateValid = eventFactory.checkDateValid(testStartTime, testEndTime);
		assertTrue("The start date is prior to the end date.\n", checkDateValid);
		Boolean checkDateNotValid = eventFactory.checkDateValid(testEndTime, testStartTime);
		assertFalse("The end date is prior to the start date.\n", checkDateNotValid);
		
	}

	@Test
	public void testInvalidDate(){
		// Initialize EventFactory
		EventFactory eventFactory = new EventFactory();
		
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		String startString = "2017.01.10 AD at 15:15:82 EST";
		String endString = "2017.01.05 AD at 14:02:59 EST";
		Date testStartTime = null; Date testEndTime = null;
		try {
			testStartTime = df.parse(startString);
			testEndTime = df.parse(endString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Boolean test = eventFactory.checkDateValid(testStartTime, testEndTime);
		assertFalse("The start date is after the end date.\n", test);
		
	}
	
	@Test
	public void testInvalidTime(){
		// Initialize EventFactory
		EventFactory eventFactory = new EventFactory();
		
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		String startString = "2017.01.05 AD at 09:00:01 EST";
		String endString = "2017.01.05 AD at 09:00:00 EST";
		Date testStartTime = null; Date testEndTime = null;
		try {
			testStartTime = df.parse(startString);
			testEndTime = df.parse(endString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Boolean test = eventFactory.checkDateValid(testStartTime, testEndTime);
		assertFalse("The start time is after the end time.\n", test);
		
	}
}
