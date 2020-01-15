package com.romco;

import com.romco.utilities.MyFormatter;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.Calendar;

public class ClockTabController {
    @FXML
    private Tab clockTab;
    @FXML
    private TextField clock1;
    
    private StringProperty timeStringProperty = new SimpleStringProperty();
    
    //test
    @FXML
    TextField test;
    StringProperty prop = new SimpleStringProperty("0");
    
    @FXML
    private void initialize() {
        bindToTime();
        clock1.textProperty().bind(timeStringProperty);
    
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                prop.set(MyFormatter.longMillisecondsTimeToTimeString(l/1000000));
            }
        };
        timer.start();
        test.textProperty().bind(prop);
    }
    
    private void bindToTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(0), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Calendar time = Calendar.getInstance();
                        StringBuilder sb = new StringBuilder();
                        sb.append(time.get(Calendar.HOUR_OF_DAY)).append(":")
                                .append(time.get(Calendar.MINUTE)).append(":")
                                .append(time.get(Calendar.SECOND)).append("")
                                .append(time.get(Calendar.MILLISECOND));
                        timeStringProperty.set(sb.toString());
                    }
                }), new KeyFrame(Duration.millis(100))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
