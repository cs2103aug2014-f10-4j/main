//@author A0113672L
package list;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import list.model.Date;
import list.model.Date.InvalidDateException;

import org.junit.Ignore;
import org.junit.Test;

/**
 * JUnit Test Case for list.Date class.
 * @author Andhieka Putra
 */
public class DateTest {

    @Test
    public void compareTwoEqualDates() throws Exception {
        Date someDate = new Date(21,12,2014);
        Date theSameDate = new Date(21,12,2014);
        assertEquals(someDate, theSameDate);
    }

    @Test
    public void compareTwoDifferentDates() throws Exception {
        Date someDate = new Date(21,12,2014);
        Date anotherDate = new Date(25,12,2014);
        assertTrue(anotherDate.compareTo(someDate) > 0);
        assertTrue(someDate.compareTo(anotherDate) < 0);
    }
    
    @Ignore
    @Test(expected = InvalidDateException.class)
    public void expectingAnError() throws Exception {
        @SuppressWarnings("unused")
        Date invalidDate = new Date("34-10-2014");
    }
    
    @Test
    public void parseFlexibleFormatDate() throws Exception {
        Date targetDate = new Date(25, 12, 2014);
        assertTrue(targetDate.equalsDateOnly(Date.tryParse("25-12-2014")));
        assertTrue(targetDate.equalsDateOnly(Date.tryParse("25 dec 2014")));
        assertTrue(targetDate.equalsDateOnly(Date.tryParse("12/25/2014")));
    }
    
    @Test
    public void parsePrettyString() throws Exception {
        Date targetDate = Date.tryParse("Dec 17 2999 11.11AM");
        String prettyFormat = targetDate.getPrettyFormat();
        Method method = Date.class.getDeclaredMethod("tryParsePrettyFormat", String.class);
        method.setAccessible(true);        
        Date parseResult = (Date) method.invoke(null, prettyFormat);
        assertEquals(targetDate, parseResult);
    }
    
    @Test
    public void parseTodayAndTomorrowString() throws Exception {
        Date date = new Date();
        String pretty = date.getPrettyFormat();
        Date date2 = Date.tryParse(pretty);
        assertEquals(date, date2);
    }
    
    @Test
    public void testWithinOneDayCompare() throws Exception {
    	Date start = Date.tryParse("yesterday 23.59");
    	Date end = Date.tryParse("today 23.58");
    	Date over = Date.tryParse("today 23.59");
    	assertTrue(end.withinOneDayFrom(start));
    	assertFalse(over.withinOneDayFrom(start));
    }
}
