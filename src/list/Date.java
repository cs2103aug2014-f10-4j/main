package list;

public class Date implements Comparable<Date> {
    private final int mDay;
    private final int mMonth;
    private final int mYear;
    
    private final String[] MONTH_NAME = {
    	"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };
    
    Date(int day, int month, int year) {
        mDay = day;
        mMonth = month;
        mYear = year;
    }
    
    public int getDay() {
        return mDay;
    }
    
    public int getMonth() {
        return mMonth;
    }
    
    public int getYear() {
        return mYear;
    }
    
    public String getMonthName() {
        return MONTH_NAME[mMonth - 1];
    }

    @Override
    public int compareTo(Date o) {
        if (o == this) return 0;
        if (getYear() < o.getYear()) return -1;
        if (getYear() > o.getYear()) return 1;
        if (getMonth() < o.getMonth()) return -1;
        if (getMonth() > o.getMonth()) return 1;
        if (getDay() < o.getDay()) return -1;
        if (getDay() > o.getDay()) return 1;
        return 0;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (!(o instanceof Date)) return false;
        Date other = (Date) o;
        return this.compareTo(other) == 0; 
    }
}
