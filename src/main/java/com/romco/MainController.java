package com.romco;

import com.romco.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class MainController {

    // = Fields
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    // == Panes
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private TabPane tabPane;

    // == Controllers
    @FXML
    private StopwatchTabController stopwatchTabController;
    @FXML
    private TimerTabController timerTabController;

    // == Tabs
    @FXML private Tab clockTab, timerTab, stopwatchTab;

//    @FXML
//    private void initialize() {
//        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
//            @Override
//            public void changed(ObservableValue<? extends Tab> observableValue, Tab tab, Tab t1) {
//
//            }
//        });
//    }
    
    @FXML
    private void setTabSelection() {
        System.out.println(tabPane.getSelectionModel().getSelectedItem().getId());
    }
    
    @FXML
    private void saveCurrentAs() {
        FileChooser fileChooser = new FileChooser();
        logger.info(tabPane.getSelectionModel().getSelectedItem().getId());
        switch (tabPane.getSelectionModel().getSelectedItem().getId()) {
            case "timerTab":
                fileChooser.setInitialFileName("timer.csv");
                break;
            case "stopwatchTab":
                // do not allow saving if a stopwatch is running
                // TODO place a checker method into the appropriate controller instead of iterating here
                for (StopWatch stopwatch : stopwatchTabController.getStopwatches()) {
                    if (stopwatch.isStarted() && !stopwatch.isSuspended()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Please stop stopwatches before saving.", ButtonType.OK);
                        alert.showAndWait();
                        return;
                    }
                }
                fileChooser.setInitialFileName("stopwatch.txt");
                break;
            case "clockTab":
                fileChooser.setInitialFileName("clock.txt");
                break;
            default:
                break;
        }

//        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext();
//        fileChooser.setInitialDirectory(new File(context.getEnvironment().getSystemProperties().get("user.home") + "/Desktop"));
//        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
//        logger.info("Initial directory set to: " + fileChooser.getInitialDirectory());

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("csv", "*.csv"),
                                                 new FileChooser.ExtensionFilter("All", "*.*"));
        
        File file = fileChooser.showSaveDialog(mainBorderPane.getScene().getWindow());
        if (file == null) {
            return; //user pressed cancel
        }
        
        if (clockTab.isSelected()){
        }

        if (timerTab.isSelected()) {
            Persistence.saveTimersAs(timerTabController.getCopyOfTimers(), file);
        }

        if (stopwatchTab.isSelected()) {
//            Collection<StopWatch> stopWatches = stopwatchTabController.getStopwatches();
//            for (StopWatch stopWatch : stopWatches) {
//                dataToWrite.append(stopWatch.getNanoTime());
//                dataToWrite.append(";\n");
//            }
        }


    }

}




