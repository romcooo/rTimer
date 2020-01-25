package com.romco.persistence;

public enum FileType {
    TIMER,
    STOPWATCH,
    CLOCK;

    public static FileType getByString(String string) {
        for (FileType fileType : FileType.values() ) {
            if (fileType.toString().equalsIgnoreCase(string)) {
                System.out.println(fileType.toString());
                return fileType;
            }
        }
        return null;
    }
}
