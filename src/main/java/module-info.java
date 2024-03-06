module rzelonek.libsys {
    requires javafx.controls;
    requires javafx.fxml;

    opens rzelonek.libsys to javafx.fxml;
    opens rzelonek.libsys.controlers to javafx.fxml; 
    opens rzelonek.libsys.auth to javafx.fxml; 
    opens rzelonek.libsys.model to javafx.fxml;
    opens rzelonek.libsys.db to javafx.fxml;
    exports rzelonek.libsys;
}
