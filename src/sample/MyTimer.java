package sample;

public class MyTimer implements Startable {
    private long startNanoTime, storedElapsedTime, totalTime;
    private TimerStates state;
//    private String timerString;
    
    public MyTimer(String timerString) {
        this.state = TimerStates.STOPPED;
        this.storedElapsedTime = 0;
        this.totalTime = MyFormatter.timeStringToLongMillisecondsTime(timerString);
    }
    
    public long getTotalTime() {
        return totalTime;
    }
    
    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }
    
    public long getRemainingTime() {
        System.out.println("state: " + this.state.toString()
                                   + "\ntotal time: " + totalTime
                                   + "\nstored elapsed time: " + storedElapsedTime
                                   + "\nstarted nano time: " + startNanoTime
                                   + "\ncurrent nano time: " + System.nanoTime());
        if (this.state == TimerStates.STOPPED) {
            return this.totalTime;
        } else if (this.state == TimerStates.PAUSED) {
            return totalTime - storedElapsedTime;
        } else if (this.state == TimerStates.STARTED) {
            return totalTime - storedElapsedTime - ((System.nanoTime() - startNanoTime)/1000000);
        }
        return -1;
    }
    
    @Override
    public boolean start() {
        if (this.state == TimerStates.STARTED ) {
            System.out.println("Already started.");
            return false;
        }
        
        this.startNanoTime = System.nanoTime();
        this.state = TimerStates.STARTED;
        
        return true;
    }
    
    @Override
    public boolean stop() {
        if(this.state == TimerStates.STOPPED) {
            System.out.println("Already stopped.");
            return false;
        }
        
        this.state = TimerStates.STOPPED;
        this.storedElapsedTime = 0;
        this.startNanoTime = 0;
        return true;
    }
    
    @Override
    public boolean pause() {
        if(this.state == TimerStates.PAUSED) {
            System.out.println("Already paused.");
            return false;
        }
        
        this.state = TimerStates.PAUSED;
        this.storedElapsedTime += ((System.nanoTime() - startNanoTime) / 1000000);
        
        return true;
    }
    
    
    
    //    public static enum State implements Startable {
//        STARTED {
//            boolean isStarted() { return true; }
//            boolean isRunning() { return true; }
//        },
//        STOPPED {
//            boolean isStarted() { return false; }
//            boolean isRunning() { return false; }
//        },
//        PAUSED {
//            boolean isStarted() { return true; }
//            boolean isRunning() { return false; }
//        };
//        abstract boolean isStarted();
//        abstract boolean isRunning();
//    }
    
}
