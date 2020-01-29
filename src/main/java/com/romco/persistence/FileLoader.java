package com.romco.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileLoader {
    private static final Logger logger = LoggerFactory.getLogger(FileSaver.class);

    public static String loadStringFromFile(File fileToLoad) throws IOException {
        try (Scanner scanner = new Scanner(new FileReader(fileToLoad))) {
            if (scanner.hasNextLine()) {
                scanner.useDelimiter("\\Z");
                String restOfFile = scanner.next(); //rest of file
                logger.info("File is: " + restOfFile);
                if (!restOfFile.isEmpty()) {
                    return restOfFile;
                }
            }
            throw new IOException("File is empty.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
