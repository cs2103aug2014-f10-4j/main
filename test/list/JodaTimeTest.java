//@author A0113672L

package list;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

/**
 * Learning the behavior of JodaTime library.
 * 
 * @author Andhieka Putra
 */
public class JodaTimeTest {

	@Test
	public void compareDatesWithoutTimePortion() throws Exception {
		DateTime someDate = new DateTime(2014, 5, 3, 0, 0);
		DateTime anotherDateWithDifferentTime = new DateTime(2014, 5, 3, 11, 59);
		
		int comparisonResult = DateTimeComparator.getDateOnlyInstance().
		        compare(someDate, anotherDateWithDifferentTime);
		assertEquals(0, comparisonResult);
	}
	
	@Test
	public void shouldIgnoreThreadSleep() throws Exception {
        DateTime someDate = new DateTime(2014, 5, 3, 0, 0);
        Thread.sleep(50);
        DateTime theSameDate = new DateTime(2014, 5, 3, 0, 0);
        
        assertEquals(someDate, theSameDate);
	}
	
	@Test
	public void testToStringMethod() throws Exception {
	    DateTime someDate = new DateTime(2014, 5, 3, 11, 23);
	    
	    assertEquals("2014-05-03T11:23:00.000+08:00", someDate.toString());
	}

	/**
	 * For text return types, four or more letters will result in full form.
	 * Otherwise, the short form, if available, will be used.
	 * In order to print full day name, we have to use four or more 'E' letters.
	 * @throws Exception
	 */
	@Test
	public void testPrintingDayName() throws Exception {
	    DateTime oneSunnyWednesday = new DateTime(2014, 10, 15, 0, 0);
	    DateTimeFormatter format = DateTimeFormat.forPattern("EEEE d/M/y");
	    String dayNameAndDate = format.print(oneSunnyWednesday);
	    
	    assertEquals("Wednesday 15/10/2014", dayNameAndDate);
	}
	
	/**
     * In order to print shortened day name, we can use a single 'E' letter.
     * In contrast with <code>testPrintingDayName()</code>
     * @throws Exception
     */
	@Test
	public void testPrintingDayNameShortForm() throws Exception {
	    DateTime oneSunnyWednesday = new DateTime(2014, 10, 15, 0, 0);
        DateTimeFormatter format = DateTimeFormat.forPattern("E, d MMM y");
        String dayNameAndDate = format.print(oneSunnyWednesday);
        
        assertEquals("Wed, 15 Oct 2014", dayNameAndDate);
	}
	
	@Test(expected = IllegalFieldValueException.class)
	public void testInvalidDate() throws Exception {
	    @SuppressWarnings("unused")
        DateTime invalidDate = new DateTime(2014, 02, 29, 0, 0);
	}
}
