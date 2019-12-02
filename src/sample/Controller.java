package sample;


import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Controller {
    @FXML
    private TextField timer1;
    @FXML
    private Button start;

    private StopWatch stopwatch1 = new StopWatch();
//    private ExecutorService stopwatchExecutor;

    @FXML
    public void toggleTimer(ActionEvent e) {

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
                            timer1.setText(MyFormatter.longMillisecondsTimeToTimeString(
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
    public void addTimerTop() throws IOException {
        TextField textFieldNewTop = new TextField("0:00:00.000");

        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        root.getChildrenUnmodifiable().add(textFieldNewTop);

    }

    @FXML
    public void addTimerBottom() {

    }
//
//    void killStopwatches() {
//        this.stopwatchExecutor.shutdownNow();
//    }



}


