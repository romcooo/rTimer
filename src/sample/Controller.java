package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.time.StopWatch;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Controller {
    @FXML
    private TextField timer1;
    @FXML
    private Button start;

    StopWatch stopwatch = new StopWatch();

    @FXML
    public void startStopTimer(ActionEvent e) throws InterruptedException {
        if (start.getText().equals("START")) {
            startTimer();
        } else if (start.getText().equals("STOP")) {
            stopTimer();
        }
    }

    public void startTimer() {
        start.setText("STOP");

        if (stopwatch.isSuspended()) {
            stopwatch.resume();
        } else if (stopwatch.isStopped()) {
            stopwatch.start();
        }
        Runnable timeTracker = new Runnable() {
            @Override
            public void run() {
                while(stopwatch.isStarted()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            timer1.setText(String.format("%02d:%02d:%02d.%03d",
                                    stopwatch.getTime(TimeUnit.HOURS),
                                    stopwatch.getTime(TimeUnit.MINUTES),
                                    stopwatch.getTime(TimeUnit.SECONDS),
                                    stopwatch.getTime(TimeUnit.MILLISECONDS)
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

    public void stopTimer() {
        start.setText("START");

        if (stopwatch.isStarted()) {
            stopwatch.suspend();
        }

    }



}


