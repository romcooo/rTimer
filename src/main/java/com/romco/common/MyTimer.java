package com.romco.common;

import com.romco.controller.TimerTabController;
import com.romco.utilities.MyFormatter;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class MyTimer implements Startable {
    private static final long DEFAULT_TIMER_MILLIS_VALUE = 1000;
    private static final Logger logger = LoggerFactory.getLogger(MyTimer.class);

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

    public TimerStates getState() {
        return state;
    }

    public void setStartNanoTime(long startNanoTime) {
        this.startNanoTime = startNanoTime;
    }

    public void setStoredElapsedTime(long storedElapsedTime) {
        this.storedElapsedTime = storedElapsedTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public void setState(TimerStates state) {
        this.state = state;
    }

    public void setMusic(Media music) {
        this.music = music;
    }

    public void setHasRung(boolean hasRung) {
        this.hasRung = hasRung;
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

    public void stopAndUpdate(long timeToSet) {
        if (this.state == TimerStates.STOPPED) {
            logger.info("Already stopped.");
        } else {
            logger.info("Stopping.");
        }
        this.state = TimerStates.STOPPED;
        this.storedElapsedTime = 0;
        this.startNanoTime = 0;
        this.mediaPlayer.stop();
        this.totalTime = timeToSet;
        this.hasRung = this.totalTime <= 0;
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

    @Override
    public String toString() {
        return "startNanoTime=" + startNanoTime +
                ", storedElapsedTime=" + storedElapsedTime +
                ", totalTime=" + totalTime +
                ", state=" + state +
                ", musicSource=" + music.getSource() +
                ", hasRung=" + hasRung;
    }

    public static class MyTimerFactory {
        public static MyTimer fromString(String from) {
            MyTimer myTimer = new MyTimer();
            Map<String, String> map = new HashMap<>();
            String[] values = from.split(", ");
            for (String value : values) {
                logger.info(value);
                String[] pair = value.split("=");
                map.put(pair[0], pair[1]);
            }
            myTimer.startNanoTime = Long.parseLong(map.get("startNanoTime"));
            myTimer.storedElapsedTime = Long.parseLong(map.get("storedElapsedTime"));
            myTimer.totalTime = Long.parseLong(map.get("totalTime"));
            myTimer.state = TimerStates.getFromString(map.get("state"));
            try {
                if (!new File(map.get("musicSource")).exists()) {
                    throw new FileNotFoundException();
                }
                myTimer.music = new Media(map.get("musicSource"));
            } catch (MediaException | FileNotFoundException e) {
                logger.error("Cannot find music source at {}, using default.", map.get("musicSource"));
                myTimer.music = new Media(new File(TimerTabController.DEFAULT_ALARM_SOUND_FILE_PATH).toURI().toString());
            }
            myTimer.hasRung = Boolean.parseBoolean(map.get("hasRung"));
            logger.info("Built: " + myTimer.toString());
            return myTimer;
        }
    }

}
