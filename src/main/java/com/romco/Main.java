package com.romco;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        initializeSettings();
        
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainWindow.fxml"));
        primaryStage.setTitle("Mortimer");
        primaryStage.setScene(new Scene(root, 800, 700));
        primaryStage.show();
        
        
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    private void initializeSettings() {
        InputStream inputStream = getClass().getResourceAsStream("/datastore/userSettings.yaml");
        try (Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                System.out.println(nextLine);
                if (nextLine.isBlank()) {
                    System.out.println("blank");
                    
                }
            }
        }
    }
}
