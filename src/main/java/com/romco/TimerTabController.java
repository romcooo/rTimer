package com.romco;

import com.romco.utilities.MyFormatter;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.FileSystems;
import java.util.*;

public class TimerTabController {
    private static Logger logger = LoggerFactory.getLogger(TimerTabController.class);
    public static final String DEFAULT_ALARM_SOUND_FILE_PATH = FileSystems.getDefault()
            .getPath("src", "main", "resources", "sourcefiles", "Alarm01.wav").toString();
    private static final long DEFAULT_BREAK_BETWEEN_TIMERS_VALUE_MILLISECONDS = 1000;

    @FXML
    private Tab timerTab;
    @FXML
    private VBox timerCenterVBox1;
    @FXML
    private Button addTimerButtonTopDefault;
    @FXML
    private ToggleButton sequenceToggleButton;
    @FXML
    private CheckBox timeBetweenTimersCheckBox;
    @FXML
    private TextField timeBetweenTimersTextField;

    private List<TimerHBox> timerHBoxes = new ArrayList<>();
    
    private AnimationTimer timerTabAnimationTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            for (TimerHBox timerHBox : timerHBoxes) {
                if (timerHBox.getTimer().getState().isRunning()) {
                    timerHBox.refreshDisplayedTime(true);
                    if (sequenceToggleButton.isSelected() && timerHBox.getTimer().getRemainingTime() <= 0) {
                        long breakTime = 0;
                        // check for "break between timers"
                        if (timeBetweenTimersCheckBox.isSelected()) {
                            try {
                                breakTime = MyFormatter.timeStringToLongMillisecondsTime(
                                        timeBetweenTimersTextField.getText());
                                System.out.println(breakTime);
                            } catch (InputMismatchException e) {
                                logger.info(e.getMessage());
                            }
                        }
                        // -breakTime, because if break time is 1 second, it should wait until the timer is at -1000ms
                        if (timerHBox.getTimer().getRemainingTime() <= -breakTime) {
                            int index = timerHBoxes.indexOf(timerHBox);
                            if (timerHBoxes.size() > index + 1) {
                                timerHBoxes.get(index + 1).startStoppedTimer();
                            }
                        }
                    }
                }
            }
        }
    };
    
    @FXML
    protected void initialize() {
        TimerHBox defaultTimerHBox = new TimerHBox(this);
        timerCenterVBox1.getChildren().add(1, defaultTimerHBox);
        timerHBoxes.add(defaultTimerHBox);

        timeBetweenTimersCheckBox.disableProperty().bind(sequenceToggleButton.selectedProperty().not());

        timeBetweenTimersTextField.setText(
                MyFormatter.longMillisecondsTimeToTimeString(DEFAULT_BREAK_BETWEEN_TIMERS_VALUE_MILLISECONDS));
    }
    
    @FXML
    void handleSelectionChanged() {
        if (timerTab.isSelected()) {
            timerTabAnimationTimer.start();
        } else {
            timerTabAnimationTimer.stop();
        }
    }

    @FXML
    private boolean addTimer(ActionEvent e) {
        Button pressedButton;
        if (e.getSource() instanceof Button) {
            pressedButton = (Button) e.getSource();
        } else {
            return false;
        }

        TimerHBox newTimerHBox = new TimerHBox(this);
        int newHBoxIndex = timerCenterVBox1.getChildren().indexOf(pressedButton);
        if (pressedButton.equals(addTimerButtonTopDefault)) {
            newHBoxIndex++;
            timerHBoxes.add(0, newTimerHBox);
        } else {
            timerHBoxes.add(newTimerHBox);
        }
        timerCenterVBox1.getChildren().add(newHBoxIndex, newTimerHBox);
        return true;
    }

    public boolean addMultipleTimers(Collection<MyTimer> timers) {
        int timerHBoxIndex = 0;
        int hBoxIndex = 1;
        for (MyTimer myTimer : timers) {
            TimerHBox newTimerHBox = new TimerHBox(this, myTimer);
            timerHBoxes.add(timerHBoxIndex++, newTimerHBox);
            timerCenterVBox1.getChildren().add(hBoxIndex++, newTimerHBox);
        }
        return true;
    }

    @FXML
    void deleteTimerHBox(TimerHBox timerHBoxToDelete) {
        timerHBoxes.remove(timerHBoxToDelete);
        timerCenterVBox1.getChildren().remove(timerHBoxToDelete);
    }

    void deleteAllTimers() {
        for (TimerHBox timerHBox : timerHBoxes) {
            timerHBoxes.remove(timerHBox);
            timerCenterVBox1.getChildren().remove(timerHBox);
        }
    }

    List<MyTimer> getCopyOfTimers() {
        ArrayList<MyTimer> myTimers = new ArrayList<>();
        for (TimerHBox timerHBox : timerHBoxes) {
            myTimers.add(timerHBox.getTimer());
        }
        return myTimers;
    }
}
