module com.example.kyrs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.kyrs to javafx.fxml;
    exports com.example.kyrs;
}