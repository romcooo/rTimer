package sample;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.commons.lang3.time.StopWatch;

import java.io.File;
import java.io.IOException;

public class MainController {
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private TabPane tabPane;

    @FXML
    private StopwatchTabController stopwatchTabController;

    @FXML private Tab clockTab, timerTab, stopwatchTab;

    @FXML
    private void open() {

    }

    @FXML
    private void saveCurrentAs() {
        // do not allow saving if a "clock" is running
        for (StopWatch stopwatch:stopwatchTabController.getStopwatches()) {
            if (stopwatch.isStarted() && !stopwatch.isSuspended()) {
                System.out.println("Please stop stopwatches before saving.");
                return;
            }
        }

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(mainBorderPane.getScene().getWindow());

        if (clockTab.isSelected()){

        }

        if (timerTab.isSelected()) {

        }

        if (stopwatchTab.isSelected()) {

        }

    }

}




