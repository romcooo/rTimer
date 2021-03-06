package com.romco.persistence;

import com.romco.common.MyTimer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class ComponentParser {
    private static final Logger logger = LoggerFactory.getLogger(ComponentParser.class);

    public static String parseTimersToFileFormat(Collection<MyTimer> timers) {
        StringBuilder dataToWrite = new StringBuilder();
        dataToWrite.append("FileType=")
                   .append(FileType.TIMER.toString())
                   .append("\n");
        int count = 0;
        for (MyTimer t : timers) {
            dataToWrite.append(t)
                       .append("\n");
            logger.info(dataToWrite.toString());
        }
        return dataToWrite.toString();
    }

    public static FileType determineFileType(String file) {
        String firstLine = file.substring(0, file.indexOf("\n"));
        logger.debug("First line: {}", firstLine);
        String subString = firstLine.substring(firstLine.indexOf('=')+1).trim();
        logger.debug("Substring: {}", subString);
        return FileType.getByString(subString);
    }

    @NotNull
    public static ArrayList<MyTimer> parseTimersFromFile(String fileContent) {
        List<String> lines = new ArrayList<>(Arrays.asList(fileContent.split("\n")));

        //remove first line so rest can be passed to a for loop for parsing
        if (lines.get(0).trim().contains(FileType.TIMER.toString())) {
            lines.remove(0);
        }
        if (lines.isEmpty()) {
            //throw exception
            throw new InputMismatchException("File is empty.");
        }

        ArrayList<MyTimer> timers = new ArrayList<>();
        for (String line : lines) {
            logger.info("At line: " + line);
            timers.add(MyTimer.MyTimerFactory.fromString(line));
        }
        return timers;
    }

    public static ArrayList<Object> parseFile(String fileString) throws InputMismatchException {
        List<String> lines = new ArrayList<>(Arrays.asList(fileString.split("\n")));
        if (lines.isEmpty()) {
            //throw exception
            throw new InputMismatchException("File is empty.");
        }
        //remove first line so rest can be passed to a for loop for parsing
        FileType result = FileType.getByString(lines.remove(0));
        if (result == null) {
            throw new InputMismatchException("Invalid file: missing type (first line should be one of: "
                                                     + Arrays.stream(FileType.values())
                                                             .map(Object::toString)
                                                             .collect(Collectors.joining(", ")));
        }

        switch (result) {
            case TIMER:
                ArrayList<Object> timers = new ArrayList<>();
                for (String line : lines) {
                    timers.add(MyTimer.MyTimerFactory.fromString(line));
                }
                return timers;
            default:
                break;
        }
        if (lines.size() <= 1) {
            throw new InputMismatchException("File contains type, but is otherwise empty.");
        }

        return null;
    }

}
