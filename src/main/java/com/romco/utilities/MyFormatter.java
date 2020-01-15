package com.romco.utilities;

import java.util.InputMismatchException;

public class MyFormatter {
    private static final int MILLIS_TO_HOURS = 3600000;
    private static final int MILLIS_TO_MINUTES = 60000;
    private static final int MILLIS_TO_SECONDS = 1000;
    
    /**
     * Takes a time parameter in total elapsed milliseconds,
     * and returns a string formatted by default to "HH:MI:SS.fff".
     *
     * @param l
     * Time in milliseconds (elapsed, not unix time)
     * eg. "62350"
     *
     * @return
     * "HH:MI:SS.fff"
     * eg. "00:01:02:350"
     *
     */
    public static String longMillisecondsTimeToTimeString(long l) {
        return String.format("%01d:%02d:%02d.%03d",
                l / MILLIS_TO_HOURS,
                l % MILLIS_TO_HOURS / MILLIS_TO_MINUTES,
                l % MILLIS_TO_HOURS % MILLIS_TO_MINUTES / MILLIS_TO_SECONDS,
                l % MILLIS_TO_HOURS % MILLIS_TO_MINUTES % MILLIS_TO_SECONDS);
    }
    
    /**
     * Takes a "time string" in format H+:m+:s+.f+,
     * and returns the time in milliseconds
     *
     * @param s
     * Time as a string, eg. "HH:MI:SS.fff"
     *
     * @return
     * For input "00:01:02:350", returns 62350
     *
     * @throws InputMismatchException
     * If the input does not conform to the following regex: "\d+:\d+:\d.\d+"
     * in other words, digits:digits:digits.digits (where digits is one or more digit)
     */
    public static long timeStringToLongMillisecondsTime(String s) throws InputMismatchException {
        String[] values = s.split("[:.]");
        if (values.length < 4) {
            throw new InputMismatchException("Input string cannot be parsed;");
        }
        
        int hours = Integer.parseInt(values[0]);
        int minutes = Integer.parseInt(values[1]);
        int seconds = Integer.parseInt(values[2]);
        int millis = Integer.parseInt(values[3]);
        
        return hours * MILLIS_TO_HOURS
                + minutes * MILLIS_TO_MINUTES
                + seconds * MILLIS_TO_SECONDS
                + millis;
    }
}