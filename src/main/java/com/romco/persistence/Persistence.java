package com.romco.persistence;

import com.romco.controller.MainController;
import com.romco.common.MyTimer;
import com.romco.controller.TimerTabController;
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

    public static boolean loadFile(File fileToLoad, MainController controller) {
        try {
            String fileContent = FileLoader.loadStringFromFile(fileToLoad);
            FileType type = ComponentParser.determineFileType(fileContent);
            logger.debug("loadFile - Parsed type is {}", type);
            switch (type) {
                case TIMER:
                    ArrayList<MyTimer> list = ComponentParser.parseTimersFromFile(fileContent);
                    if (list.isEmpty()) {
                        logger.debug("Empty list returned by parser.");
                        return false;
                    }
                    TimerTabController ttc = controller.getTimerTabController();
                    ttc.deleteAllTimerHBoxes();
                    ttc.addMultipleTimers(list);
                    return true;
                default:
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
