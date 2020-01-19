package com.romco.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSaver {
    private static final Logger logger = LoggerFactory.getLogger(FileSaver.class);

    public static boolean saveFile(File toSave, String toWrite) {
        logger.info("Saving: " + toSave.getPath() + ": " + toWrite);
        Path path = Paths.get(toSave.getPath());
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(toWrite);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
