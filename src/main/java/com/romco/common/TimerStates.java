package com.romco.common;

public enum TimerStates {
    RUNNING {
        public boolean isStarted() { return true; }
        public boolean isRunning() { return true; }
    },
    STOPPED {
        public boolean isStarted() { return false; }
        public boolean isRunning() { return false; }
    },
    PAUSED {
        public boolean isStarted() { return true; }
        public boolean isRunning() { return false; }
    };

    public static TimerStates getFromString(String from) {
        for (TimerStates timerStates : TimerStates.values()) {
            if (timerStates.toString().equalsIgnoreCase(from)) {
                return timerStates;
            }
        }
        return null;
    }

    public abstract boolean isStarted();
    public abstract boolean isRunning();
}
