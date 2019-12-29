package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TimerTabController {
    public static final int TIMER_HBOX_TEXTFIELD_INDEX = 1;
    public static final int TIMER_HBOX_STARTSTOP_BUTTON_INDEX = 2;
    
    @FXML
    private Tab timerTab;
    @FXML
    private HBox defaultTimerHBox;
    @FXML
    private TextField defaultTimerTextField;
    
    private Map<HBox, MyTimer> timers = new HashMap<>();
    
    private AnimationTimer timerTabAnimationTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            for (HBox hBox : timers.keySet()) {
                if (hBox.getChildren().get(TIMER_HBOX_TEXTFIELD_INDEX) instanceof TextField) {
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
        
//        if (timerTab.isSelected()) {
//            startTimeFieldUpdates();
//        }
    
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
        TextField textField = (TextField) parentHBox.getChildren().get(TIMER_HBOX_TEXTFIELD_INDEX);
        Button button = (Button) parentHBox.getChildren().get(TIMER_HBOX_STARTSTOP_BUTTON_INDEX);
        
        timers.get(parentHBox).start();
        button.setText("Pause");
        
        
        return true;
    }
    
    private boolean pauseTimer(HBox parentHBox) {
        if (!(parentHBox.getChildren().get(TIMER_HBOX_TEXTFIELD_INDEX) instanceof TextField) || !(parentHBox.getChildren().get(TIMER_HBOX_STARTSTOP_BUTTON_INDEX) instanceof Button)) {
            throw new IllegalArgumentException("Passed HBox does not contain the necessary fields;");
        }
        TextField textField = (TextField) parentHBox.getChildren().get(TIMER_HBOX_TEXTFIELD_INDEX);
        Button button = (Button) parentHBox.getChildren().get(TIMER_HBOX_STARTSTOP_BUTTON_INDEX);
    
        timers.get(parentHBox).pause();
        button.setText("Start");
        return true;
    }
    
    @FXML private boolean addTimer(ActionEvent e) {
        HBox newBox = new HBox();
        Button deleteButton = new Button();
        deleteButton.setText("X");
        deleteButton.setCancelButton(true);
        
        return true;
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
