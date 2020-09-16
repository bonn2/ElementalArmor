package ca.minecore.elementalarmor.util;

import java.util.Calendar;
import java.util.Date;

public class Timeout {

    private final Date endTime;

    /**
     * Creates a timeout that has already expired
     */
    public Timeout() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, -1);
        endTime = calendar.getTime();
    }

    /**
     * @param time Amount of time until the timeout expires
     * @param units Unit of time from java.util.Calender
     */
    public Timeout(int time, int units) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(units, time);
        endTime = calendar.getTime();
    }

    /**
     * @return Whether the timeout has expired or not
     */
    public boolean isTimedOut() {
        return new Date().after(endTime);
    }

    /**
     * @return How many seconds until timeout expires
     */
    public int getRemainingTime() {
        return (int) ((endTime.getTime() - new Date().getTime()) / 1000);
    }
}
