package sample;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

class MyFormatter {
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
    static String longMillisecondsTimeToTimeString(long l) {
        String timeString = String.format("%02d:%02d:%02d.%03d",
                l / MILLIS_TO_HOURS,
                l % MILLIS_TO_HOURS / MILLIS_TO_MINUTES,
                l % MILLIS_TO_HOURS % MILLIS_TO_MINUTES / MILLIS_TO_SECONDS,
                l % MILLIS_TO_HOURS % MILLIS_TO_MINUTES % MILLIS_TO_SECONDS);

        return timeString;
    }
}
