package com.romco;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.nio.file.FileSystems;
import java.util.*;

public class TimerTabController {
    public static final String DEFAULT_ALARM_SOUND_FILE_PATH = FileSystems.getDefault()
            .getPath("src", "main", "resources", "sourcefiles", "Alarm01.wav").toString();
    @FXML
    private Tab timerTab;
    @FXML
    private VBox timerCenterVBox1;
    @FXML
    private Button addTimerButtonTopDefault;
    @FXML
    private ToggleButton sequenceToggleButton;
    
//    private Map<HBox, MyTimer> timers = new HashMap<>();
    private List<TimerHBox> timerHBoxes = new ArrayList<>();
    
    private AnimationTimer timerTabAnimationTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            for (TimerHBox timerHBox : timerHBoxes) {
                if (timerHBox.getTimer().getState().isRunning()) {
                    timerHBox.refreshDisplayedTime(true);
                    if (sequenceToggleButton.isSelected() && timerHBox.getTimer().getRemainingTime() <= 0) {
                        int index = timerHBoxes.indexOf(timerHBox);
                        if (timerHBoxes.size() > index + 1) {
                            timerHBoxes.get(index + 1).startStoppedTimer();
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

    @FXML
    void deleteTimerHBox(TimerHBox timerHBoxToDelete) {
        timerHBoxes.remove(timerHBoxToDelete);
        timerCenterVBox1.getChildren().remove(timerHBoxToDelete);
    }

    public List<TimerHBox> getCopyOfTimerHBoxes() {
        return new ArrayList<>(this.timerHBoxes);
    }
}
