package sample;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.commons.lang3.time.StopWatch;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainController {
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private TabPane tabPane;

    @FXML
    private StopwatchTabController stopwatchTabController;

    @FXML private Tab clockTab, timerTab, stopwatchTab;

    @FXML
    private void initialize() {

    }

    @FXML
    private void open() {

    }

    @FXML
    private void saveCurrentAs() throws IOException {
        // do not allow saving if a "clock" is running
        for (StopWatch stopwatch:stopwatchTabController.getStopwatches()) {
            if (stopwatch.isStarted() && !stopwatch.isSuspended()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please stop stopwatches before saving.", ButtonType.OK);
                alert.showAndWait();
//                System.out.println("Please stop stopwatches before saving.");
                return;
            }
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("stopwatches.txt");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text", "*.txt"),
                                                 new FileChooser.ExtensionFilter("All", "*.*"));
        
        File file = fileChooser.showSaveDialog(mainBorderPane.getScene().getWindow());
        if (file == null) {
            return; //user pressed cancel
        }
        
        Path path = Paths.get(file.getPath());
        StringBuilder dataToWrite = new StringBuilder();
        
        if (clockTab.isSelected()){

        }

        if (timerTab.isSelected()) {

        }

        if (stopwatchTab.isSelected()) {
            Collection<StopWatch> stopWatches = stopwatchTabController.getStopwatches();
            for (StopWatch stopWatch : stopWatches) {
                dataToWrite.append(stopWatch.getNanoTime());
                dataToWrite.append(";\n");
            }
        }
    
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(dataToWrite.toString());
        }

    }

}




