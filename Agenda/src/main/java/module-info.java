module com.example.agenda {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires java.desktop;

    opens com.agenda to javafx.fxml;
    exports com.agenda;
    exports com.agenda.controller;
    opens com.agenda.controller to javafx.fxml;
    //opens com.agenda.controller to java.awt;
}