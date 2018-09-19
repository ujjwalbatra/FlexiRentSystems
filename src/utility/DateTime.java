package utility;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTime {

    private long advance;
    private long time;

    public DateTime() {
        time = System.currentTimeMillis();
    }

    public DateTime(int setClockForwardInDays) {
        advance = ((setClockForwardInDays * 24L + 0) * 60L) * 60000L;
        time = System.currentTimeMillis() + advance;
    }

    public DateTime(DateTime startDate, int setClockForwardInDays) {
        advance = ((setClockForwardInDays * 24L + 0) * 60L) * 60000L;
        time = startDate.getTime() + advance;
    }

    public DateTime(int day, int month, int year) {
        setDate(day, month, year);
    }


    public static String getCurrentTime() {
        Date date = new Date(System.currentTimeMillis());  // returns current Date/Time
        return date.toString();
    }

    // returns difference in days
    public static int diffDays(DateTime endDate, DateTime startDate) {
        final long HOURS_IN_DAY = 24L;
        final int MINUTES_IN_HOUR = 60;
        final int SECONDS_IN_MINUTES = 60;
        final int MILLISECONDS_IN_SECOND = 1000;
        long convertToDays = HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTES * MILLISECONDS_IN_SECOND;
        long hirePeriod = endDate.getTime() - startDate.getTime();
        double difference = (double) hirePeriod / (double) convertToDays;
        int round = (int) Math.round(difference);
        return round;
    }

    public long getTime() {
        return time;
    }

    public String toString() {
//		long currentTime = getTime();
//		Date gct = new Date(currentTime);
//		return gct.toString();
        return getFormattedDate();
    }

    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        long currentTime = getTime();
        Date gct = new Date(currentTime);

        return sdf.format(gct);
    }

    public String getEightDigitDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        long currentTime = getTime();
        Date gct = new Date(currentTime);

        return sdf.format(gct);
    }

    private void setDate(int day, int month, int year) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, 0, 0);

        java.util.Date date = calendar.getTime();

        time = date.getTime();
    }

    public Date toSqlDate() {
        String date = getFormattedDate();
        int day = Integer.parseInt(date.substring(0,2));
        int month = Integer.parseInt(date.substring(3,5));
        int year = Integer.parseInt(date.substring(6,10));

        return new Date(year, month, day);
    }
    // Advances date/time by specified days, hours and mins for testing purposes
    public void setAdvance(int days, int hours, int mins) {
        advance = ((days * 24L + hours) * 60L) * 60000L;
    }
}
