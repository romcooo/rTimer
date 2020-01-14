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
        UserSettings settings = initializeSettings();
        int w = 800;
        int h = 600;
        if (settings != null) {
            w = settings.getDefaultWidth();
            h = settings.getDefaultHeight();
        }
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainWindow.fxml"));
        primaryStage.setTitle("Mortimer");
        primaryStage.setScene(new Scene(root, w, h));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        //storeSettingsOnClose
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    private UserSettings initializeSettings() {
        InputStream inputStream = getClass().getResourceAsStream("/datastore/userSettings.yaml");
        if (inputStream == null) {
            System.out.println("using default class");
            return new UserSettings();
        } else {
            UserSettings settings = UserSettings.loadSettingsFromYaml(inputStream);
            System.out.println(settings.getDefaultHeight());
            return settings;
        }
    }
}
