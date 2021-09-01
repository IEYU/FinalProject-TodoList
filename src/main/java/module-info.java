module com.example.finalprojecttodolist {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.finalprojecttodolist to javafx.fxml;
    exports com.example.finalprojecttodolist;
}