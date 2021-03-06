module MorTimer {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    requires org.apache.commons.lang3;
    requires snakeyaml;
    
    requires java.sql; //required by snakeyaml for some reason...

    requires logback.core;
    requires logback.classic;
    requires slf4j.api;
    requires spring.context;
    requires spring.core;
    requires org.jetbrains.annotations;

    opens com.romco;
    opens com.romco.utilities;
    opens com.romco.controller;
}