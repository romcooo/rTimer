package com.romco;

import com.romco.utilities.MyFormatter;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class MyTimer implements Startable {
    private static final long DEFAULT_TIMER_MILLIS_VALUE = 1000;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private long startNanoTime, storedElapsedTime, totalTime;
    private TimerStates state;

    private Media music;
    private MediaPlayer mediaPlayer;
    private boolean hasRung;

    public MyTimer(String timerString) {
        this();
        this.totalTime = MyFormatter.timeStringToLongMillisecondsTime(timerString);

        hasRung = this.totalTime <= 0;
    }
    
    public MyTimer() {
        this.state = TimerStates.STOPPED;
        this.storedElapsedTime = 0;
        this.totalTime = DEFAULT_TIMER_MILLIS_VALUE;

        music = new Media(new File(TimerTabController.DEFAULT_ALARM_SOUND_FILE_PATH).toURI().toString());
        mediaPlayer = new MediaPlayer(music);

        hasRung = DEFAULT_TIMER_MILLIS_VALUE <= 0;
    }
    
    public static String getDefaultTimerStringValue() {
        return MyFormatter.longMillisecondsTimeToTimeString(DEFAULT_TIMER_MILLIS_VALUE);
    }
    
    public long getTotalTime() {
        return totalTime;
    }
    
    TimerStates getState() {
        return state;
    }

    public long getRemainingTime() {
//        System.out.println("state: " + this.state.toString()
//                                   + "\ntotal time: " + totalTime
//                                   + "\nstored elapsed time: " + storedElapsedTime
//                                   + "\nstarted nano time: " + startNanoTime
//                                   + "\ncurrent nano time: " + System.nanoTime());
        if (this.state == TimerStates.STOPPED) {
            return this.totalTime;
        } else if (this.state == TimerStates.PAUSED) {
            return totalTime - storedElapsedTime;
        } else if (this.state == TimerStates.RUNNING) {
            return totalTime - storedElapsedTime - ((System.nanoTime() - startNanoTime)/1000000);
        }
        return -1;
    }

    public long getRemainingTimeAndRingOnPassing() {
        if (this.getRemainingTime() <= 0 && !hasRung) {
            hasRung = true;
            mediaPlayer.play();
        }
        return this.getRemainingTime();
    }
    
    @Override
    public boolean start() {
        if (this.state.isRunning() ) {
            logger.info("Already running.");
            return false;
        }
        
        this.startNanoTime = System.nanoTime();
        this.state = TimerStates.RUNNING;

        return true;
    }
    
    @Override
    public boolean stop() {
        this.stopAndUpdate(this.totalTime);
        return true;
    }

    public boolean stopAndUpdate(long timeToSet) {
        if (this.state == TimerStates.STOPPED) {
            logger.info("Already stopped.");
            return false;
        }
        logger.info("Stopping.");
        this.state = TimerStates.STOPPED;
        this.storedElapsedTime = 0;
        this.startNanoTime = 0;
        this.mediaPlayer.stop();
        this.totalTime = timeToSet;
        this.hasRung = this.totalTime <= 0;
        return true;
    }

    public void setNewTotalTime(long newTotalTime) {
        this.stopAndUpdate(newTotalTime);
    }

    public void refresh() {
        this.state = TimerStates.STOPPED;
    }

    @Override
    public boolean pause() {
        if(this.state == TimerStates.PAUSED) {
            logger.info("Already paused.");
            return false;
        }
        
        this.state = TimerStates.PAUSED;
        this.storedElapsedTime += ((System.nanoTime() - startNanoTime) / 1000000);
        
        return true;
    }

    @Override
    public boolean reset() {
        if (this.state != TimerStates.STOPPED) {
            this.state = TimerStates.STOPPED;
        }
        if (this.totalTime > 0) {
            this.hasRung = false;
            this.mediaPlayer.stop();
        }
        this.storedElapsedTime = 0;
        return true;
    }

    //        public static enum TimerStates {
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
    
//    public static void main(String[] args) {
//        MyTimer timer = new MyTimer("00:01:02.345");
//        TimerStates q = timer.getState();
//        System.out.println(q.toString());
//        q = TimerStates.STARTED;
//        System.out.println(q.toString());
//        System.out.println(timer.getState().toString());
//    }
}
