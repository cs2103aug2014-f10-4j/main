//@author A0113672L
package list.model;

import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.DateTimeFieldType;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.mdimension.jchronic.Chronic;
import com.mdimension.jchronic.utils.Span;

public class Date implements Comparable<Date> {
    private static final String DAY_FLOATING_PRETTY = "";
    private static final String DAY_FLOATING = "";
    
    private static final int MINUTE_VALUE = 00;
    private static final int HOUR_VALUE = 12;
    private static final String FORMAT_STRING_PRETTY = "d MMM y hh:mma";
    private static final String FORMAT_STRING_DAY_NAME = "EEEE";
    private static final String FORMAT_STRING_MONTH_NAME = "MMM";
    private static final String FORMAT_STRING_TIME = "hh:mma";
    private static final String STRING_TODAY = "Today";
    private static final String STRING_TOMORROW = "Tomorrow";
    
    private static final DateTimeFormatter FORMATTER_PRETTY = DateTimeFormat.forPattern(FORMAT_STRING_PRETTY);
    private static final DateTimeFormatter FORMATTER_DAY_NAME = DateTimeFormat.forPattern(FORMAT_STRING_DAY_NAME);
    private static final DateTimeFormatter FORMATTER_MONTH_NAME = DateTimeFormat.forPattern(FORMAT_STRING_MONTH_NAME);
    private static final DateTimeFormatter FORMATTER_TIME = DateTimeFormat.forPattern(FORMAT_STRING_TIME);
    private static final DateTimeFormatter FORMATTER_STANDARD = ISODateTimeFormat.dateTime();
    
    private static final DateTimeComparator DATE_ONLY_COMPARATOR = DateTimeComparator.getDateOnlyInstance();
    private static final DateTimeComparator TIME_COMPARATOR = DateTimeComparator.getInstance(DateTimeFieldType.minuteOfDay());
    
    private static Date DATE_FLOATING = null;
    
    private DateTime dateTime;
    private boolean isFloating = false;
    
    @SuppressWarnings("serial")
    public class InvalidDateException extends Exception { };
    
    /**
     * Returns today's date
     */
    public Date() {
        this.dateTime = new DateTime();
    }
    
    public Date(int day, int month, int year) throws InvalidDateException {
        try {
            this.dateTime = new DateTime(year, month, day, HOUR_VALUE, MINUTE_VALUE);
        } catch (IllegalFieldValueException e) {
            e.printStackTrace();
            throw new InvalidDateException();
        }
    }
    
    public Date(String dateString) throws InvalidDateException {
        try {
            if (dateString.equals(DAY_FLOATING)) {
                this.isFloating = true;
                this.dateTime = new DateTime();
            } else {
                this.dateTime = FORMATTER_STANDARD.parseDateTime(dateString);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new InvalidDateException();
        }
    }
    
    private Date(Calendar calendar) {
        this.dateTime = new DateTime(calendar);
    }
    
    private Date(DateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    /**
     * This method tries to parse the given string into a date.
     * If the given string is invalid, it will return null but
     * will not throw an exception.
     * @param userInput
     * @return a Date object
     */
    public static Date tryParse(String userInput) {
        try {
            Date result;
            result = tryParsePrettyFormat(userInput);
            if (result != null) {
                return result;
            } else {
                Span time = Chronic.parse(userInput);
                return new Date(time.getEndCalendar());                
            }
        } catch (Exception e) {
            return null;
        }
    }
    
    private static Date tryParsePrettyFormat(String prettyDateString) {
        try {
            DateTime dateTime = FORMATTER_PRETTY.parseDateTime(prettyDateString);
            return new Date(dateTime);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    public static Date getFloatingDate() {
        if (DATE_FLOATING == null) {
            DATE_FLOATING = new Date();
            DATE_FLOATING.isFloating = true;
        }
        return DATE_FLOATING;
    }
    
    public int getDay() {
        return this.dateTime.getDayOfMonth();
    }
    
    public int getMonth() {
        return this.dateTime.getMonthOfYear();
    }
    
    public int getYear() {
        return this.dateTime.getYear();
    }
    
    public String getDayName() {
        return FORMATTER_DAY_NAME.print(this.dateTime);
    }
    
    public String getMonthName() {
        return FORMATTER_MONTH_NAME.print(this.dateTime);
    }

    /**
     * Returns a String representation that is adjusted for displaying
     * in the user interface. Pretty-printed for user satisfaction.
     * @return
     */
    public String getPrettyFormat() {
        if (this.isFloating) {
            return DAY_FLOATING_PRETTY;
        }
        DateTime today = new DateTime();
        DateTime tomorrow = today.plusDays(1);
    	if (DATE_ONLY_COMPARATOR.compare(today, this.dateTime) == 0) {
    		return STRING_TODAY + " " + getTime();
    	} else if (DATE_ONLY_COMPARATOR.compare(tomorrow, this.dateTime) == 0) {
    		return STRING_TOMORROW + " " + getTime();
    	}
        return FORMATTER_PRETTY.print(this.dateTime); 
    }
    
    public String getTime() {
        return FORMATTER_TIME.print(this.dateTime);
    }
    
    public int compareToDateOnly(Date o) {
        if (this.isFloating && !o.isFloating) {
            return 1;
        } else if (!this.isFloating && o.isFloating) {
            return -1;
        }
        return DATE_ONLY_COMPARATOR.compare(this.dateTime, o.dateTime);
    }
    
    public boolean equalsDateOnly(Date o) {
        if (o == this) return true;
        if (o == null) return false;
        if (!(o instanceof Date)) return false;
        Date other = (Date) o;
        return this.compareToDateOnly(other) == 0; 
    }
    
    @Override
    public int compareTo(Date o) {
        if (this.isFloating && !o.isFloating) {
            return 1;
        } else if (!this.isFloating && o.isFloating) {
            return -1;
        }
        return TIME_COMPARATOR.compare(dateTime, o.dateTime);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (!(o instanceof Date)) return false;
        Date other = (Date) o;
        return this.compareTo(other) == 0; 
    }
    
    /**
     * Returns the date in standard format.
     * The returned String is guaranteed to be accepted by the constructor
     * of this Date class. Suitable for saving to text file format, e.g. JSON.
     */
    @Override
    public String toString() {
        if (this.isFloating) {
            return DAY_FLOATING;
        }
        return FORMATTER_STANDARD.print(dateTime);
    }
}
