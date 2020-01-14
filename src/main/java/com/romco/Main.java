package com.romco;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        String sceneFile = "/../../../resources/fxml/mainWindow.fxml";
//        Parent testRoot = null;
//        URL url  = null;
//        try
//        {
//            url  = getClass().getResource( sceneFile );
//            testRoot = FXMLLoader.load( url );
//            System.out.println( "  fxmlResource = " + sceneFile );
//        }
//        catch ( Exception ex )
//        {
//            System.out.println( "Exception on FXMLLoader.load()" );
//            System.out.println( "  * url: " + url );
//            System.out.println( "  * " + ex );
//            System.out.println( "    ----------------------------------------\n" );
//            throw ex;
//        }
//        System.out.println(getClass().getResource("../../../resources/fxml/mainWindow.fxml"));
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
    
    private void initialize() {
        try (ObjectInputStream locFile = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(
                                "C:\\Users\\roman.stubna\\OneDrive - Home Credit International a.s\\" +
                                        "Personal\\git\\MorTimer\\src\\sample\\datastore\\userSettings.yaml")))) {
            
    
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
