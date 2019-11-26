package sample;

import javafx.animation.AnimationTimer;
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

    private AnimationTimer t;

    @FXML
    public void onStart(ActionEvent e) throws InterruptedException {
        long nanoTime = System.nanoTime();
        start.setText("STOP");
//        timer1.setText(String.valueOf(nanoTime));
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        double x = 123;
        int y = 0;

    }

}
