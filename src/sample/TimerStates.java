package sample;

enum TimerStates {
    STARTED {
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
    abstract boolean isStarted();
    abstract boolean isRunning();
}
