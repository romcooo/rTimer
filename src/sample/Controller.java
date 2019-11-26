package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.TimeUnit;

public class Controller {
    @FXML
    private TextField timer1;
    @FXML
    private Button start;

    StopWatch stopwatch1 = new StopWatch();

    @FXML
    public void toggleTimer(ActionEvent e) throws InterruptedException {

        if (stopwatch1.isStopped() || stopwatch1.isSuspended()) {
            startResumeTimer();
        } else if (stopwatch1.isStarted()) {
            pauseTimer();
        }
    }

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
                            timer1.setText(String.format("%02d:%02d:%02d.%03d",
                                    stopwatch1.getTime(TimeUnit.HOURS),
                                    stopwatch1.getTime(TimeUnit.MINUTES),
                                    stopwatch1.getTime(TimeUnit.SECONDS),
                                    stopwatch1.getTime(TimeUnit.MILLISECONDS)
                            ));
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

        new Thread(timeTracker).start();

    }

    public void pauseTimer() {
        start.setText("START");

        if (stopwatch1.isStarted()) {
            stopwatch1.suspend();
        }

    }



}


