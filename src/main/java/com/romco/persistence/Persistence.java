package com.romco.persistence;

import com.romco.MyTimer;
import com.romco.TimerTabController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Persistence {
    private static final Logger logger = LoggerFactory.getLogger(Persistence.class);

    public static boolean saveTimersAs(Collection<MyTimer> timers, File saveTo) {
        String toWrite = ComponentParser.parseTimersToFileFormat(timers);
        return FileSaver.saveFile(saveTo, toWrite);
    }

    public static boolean loadFile(File fileToLoad, TimerTabController controller) {
        try {
            ArrayList list = ComponentParser.parseFile(
                    FileLoader.loadStringFromFile(fileToLoad));
            if (list.isEmpty()) {
                logger.debug("Empty list returned by parser.");
                return false;
            }
            logger.info(list.get(0).getClass().toString());
            if (list.get(0) instanceof MyTimer) {
                // TODO rework controller access
                controller.addMultipleTimers(list);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
