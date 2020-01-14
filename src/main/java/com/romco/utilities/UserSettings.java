package com.romco.utilities;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.Map;

public class UserSettings {
    private Map<String, String> windowSettings;
//    private String defaultTab;
//    private int defaultWidth, defaultHeight;
    
    public UserSettings() {
    }
    
    public Map<String, String> getWindowSettings() {
        return windowSettings;
    }
    
    public void setWindowSettings(Map<String, String> windowSettings) {
        this.windowSettings = windowSettings;
    }
    
    //
//    public String getDefaultTab() {
//        return defaultTab;
//    }
//
//    public void setDefaultTab(String defaultTab) {
//        this.defaultTab = defaultTab;
//    }
//
//    public int getDefaultWidth() {
//        return defaultWidth;
//    }
//
//    public void setDefaultWidth(int defaultWidth) {
//        this.defaultWidth = defaultWidth;
//    }
//
//    public int getDefaultHeight() {
//        return defaultHeight;
//    }
//
//    public void setDefaultHeight(int defaultHeight) {
//        this.defaultHeight = defaultHeight;
//    }
    
    public static UserSettings createFromYaml(InputStream input) {
        Constructor c = new Constructor(UserSettings.class);
        Yaml yaml = new Yaml(c);
        
        UserSettings settings = yaml.loadAs(input, UserSettings.class);
        System.out.println(settings);
        System.out.println(yaml.dump(settings));
        return settings;
    }
}
