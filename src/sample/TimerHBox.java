package sample;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.util.InputMismatchException;

public class TimerHBox extends HBox {
    public static final int TIMER_HBOX_TEXTFIELD_INDEX = 2;
    public static final int TIMER_HBOX_STARTSTOP_BUTTON_INDEX = 3;
    public static final String DEFAULT_ALARM_SOUND_FILE_PATH = "I:\\\\Documents\\\\lib\\\\Sounds\\\\Alarm01.wav";

    Button deleteButton;
    Button resetButton;
    TextField newTimerTextField;
    Button startStopButton;
    MyTimer timer;
    TimerTabController controller;

    public TimerHBox(TimerTabController controller) {
        this.controller = controller;
        this.deleteButton = new Button("X");
        this.resetButton = new Button("Reset");
        this.newTimerTextField = new TextField();
        this.startStopButton = new Button("Start");

        this.deleteButton.setCancelButton(true);
        deleteButton.setOnAction(this::deleteThis);
        resetButton.setOnAction(this::resetTimer);
        newTimerTextField.setOnKeyReleased(this::updateTimerTotalTime);
        startStopButton.setOnAction(this::toggleTimer);

        this.timer = new MyTimer();

        newTimerTextField.setText(MyFormatter.longMillisecondsTimeToTimeString(timer.getRemainingTime()));

        this.getChildren().addAll( //Order matters, see indices in constants
                                      deleteButton,
                                      resetButton,
                                      newTimerTextField,
                                      startStopButton);

        this.setAlignment(Pos.BASELINE_CENTER);
    }

    public MyTimer getTimer() {
        return timer;
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
            return true;
        }
        return false;
    }

    private boolean pauseTimer() {
        timer.pause();
        startStopButton.setText("Start");

        updateTimerTextField(); //because the gui sync currently only happens when a timer is running,
        // this ensures it will be in sync after pause

        return true;
    }

    void updateTimerTextField() throws IllegalArgumentException {
        newTimerTextField.setText(MyFormatter.longMillisecondsTimeToTimeString(timer.getRemainingTime()));
    }

    private void resetTimer(ActionEvent e) {
        timer.reset();
        updateTimerTextField();
    }

    void updateTimerTotalTime(KeyEvent e) throws IllegalCallerException {
        long newTime;
        try {
            newTime = MyFormatter.timeStringToLongMillisecondsTime(newTimerTextField.getText());
        } catch (InputMismatchException ex) {
            ex.printStackTrace();
            return;
        }

        timer.setNewTotalTime(newTime);
        System.out.println("Time set to: " + MyFormatter.longMillisecondsTimeToTimeString(newTime));
//        System.out.println("new time in millis: " + newTime);
    }

    private void deleteThis(ActionEvent e) {
        timer.stop();
        controller.deleteTimerHBox(this);
    }
}
