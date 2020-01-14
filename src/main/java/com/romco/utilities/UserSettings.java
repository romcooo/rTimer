package com.romco.utilities;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.Map;

public class UserSettings {
    //values pre-defined below are for case no file with settings is found
    private String defaultTab = "timerTab";
    private int defaultWidth = 800;
    private int defaultHeight = 600;
    
    public UserSettings() {
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
    
    public static UserSettings loadSettingsFromYaml(InputStream input) {
        Constructor c = new Constructor(UserSettings.class);
        Yaml yaml = new Yaml(c);
        UserSettings settings = yaml.loadAs(input, UserSettings.class);
        System.out.println(settings);
        System.out.println(yaml.dump(settings));
        return settings;
    }
}
