module MorTimer {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.apache.commons.lang3;
    requires snakeyaml;
    
    requires java.sql; //required by snakeyaml for some reason...
    
    opens com.romco;
    opens com.romco.utilities;
}