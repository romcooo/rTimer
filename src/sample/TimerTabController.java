package sample;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class TimerTabController {
    public static final int TIMER_HBOX_TEXTFIELD_INDEX = 1;
    public static final int TIMER_HBOX_STARTSTOP_BUTTON_INDEX = 2;
    
    @FXML
    private Tab timerTab;
    @FXML
    private HBox defaultTimerHBox;
    @FXML
    private TextField defaultTimerTextField;
    @FXML
    private VBox timerCenterVBox1;
    @FXML
    private Button addTimerButtonTopDefault;
    
    private Map<HBox, MyTimer> timers = new HashMap<>();
    
    private AnimationTimer timerTabAnimationTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            for (HBox hBox : timers.keySet()) {
                if (hBox.getChildren().get(TIMER_HBOX_TEXTFIELD_INDEX) instanceof TextField
                        && timers.get(hBox).getState().isRunning()) {
                    TextField currentField = (TextField) hBox.getChildren().get(TIMER_HBOX_TEXTFIELD_INDEX);
                    currentField.setText(MyFormatter.longMillisecondsTimeToTimeString(
                            timers.get(hBox).getRemainingTime())
                                        );
                }
            }
        }
    };
    
    @FXML
    protected void initialize() {
        timers.put(defaultTimerHBox, new MyTimer());
    }
    
    @FXML
    void handleSelectionChanged() {
        if (timerTab.isSelected()) {
            timerTabAnimationTimer.start();
        } else {
            timerTabAnimationTimer.stop();
        }
    }
    
    private void updateTimerTextField(HBox hBox) throws IllegalArgumentException {
        TextField currentTextField;
        if (hBox.getChildren().get(TIMER_HBOX_TEXTFIELD_INDEX) instanceof TextField) {
            currentTextField = (TextField) hBox.getChildren().get(TIMER_HBOX_TEXTFIELD_INDEX);
        } else {
            throw new IllegalArgumentException("Input hBox doesn't contain a textField where expected;");
        }
        
        MyTimer currentTimer = timers.get(hBox);
        currentTextField.setText(MyFormatter.longMillisecondsTimeToTimeString(currentTimer.getRemainingTime()));
    }
    
    @FXML
    public void toggleTimer(ActionEvent e) throws IllegalCallerException {
        Button pressedButton;
        HBox parentHBox;
        
        if (e.getSource() instanceof Button) {
            pressedButton = (Button) e.getSource();
        } else {
            throw new IllegalCallerException("Exception during startTimer() - function triggered by a component not corresponding to a specific timer;");
        }
        if (pressedButton.getParent() instanceof HBox) {
            parentHBox = (HBox) pressedButton.getParent();
        } else {
            throw new IllegalCallerException("Exception during startTimer() - function triggered by a component without a parent HBox;");
        }
    
        MyTimer currentTimer = timers.get(parentHBox);
        
        if (!currentTimer.getState().isRunning()) {
            startTimer(parentHBox);
        } else {
            pauseTimer(parentHBox);
        }
        
        System.out.println(timers.get(defaultTimerHBox).getRemainingTime());
    }
    
    private boolean startTimer(HBox parentHBox) throws IllegalArgumentException {
        if (!(parentHBox.getChildren().get(TIMER_HBOX_TEXTFIELD_INDEX) instanceof TextField)
                || !(parentHBox.getChildren().get(TIMER_HBOX_STARTSTOP_BUTTON_INDEX) instanceof Button)) {
            throw new IllegalArgumentException("Passed HBox does not contain the necessary fields;");
        }
        Button button = (Button) parentHBox.getChildren().get(TIMER_HBOX_STARTSTOP_BUTTON_INDEX);
        
        timers.get(parentHBox).start();
        button.setText("Pause");
        
        return true;
    }
    
    private boolean pauseTimer(HBox parentHBox) {
        if (!(parentHBox.getChildren().get(TIMER_HBOX_TEXTFIELD_INDEX) instanceof TextField) || !(parentHBox.getChildren().get(TIMER_HBOX_STARTSTOP_BUTTON_INDEX) instanceof Button)) {
            throw new IllegalArgumentException("Passed HBox does not contain the necessary fields;");
        }
        Button button = (Button) parentHBox.getChildren().get(TIMER_HBOX_STARTSTOP_BUTTON_INDEX);
    
        timers.get(parentHBox).pause();
        button.setText("Start");
    
        updateTimerTextField(parentHBox); //because the gui sync currently only happens when a timer is running,
        // this ensures it will be in sync after pause
        
        return true;
    }
    
    @FXML private boolean addTimer(ActionEvent e) {
        Button pressedButton;
        if (e.getSource() instanceof Button) {
            pressedButton = (Button) e.getSource();
        } else {
            return false;
        }
        
        HBox newHBox = new HBox();
        
        Button deleteButton = new Button("X");
        deleteButton.setCancelButton(true);
        deleteButton.setOnAction(this::deleteTimerHBox);
    
        TextField newTimerTextField = new TextField();
        newTimerTextField.setOnKeyReleased(this::updateTimerTotalTime);
        
        Button startStopButton = new Button("Start");
        startStopButton.setOnAction(this::toggleTimer);
        
        newHBox.getChildren().addAll(deleteButton, newTimerTextField, startStopButton);
        newHBox.setAlignment(Pos.BASELINE_CENTER);
        
        MyTimer newTimer = new MyTimer();
        timers.put(newHBox, newTimer);
        
        newTimerTextField.setText(MyFormatter.longMillisecondsTimeToTimeString(newTimer.getRemainingTime()));
    
        int newHBoxIndex = timerCenterVBox1.getChildren().indexOf(pressedButton);
        if (pressedButton.equals(addTimerButtonTopDefault)) newHBoxIndex++;
        timerCenterVBox1.getChildren().add(newHBoxIndex, newHBox);
        
        return true;
    }
    
    @FXML void updateTimerTotalTime(KeyEvent e) throws IllegalCallerException {
        TextField updatedTextField;
        if (e.getSource() instanceof TextField) {
            updatedTextField = (TextField) e.getSource();
        } else {
            throw new IllegalCallerException("Source not a text field;");
        }
        
        HBox parentHBox;
        if (updatedTextField.getParent() instanceof HBox) {
            parentHBox = (HBox) updatedTextField.getParent();
        } else {
            throw new IllegalCallerException("Parent not an HBox;");
        }
        
        MyTimer timerToUpdate;
        if (timers.containsKey(parentHBox)) {
            timerToUpdate = timers.get(parentHBox);
        } else {
            throw new IllegalCallerException("No corresponding timer found;");
        }
    
        long newTime;
        try {
            newTime = MyFormatter.timeStringToLongMillisecondsTime(updatedTextField.getText());
        } catch (InputMismatchException ex) {
            ex.printStackTrace();
            return;
        }
    
        timerToUpdate.setNewTotalTime(newTime);
        System.out.println("new time: " + MyFormatter.longMillisecondsTimeToTimeString(newTime));
        System.out.println("new time in millis: " + newTime);
    }
    
    @FXML
    private void deleteTimerHBox(ActionEvent e) {
        Button pressedButton;
        if (e.getSource() instanceof Button) {
            pressedButton = (Button) e.getSource();
        } else {
            return;
        }
        
        HBox parentHBox;
        if (pressedButton.getParent() instanceof HBox) {
            parentHBox = (HBox) pressedButton.getParent();
        } else {
            return;
        }
        
        timers.get(parentHBox).stop();
        timerCenterVBox1.getChildren().remove(parentHBox);
    }
    
//    public void startTimeFieldUpdates() {
//
//        Runnable timeTracker = new Runnable() {
//            @Override
//            public void run() {
//                while(timerTab.isSelected()) {
//                    Platform.runLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            for (HBox hBox : timers.keySet()) {
//                                if (hBox.getChildren().get(1) instanceof TextField) {
//                                    TextField currentField = (TextField) hBox.getChildren().get(1);
//                                    currentField.setText(MyFormatter.longMillisecondsTimeToTimeString(
//                                            timers.get(hBox).getRemainingTime())
//                                                        );
//                                }
//                            }
//                        }
//                    });
//
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException ex) {
//                        ex.printStackTrace();
//                    }
//
//                }
//            }
//        };
//        Thread daemonStopwatch = new Thread(timeTracker);
//        // thread is set to daemon so that the application terminates correctly when user closes the main window.
//        // TODO look at ExecutorServices or Tasks whether this can be handled better
//        daemonStopwatch.setDaemon(true);
//        daemonStopwatch.start();
//    }

}
