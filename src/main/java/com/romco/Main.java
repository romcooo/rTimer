package com.romco;

import com.romco.utilities.UserSettings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.Collections;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //load settings
        UserSettings settings = UserSettings.loadSettingsFromYaml();

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainWindow.fxml"));
        primaryStage.setTitle("Mortimer");
        primaryStage.setScene(new Scene(root, settings.getDefaultWidth(), settings.getDefaultHeight()));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainWindow.fxml"));
//        UserSettings.getInstance().setDefaultHeight((int) root.getScene().getHeight());
//        UserSettings.getInstance().setDefaultWidth((int) root.getScene().getWidth());
//        UserSettings.storeSettingsToYaml();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
