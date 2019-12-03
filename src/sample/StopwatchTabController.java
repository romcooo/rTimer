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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class StopwatchTabController {

    private static int stopwatchId;

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

    private StopWatch defaultStopwatch1 = new StopWatch();
//    private ExecutorService stopwatchExecutor;

    @FXML
    protected void initialize() {
        stopwatches.put(defaultStopwatchHBox, defaultStopwatch1);
    }

    public void startAllStopwatches() {
        //TODO
        System.out.println("asd");
    }


    @FXML
    public void toggleStopwatch(ActionEvent e) {
        Button pressedButton;
        if (e.getSource() instanceof Button) {
            pressedButton = (Button) e.getSource();
        }
        //TODO make it so that the stopwatch corresponding to the button from the same HBOX is toggled

        if (defaultStopwatch1.isStopped() || defaultStopwatch1.isSuspended()) {
            startResumeTimer();
        } else if (defaultStopwatch1.isStarted()) {
            pauseTimer();
        }
    }

    @FXML
    public void startResumeTimer() {
        defaultStartButton.setText("PAUSE");

        if (defaultStopwatch1.isSuspended()) {
            defaultStopwatch1.resume();
        } else if (defaultStopwatch1.isStopped()) {
            defaultStopwatch1.start();
        }
        Runnable timeTracker = new Runnable() {
            @Override
            public void run() {
                while(defaultStopwatch1.isStarted()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            defaultStopwatch1TextField.setText(MyFormatter.longMillisecondsTimeToTimeString(
                                    defaultStopwatch1.getTime(TimeUnit.MILLISECONDS))
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

        Thread daemonStopwatch1 = new Thread(timeTracker);
        // thread is set to daemon so that the application terminates correctly when user closes the main window.
        // TODO look at ExecutorServices or Tasks whether this can be handled better
        daemonStopwatch1.setDaemon(true);
        daemonStopwatch1.start();

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
    public void pauseTimer() {
        defaultStartButton.setText("START");

        if (defaultStopwatch1.isStarted()) {
            defaultStopwatch1.suspend();
        }
    }

    @FXML
    public boolean addStopwatch(ActionEvent e) {
        HBox newTimerHBox = new HBox();
        newTimerHBox.setAlignment(Pos.BASELINE_CENTER);
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
//        deleteButton.setId("cancelButton" + centerVBox1.getChildren().size());
        deleteButton.setOnAction(this::deleteStopwatchHBox);

        Button toggleButton = new Button("Start");
        toggleButton.setOnAction(this::toggleStopwatch);

        int pressedButtonIndex = stopwatchCenterVBox1.getChildren().indexOf(pressedButton);

        //If top button, new field should be created below it - increase index by one
        if(pressedButton.equals(addStopwatchButtonTopDefault)) pressedButtonIndex++;

        TextField textFieldNew = new TextField("0:00:00.000");

        stopwatches.put(newTimerHBox, new StopWatch());

        newTimerHBox.getChildren().add(deleteButton);
        newTimerHBox.getChildren().add(textFieldNew);
        newTimerHBox.getChildren().add(toggleButton);
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

}
