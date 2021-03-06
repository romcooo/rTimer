package com.romco.common;

import com.romco.controller.TimerTabController;
import com.romco.utilities.MyFormatter;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.InputMismatchException;

public class TimerHBox extends HBox {
    public static final String BUTTON_TEXT_START = "Start";
    
    private Button deleteButton;
    private Button resetButton;
    private TextField newTimerTextField;
    private Button startStopButton;
    private MyTimer timer;
    private TimerTabController controller;

    public TimerHBox(TimerTabController controller) {
        this.controller = controller;
        this.timer = new MyTimer();
        this.deleteButton = new Button("X");
        this.resetButton = new Button("Reset");
        this.newTimerTextField = new TextField();
        this.startStopButton = new Button(BUTTON_TEXT_START);

        this.deleteButton.setCancelButton(true);
        deleteButton.setOnAction(this::deleteThis);

        resetButton.setOnAction(this::resetTimer);

        newTimerTextField.setOnKeyReleased(this::updateTimerTotalTime);
        newTimerTextField.setOnMouseClicked(this::handleTimerFocused);
        newTimerTextField.setText(MyFormatter.longMillisecondsTimeToTimeString(timer.getRemainingTime()));

        startStopButton.setOnAction(this::toggleTimer);

        this.getChildren().addAll(
                                      deleteButton,
                                      resetButton,
                                      newTimerTextField,
                                      startStopButton);

        this.setAlignment(Pos.BASELINE_CENTER);
    }

    public TimerHBox(TimerTabController controller, MyTimer timer) {
        this(controller);
        this.timer = timer;
        refreshDisplayedTime(false);
    }

    public MyTimer getTimer() {
        return timer;
    }

    public void stopAndResetTimer() {
        if (timer.getState().isRunning()) {
            timer.stop();
        }
        timer.reset();
        startStopButton.setText(BUTTON_TEXT_START);
        refreshDisplayedTime(false);
    }
    
    private void toggleTimer(ActionEvent e) throws IllegalCallerException {
        if (!timer.getState().isRunning()) {
            startTimer();
        } else {
            pauseTimer();
        }
//        System.out.println(timers.get(defaultTimerHBox).getRemainingTime());
    }

    private boolean startTimer() throws IllegalArgumentException {
        if (timer.start()) {
            startStopButton.setText("Pause");
//            newTimerTextField.setEditable(false);
            return true;
        }
        return false;
    }

    public void startStoppedTimer() throws IllegalArgumentException {
        if (!timer.getState().isStarted()) { //isStarted -> has been started at least once
            this.startTimer();
        }
    }

    private boolean pauseTimer() {
        timer.pause();
        startStopButton.setText(BUTTON_TEXT_START);
//        newTimerTextField.setEditable(true);
        refreshDisplayedTime(false); //because the gui sync currently only happens when a timer is running,
        // this ensures it will be in sync after pause

        return true;
    }

    public void refreshDisplayedTime(boolean ringIfTime) throws IllegalArgumentException {
        if (ringIfTime) {
            newTimerTextField.setText(MyFormatter.longMillisecondsTimeToTimeString(timer.getRemainingTimeAndRingOnPassing()));
        } else {
            newTimerTextField.setText(MyFormatter.longMillisecondsTimeToTimeString(timer.getRemainingTime()));
        }
    }

    private void resetTimer(ActionEvent e) {
        timer.reset();
        startStopButton.setText(BUTTON_TEXT_START);
        refreshDisplayedTime(false);
    }

    private void updateTimerTotalTime(KeyEvent e) throws IllegalCallerException {
        long newTime;
        try {
            newTime = MyFormatter.timeStringToLongMillisecondsTime(newTimerTextField.getText());
            timer.setNewTotalTime(newTime);
            System.out.println("Time set to: " + MyFormatter.longMillisecondsTimeToTimeString(newTime));
        } catch (InputMismatchException ex) {
            ex.printStackTrace();
        }
//        System.out.println("new time in millis: " + newTime);
    }

    private void handleTimerFocused(MouseEvent mouseEvent) {
        if (!timer.getState().isRunning()) {
            timer.refresh();
        }
    }

    private void deleteThis(ActionEvent e) {
        timer.stop();
        controller.deleteTimerHBox(this);
    }
}
