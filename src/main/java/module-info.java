module id.dojo.akunku {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;


    opens id.dojo.akunku to javafx.fxml;
    exports id.dojo.akunku;
    opens id.dojo.akunku.controllers to javafx.fxml;
    exports id.dojo.akunku.controllers;

}