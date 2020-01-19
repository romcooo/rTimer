package com.romco.persistence;

import com.romco.MyTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class ComponentParser {
    private static final Logger logger = LoggerFactory.getLogger(ComponentParser.class);

    public static String parseTimersToFileFormat(Collection<MyTimer> timers) {
        StringBuilder dataToWrite = new StringBuilder();
        int count = 0;
        for (MyTimer t : timers) {
            dataToWrite.append(++count)
                       .append(", ")
                       .append(t)
                       .append("\n");
            logger.info(dataToWrite.toString());
        }
        return dataToWrite.toString();
    }

    public static Collection<MyTimer> parseTimerFileFormatToTimers(String timersString) {
        //TODO
        return null;
    }

}
