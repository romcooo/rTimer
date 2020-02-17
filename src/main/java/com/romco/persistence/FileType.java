package com.romco.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum FileType {
    TIMER,
    STOPWATCH,
    CLOCK;
    
    private static final Logger logger = LoggerFactory.getLogger(FileType.class);
    
    public static FileType getByString(String string) {
        logger.debug("getByString input: {}", string);
        for (FileType fileType : FileType.values() ) {
            if (fileType.toString().equalsIgnoreCase(string)) {
                System.out.println(fileType.toString());
                return fileType;
            }
        }
        return null;
    }
}
