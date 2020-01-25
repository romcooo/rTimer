package com.romco;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.romco.utilities.UserSettings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {
    
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

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
//        UserSettings.getInstance().setDefaultHeight(800);
//        UserSettings.getInstance().setDefaultWidth(600);
//        UserSettings.storeSettingsToYaml();
        logger.info("Stopping.");
        super.stop();
    }

    public static void main(String[] args) {
//        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext();
//        logger.info(context.getEnvironment().getSystemProperties().get("user.home").toString());
        logger.info(System.getProperty("user.home"));
        launch(args);
    }

}
