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

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class StopwatchTabMainController  {

    @FXML
    private TextField stopwatch1TextField;
    @FXML
    private Button start;
    @FXML
    private VBox stopwatchCenterVBox1;
    @FXML
    private Button addStopwatchButtonTopDefault;
    @FXML
    private Button startAllStopwatchesButton;

    private ArrayList<StopWatch> stopWatches = new ArrayList<>();

    private StopWatch stopwatch1 = new StopWatch();
//    private ExecutorService stopwatchExecutor;

    public void startAllStopwatches() {
        System.out.println("asd");
    }


    @FXML
    public void toggleStopwatch(ActionEvent e) {

        if (stopwatch1.isStopped() || stopwatch1.isSuspended()) {
            startResumeTimer();
        } else if (stopwatch1.isStarted()) {
            pauseTimer();
        }
    }

    @FXML
    public void startResumeTimer() {
        start.setText("PAUSE");

        if (stopwatch1.isSuspended()) {
            stopwatch1.resume();
        } else if (stopwatch1.isStopped()) {
            stopwatch1.start();
        }
        Runnable timeTracker = new Runnable() {
            @Override
            public void run() {
                while(stopwatch1.isStarted()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stopwatch1TextField.setText(MyFormatter.longMillisecondsTimeToTimeString(
                                    stopwatch1.getTime(TimeUnit.MILLISECONDS))
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
        start.setText("START");

        if (stopwatch1.isStarted()) {
            stopwatch1.suspend();
        }
    }

    @FXML
    public void addStopwatch(ActionEvent e) {
        HBox newTimerHBox = new HBox();
        newTimerHBox.setAlignment(Pos.BASELINE_CENTER);
        Button pressedButton;

        if (e.getSource() instanceof Button) {
            pressedButton = (Button) e.getSource();
        } else {
            return;
        }

        Button deleteButton = new Button("X");
        deleteButton.setCancelButton(true);
//        deleteButton.setId("cancelButton" + centerVBox1.getChildren().size());
        deleteButton.setOnAction(this::deleteStopwatchHBox);

        int pressedButtonIndex = stopwatchCenterVBox1.getChildren().indexOf(pressedButton);

        //If top button, new field should be created below it - increase index by one
        if(pressedButton.equals(addStopwatchButtonTopDefault)) pressedButtonIndex++;

        TextField textFieldNew = new TextField("0:00:00.000");
        newTimerHBox.getChildren().add(deleteButton);
        newTimerHBox.getChildren().add(textFieldNew);
        stopwatchCenterVBox1.getChildren().add(pressedButtonIndex, newTimerHBox);
    }

    @FXML
    public void deleteStopwatchHBox(ActionEvent e) {
        Button pressedButton;

        if (e.getSource() instanceof Button) {
            pressedButton = (Button) e.getSource();
        } else {
            return;
        }

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
