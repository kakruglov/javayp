module com.example.kyrs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;


    opens com.example.kyrs to javafx.fxml;
    exports com.example.kyrs;
}