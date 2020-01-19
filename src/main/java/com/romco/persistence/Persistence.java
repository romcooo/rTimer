package com.romco.persistence;

import com.romco.MyTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;

public class Persistence {
    private static final Logger logger = LoggerFactory.getLogger(Persistence.class);

    public static boolean saveTimersAs(Collection<MyTimer> timers, File saveTo) {
        String toWrite = ComponentParser.parseTimersToFileFormat(timers);
        return FileSaver.saveFile(saveTo, toWrite);
    }
}
