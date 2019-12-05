package sample;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class StopwatchTabController {

    private static final String STOPWATCH_HBOX_ID_PREFIX = "stopwatchHBox";
    private static final String STOP_BUTTON_TEXT = "Stop";
    private static final String START_BUTTON_TEXT = "Start";

    //start at 1 because 0 is taken by the default hbox
    private static int hBoxId = 1;

    @FXML
    private TextField defaultStopwatch1TextField;

    @FXML
    private Button defaultStartButton;
    @FXML
    private VBox stopwatchCenterVBox1;
    @FXML
    private Button addStopwatchButtonTopDefault;
    @FXML
    private Button startAllStopwatchesButton;
    @FXML
    private HBox defaultStopwatchHBox;

    private Map<HBox, StopWatch> stopwatches = new HashMap<>();

//    private ExecutorService stopwatchExecutor;

    @FXML
    protected void initialize() {
        stopwatches.put(defaultStopwatchHBox, new StopWatch());
    }

    public void startAllStopwatches() {
        //TODO
        System.out.println("asd");
    }

    @FXML
    public void toggleStopwatch(ActionEvent e) {
        Button pressedButton;
        HBox parentHBox;
        if (e.getSource() instanceof Button) {
            pressedButton = (Button) e.getSource();
        } else {
            return;
        }
        if (pressedButton.getParent() instanceof HBox) {
            parentHBox = (HBox) pressedButton.getParent();
        } else {
            return;
        }

        StopWatch currentStopwatch = stopwatches.get(parentHBox);

        if (currentStopwatch.isStopped() || currentStopwatch.isSuspended()) {
            startResume(parentHBox);
        } else if (currentStopwatch.isStarted()) {
            pause(parentHBox);
        }
    }

    @FXML
    private void startResume(HBox stopwatchHBox) {
        TextField textField = (TextField) stopwatchHBox.getChildren().get(1);
        Button startButton = (Button) stopwatchHBox.getChildren().get(2);
        StopWatch stopWatch = stopwatches.get(stopwatchHBox);

        startButton.setText("Pause");

        if (stopWatch.isSuspended()) {
            stopWatch.resume();
        } else if (stopWatch.isStopped()) {
            stopWatch.start();
        }

        Runnable timeTracker = new Runnable() {
            @Override
            public void run() {
                while(stopWatch.isStarted()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            textField.setText(MyFormatter.longMillisecondsTimeToTimeString(
                                    stopWatch.getTime(TimeUnit.MILLISECONDS))
                            );
                        }
                    });

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        };

        Thread daemonStopwatch = new Thread(timeTracker);
        // thread is set to daemon so that the application terminates correctly when user closes the main window.
        // TODO look at ExecutorServices or Tasks whether this can be handled better
        daemonStopwatch.setDaemon(true);
        daemonStopwatch.start();

//         stopwatchExecutor = Executors.newSingleThreadScheduledExecutor();
//         stopwatchExecutor.execute(timeTracker);
        Task task;
        task = new Task() {
            @Override
            protected Object call() throws Exception {
                return null;
            }
        };

        Service service;
        service = new Service() {
            @Override
            protected Task createTask() {
                return null;
            }
        };

    }

    @FXML
    private void pause(HBox stopwatchHBox) {
        Button startButton = (Button) stopwatchHBox.getChildren().get(2);
        StopWatch stopWatch = stopwatches.get(stopwatchHBox);

        startButton.setText("Start");
        if (!stopWatch.isSuspended() && !stopWatch.isStopped()) {
            stopWatch.suspend();
        }
    }

    @FXML
    private void stop(HBox stopwatchHBox) {
        StopWatch stopWatch = stopwatches.get(stopwatchHBox);
        if (!stopWatch.isSuspended() && !stopWatch.isStopped()) {
            stopWatch.stop();
        }
    }

    @FXML
    public boolean addStopwatch(ActionEvent e) {
        HBox newTimerHBox = new HBox();
        newTimerHBox.setAlignment(Pos.BASELINE_CENTER);
        newTimerHBox.setId(STOPWATCH_HBOX_ID_PREFIX + hBoxId++);
        Button pressedButton;

        if (e.getSource() instanceof Button) {
            pressedButton = (Button) e.getSource();
        } else {
            return false;
        }

        // triple check
        if (pressedButton == null) {
            return false;
        }

        Button deleteButton = new Button("X");
        deleteButton.setCancelButton(true);
        deleteButton.setOnAction(this::deleteStopwatchHBox);

        Button toggleButton = new Button("Start");
        toggleButton.setOnAction(this::toggleStopwatch);

        int pressedButtonIndex = stopwatchCenterVBox1.getChildren().indexOf(pressedButton);

        //If top button, new field should be created below it - increase index by one
        if(pressedButton.equals(addStopwatchButtonTopDefault)) pressedButtonIndex++;

        TextField textFieldNew = new TextField("0:00:00.000");

        stopwatches.put(newTimerHBox, new StopWatch());

        newTimerHBox.getChildren().add(0, deleteButton);
        newTimerHBox.getChildren().add(1, textFieldNew);
        newTimerHBox.getChildren().add(2, toggleButton);
        stopwatchCenterVBox1.getChildren().add(pressedButtonIndex, newTimerHBox);

        return true;
    }

    @FXML
    public void deleteStopwatchHBox(ActionEvent e) {
        Button pressedButton;

        if (e.getSource() instanceof Button) {
            pressedButton = (Button) e.getSource();
        } else {
            return;
        }
        stop((HBox) pressedButton.getParent());
        stopwatches.remove((HBox) pressedButton.getParent());
        //remove the entire HBox
        stopwatchCenterVBox1.getChildren().remove(pressedButton.getParent());
    }
//
//    void killStopwatches() {
//        this.stopwatchExecutor.shutdownNow();
//    }

    abstract static class DeleteButtonBuilder {
        static Button getDeleteButton(EventHandler<ActionEvent> e) {
            Button deleteButton = new Button("X");
            deleteButton.setCancelButton(true);
            deleteButton.setOnAction(e);
            return deleteButton;
        }
    }

    Collection<StopWatch> getStopwatches() {
        return this.stopwatches.values();
    }
}
