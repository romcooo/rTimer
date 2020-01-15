package com.romco.utilities;

import com.romco.Main;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.net.URL;
import java.nio.file.FileSystems;

public class UserSettings {
    private static UserSettings instance;
    //values pre-defined below are for case no file with settings is found
    private String defaultTab;
    private int defaultWidth;
    private int defaultHeight;
    
    private UserSettings() {
        //this class is only created via this constructor if no user
        //settings file exists - this is a "default" implementation
        defaultTab = "timerTab";
        defaultWidth = 800;
        defaultHeight = 600;
    }

    public String getDefaultTab() {
        return defaultTab;
    }

    public void setDefaultTab(String defaultTab) {
        this.defaultTab = defaultTab;
    }

    public int getDefaultWidth() {
        return defaultWidth;
    }

    public void setDefaultWidth(int defaultWidth) {
        this.defaultWidth = defaultWidth;
    }

    public int getDefaultHeight() {
        return defaultHeight;
    }

    public void setDefaultHeight(int defaultHeight) {
        this.defaultHeight = defaultHeight;
    }

    public synchronized static UserSettings getInstance() {
        if (instance == null) {
            instance = loadSettingsFromYaml();
        }
        return instance;
    }

    public synchronized static UserSettings loadSettingsFromYaml() {
        if (instance == null) {
            InputStream inputStream = Main.class.getResourceAsStream("/data/userSettings.yaml");
            if (inputStream == null) {
                System.out.println("using default class and creating settings yaml file");
                UserSettings userSettings = new UserSettings();
                instance = userSettings;
            } else {
                Constructor c = new Constructor(UserSettings.class);
                Yaml yaml = new Yaml(c);
                UserSettings settings = yaml.loadAs(inputStream, UserSettings.class);
                System.out.println(settings);
                System.out.println(yaml.dump(settings));
                instance = settings;
            }
        }
        return instance;
    }

    public synchronized static boolean storeSettingsToYaml() {
        if (instance == null) {
            System.out.println("No settings to store!");
            return false;
        }
        Yaml yaml = new Yaml();
        try {
            System.out.println(FileSystems.getDefault().toString());
            FileWriter fileWriter = new FileWriter(new File("C:\\Users\\roman.stubna\\OneDrive - Home Credit International a.s\\Personal\\git\\MorTimer\\src\\main\\resources\\data\\userSettings.yaml"));
            System.out.println(yaml.dump(instance));
            yaml.dump(instance, fileWriter);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
