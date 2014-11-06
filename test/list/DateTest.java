package list;

import static org.junit.Assert.*;
import list.model.Date;
import list.model.Date.InvalidDateException;

import org.junit.Test;

/**
 * JUnit Test Case for list.Date class.
 * @author Andhieka Putra
 */
public class DateTest {

    @Test
    public void compareTwoEqualDates() throws Exception {
        Date someDate = new Date("21-12-2014");
        Date theSameDate = new Date("21-12-2014");
        assertEquals(someDate, theSameDate);
    }

    @Test
    public void compareTwoDifferentDates() throws Exception {
        Date someDate = new Date("21-12-2014");
        Date anotherDate = new Date("25-12-2014");
        assertTrue(anotherDate.compareTo(someDate) > 0);
        assertTrue(someDate.compareTo(anotherDate) < 0);
    }
    
    @Test(expected = InvalidDateException.class)
    public void expectingAnError() throws Exception {
        @SuppressWarnings("unused")
        Date invalidDate = new Date("34-10-2014");
    }
    
    @Test
    public void parseFlexibleFormatDate() throws Exception {
        Date targetDate = new Date(25, 12, 2014);
        assertEquals(targetDate, Date.tryParse("25-12-2014"));
        assertEquals(targetDate, Date.tryParse("25 dec 2014"));
        assertEquals(targetDate, Date.tryParse("12/25/2014"));
        //the following is possible but will return different result once the year changes
        //assertEquals(targetDate, Date.tryParse("one week before 1 Jan"));
    }
    
}
